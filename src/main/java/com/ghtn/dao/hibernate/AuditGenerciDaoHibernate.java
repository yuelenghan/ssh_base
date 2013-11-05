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
        implements AuditGenericDao<T, PK> {

    private Class<T> persistentClass;

    public AuditReader getAuditReader() {
        return AuditReaderFactory.get(getSession());
    }

    public AuditGenerciDaoHibernate(final Class<T> persistentClass) {
        super(persistentClass);
        this.persistentClass = persistentClass;
    }

    /**
     * 根据id和版本号得到之前的数据
     *
     * @param pk      id
     * @param version 版本号
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getOld(PK pk, Number version) {
        return getAuditReader().find(persistentClass, pk, version);
    }

    /**
     * 根据id得到该实体的所有版本号
     *
     * @param pk id
     * @return
     */
    @Override
    public List<Number> getRevisions(PK pk) {
        return getAuditReader().getRevisions(persistentClass, pk);
    }

    /**
     * 根据版本号得到修改时间
     *
     * @param version
     * @return
     */
    @Override
    public Date getRevisionDate(Number version) {
        return getAuditReader().getRevisionDate(version);
    }

    /**
     * 根据修改时间得到版本号
     *
     * @param date 修改时间
     * @return
     */
    @Override
    public Number getRevisionNumber(Date date) {
        return getAuditReader().getRevisionNumberForDate(date);
    }
}
