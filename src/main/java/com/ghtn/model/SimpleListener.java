package com.ghtn.model;

import org.hibernate.envers.RevisionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-5
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 */
public class SimpleListener implements RevisionListener{
    @Override
    public void newRevision(Object o) {
        SimpleRevEntity revEntity = (SimpleRevEntity)o;

        revEntity.setEditor("我编辑的");
    }
}
