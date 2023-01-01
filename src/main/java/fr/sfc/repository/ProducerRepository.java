package fr.sfc.repository;

import fr.sfc.entity.Producer;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.persistence.EntityManager;
import fr.sfc.framework.persistence.Repository;
import fr.sfc.framework.injection.Inject;

import java.util.Set;

public class ProducerRepository implements Repository<Producer>  {

    @Inject
    private EntityManager entityManager;

    @Inject
    private QueryFactory queryFactory;

    @Override
    public Set<Producer> findAll() {
        return entityManager.findAll(Producer.class);
    }

    @Override
    public Producer find(int id) {
        return entityManager.find(Producer.class, id);
    }

    @Override
    public long count() {
        return entityManager.count(Producer.class);
    }

    @Override
    public void delete(Producer entity) {
        entityManager.delete(entity);
    }

    @Override
    public void insert(Producer entity) {
        entityManager.insert(entity);
    }

    @Override
    public void save(Producer entity) {
        entityManager.insert(entity);
    }

}
