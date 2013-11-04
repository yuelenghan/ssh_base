package com.ghtn.dao.hibernate;

import com.ghtn.dao.GenericDao;
import com.ghtn.util.HibernateSearchTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;
import org.hibernate.*;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * 实现通用的增删改查
 * @param <T>
 * @param <PK>
 */
public class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;

    @Resource
    private SessionFactory sessionFactory;

    private Analyzer defaultAnalyzer;

    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
    }

    public GenericDaoHibernate(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public Session getSession() throws HibernateException {
        Session sess = getSessionFactory().getCurrentSession();
        if (sess == null) {
            sess = getSessionFactory().openSession();
        }
        return sess;
    }

    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session sess = getSession();
        return sess.createCriteria(persistentClass).list();
    }

    public List<T> getAllDistinct() {
        Collection<T> result = new LinkedHashSet<T>(getAll());
        return new ArrayList<T>(result);
    }

    /**
     * 全文搜索
     * @param searchTerm
     * @return
     * @throws SearchException
     */
    @SuppressWarnings("unchecked")
    public List<T> search(String searchTerm) throws SearchException {
        Session sess = getSession();
        FullTextSession txtSession = Search.getFullTextSession(sess);

        org.apache.lucene.search.Query qry;
        try {
            qry = HibernateSearchTools.generateQuery(searchTerm, this.persistentClass, sess, defaultAnalyzer);
        } catch (ParseException ex) {
            throw new SearchException(ex);
        }
        org.hibernate.search.FullTextQuery hibQuery = txtSession.createFullTextQuery(qry,
                this.persistentClass);
        return hibQuery.list();
    }

    @SuppressWarnings("unchecked")
    public T get(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        return entity != null;
    }

    @SuppressWarnings("unchecked")
    public T save(T object) {
        Session sess = getSession();
        return (T) sess.merge(object);
    }

    public void remove(T object) {
        Session sess = getSession();
        sess.delete(object);
    }

    @SuppressWarnings("unchecked")
    public void remove(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        sess.delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        Session sess = getSession();
        Query namedQuery = sess.getNamedQuery(queryName);

        for (String s : queryParams.keySet()) {
            namedQuery.setParameter(s, queryParams.get(s));
        }

        return namedQuery.list();
    }

    /**
     * 对单个实体重新建立索引
     */
    public void reindex() {
        HibernateSearchTools.reindex(persistentClass, getSessionFactory().getCurrentSession());
    }

    /**
     * 对所有实体重新建立索引
     * @param async  true 异步 ； false 同步
     */
    public void reindexAll(boolean async) {
        HibernateSearchTools.reindexAll(async, getSessionFactory().getCurrentSession());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> queryHql(String hql) {
        Session sess = getSession();
        return sess.createQuery(hql).list();
    }
}
