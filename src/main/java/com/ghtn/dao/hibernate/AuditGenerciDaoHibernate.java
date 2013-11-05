package com.ghtn.dao.hibernate;

import com.ghtn.dao.AuditGenericDao;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午5:26
 * To change this template use File | Settings | File Templates.
 */
public class AuditGenerciDaoHibernate<T, PK extends Serializable> extends GenericDaoHibernate
        implements AuditGenericDao<T, PK>{

    private Class<T> persistentClass;

    public AuditReader getAuditReader() {
        return AuditReaderFactory.get(getSession());
    }

    public AuditGenerciDaoHibernate(final Class<T> persistentClass) {
        super(persistentClass);
        this.persistentClass = persistentClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getOld(PK pk, Number version) {
        return getAuditReader().find(persistentClass, pk, version);
    }

    @Override
    public List<Number> getRevisions(PK pk) {
        return getAuditReader().getRevisions(persistentClass, pk);
    }

    @Override
    public Date getRevisionDate(Number version) {
        return getAuditReader().getRevisionDate(version);
    }

    @Override
    public Number getRevisionNumber(Date date) {
        return getAuditReader().getRevisionNumberForDate(date);
    }
}
