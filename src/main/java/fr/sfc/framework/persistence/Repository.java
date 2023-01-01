package fr.sfc.framework.persistence;

import java.util.Set;

/**
 *
 * @param <T>
 */
public interface Repository<T> {

    /**
     *
     * @return
     */
    Set<T> findAll();

    /**
     *
     * @param id
     * @return
     */
    T find(int id);

    /**
     *
     * @return
     */
    long count();

    /**
     *
     * @param entity
     */
    void delete(T entity);

    /**
     *
     */
    void insert(T entity);

    /**
     * 
     * @param admin
     */
    void save(T admin);
}
