package com.ghtn.service.impl;

import com.ghtn.dao.GenericDao;
import com.ghtn.service.GenericManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;

public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {

    protected final Log log = LogFactory.getLog(getClass());

    protected GenericDao<T, PK> dao;

    public GenericManagerImpl() {
    }

    public GenericManagerImpl(GenericDao<T, PK> genericDao) {
        this.dao = genericDao;
    }

    public List<T> getAll() {
        return dao.getAll();
    }

    public T get(PK id) {
        return dao.get(id);
    }

    public boolean exists(PK id) {
        return dao.exists(id);
    }

    public T save(T object) {
        return dao.save(object);
    }

    public void remove(T object) {
        dao.remove(object);
    }

    public void remove(PK id) {
        dao.remove(id);
    }

    @Override
    public List<T> search(String searchTerm) {
        if (searchTerm == null || "".equals(searchTerm.trim())) {
            return getAll();
        }

        return dao.search(searchTerm);
    }

    @Override
    public void reindex() {
        dao.reindex();
    }

    @Override
    public void reindexAll(boolean async) {
        dao.reindexAll(async);
    }

}
