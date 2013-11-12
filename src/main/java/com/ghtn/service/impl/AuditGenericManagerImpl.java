package com.ghtn.service.impl;

import com.ghtn.dao.AuditGenericDao;
import com.ghtn.service.AuditGenericManager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-11-12
 * Time: 下午4:30
 */
public class AuditGenericManagerImpl<T, PK extends Serializable> extends GenericManagerImpl
        implements AuditGenericManager<T, PK> {

    private AuditGenericDao auditGenericDao;

    public AuditGenericManagerImpl(AuditGenericDao auditGenericDao) {
        super(auditGenericDao);
        this.auditGenericDao = auditGenericDao;
    }

    /**
     * 根据id和版本号得到之前的数据
     *
     * @param pk      id
     * @param version 版本号
     * @return
     */
    @Override
    public T getOld(PK pk, Number version) {
        return (T) auditGenericDao.getOld(pk, version);
    }

    /**
     * 根据id得到该实体的所有版本号
     *
     * @param pk id
     * @return
     */
    @Override
    public List<Number> getRevisions(PK pk) {
        return auditGenericDao.getRevisions(pk);
    }

    /**
     * 根据版本号得到修改时间
     *
     * @param version
     * @return
     */
    @Override
    public Date getRevisionDate(Number version) {
        return auditGenericDao.getRevisionDate(version);
    }

    /**
     * 根据修改时间得到版本号
     *
     * @param date 修改时间
     * @return
     */
    @Override
    public Number getRevisionNumber(Date date) {
        return auditGenericDao.getRevisionNumber(date);
    }
}
