package fr.sfc.model.repository;

import fr.sfc.model.entity.Admin;

import java.util.Set;

public interface Repository<T> {

    Set<T> findAll();

    T find(int id);

    long count();

    void delete(T entity);

    void insert(T entity);

    void save(T admin);
}