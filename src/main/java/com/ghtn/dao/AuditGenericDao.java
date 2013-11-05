package com.ghtn.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午5:25
 * To change this template use File | Settings | File Templates.
 */
public interface AuditGenericDao<T, PK extends Serializable> extends GenericDao {

    T getOld(PK pk, Number version);

    List<Number> getRevisions(PK pk);

    Date getRevisionDate(Number version);

    Number getRevisionNumber(Date date);
}
