package fr.sfc.repository;

import fr.sfc.framework.persistence.EntityManager;
import fr.sfc.framework.persistence.Repository;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.entity.Producer;

import java.util.Set;

public class ProducerRepository implements Repository<Producer>  {

    @Inject
    private EntityManager entityManager;

    @Override
    public Set<Producer> findAll() {
        return entityManager.findAll(Producer.class);
    }

    @Override
    public Producer find(int id) {
        return entityManager.find(Producer.class,id);
    }

    @Override
    public long count() {
        return entityManager.count(Producer.class);
    }

    @Override
    public void delete(Producer entity) {
        entityManager.delete(Producer.class);
    }

    @Override
    public void insert(Producer entity) {
        entityManager.insert(Producer.class);
    }

    @Override
    public void save(Producer admin) {
        entityManager.insert(Producer.class);
    }
}
