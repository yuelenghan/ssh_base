package com.ghtn.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-11-12
 * Time: 下午4:30
 */
public interface AuditGenericManager<T, PK extends Serializable> extends GenericManager {

    T getOld(PK pk, Number version);

    List<Number> getRevisions(PK pk);

    Date getRevisionDate(Number version);

    Number getRevisionNumber(Date date);
}
