package com.ghtn.model;

import org.hibernate.envers.DefaultTrackingModifiedEntitiesRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-5
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
@Entity
@RevisionEntity(SimpleListener.class)
public class SimpleRevEntity extends DefaultTrackingModifiedEntitiesRevisionEntity {

    private String editor;

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

}
