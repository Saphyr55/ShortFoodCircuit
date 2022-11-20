package fr.sfc.model.repository;

public interface Repository<T> {

    Iterable<T> findAll();

    T find(int id);

    long count();

    void delete(T entity);

    boolean exist(int id);

    void save(T entity);

}
