package com.ghtn.service;

import java.io.Serializable;
import java.util.List;

public interface GenericManager<T, PK extends Serializable> {

    List<T> getAll();

    T get(PK id);

    boolean exists(PK id);

    T save(T object);

    void remove(T object);

    void remove(PK id);

    List<T> search(String searchTerm);

    void reindex();

    void reindexAll(boolean async);

}
