package fr.sfc.model.repository;


import fr.sfc.api.persistence.EntityManager;
import fr.sfc.api.persistence.Repository;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.model.entity.Order;

import java.util.Set;

public class IndentRepository implements Repository<Order> {

    @Inject
    private EntityManager entityManager;

    @Override
    public Set<Order> findAll() {
        return entityManager.findAll(Order.class);
    }

    @Override
    public Order find(int id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public long count() {
        return entityManager.count(Order.class);
    }

    @Override
    public void delete(Order entity) {
        entityManager.delete(entity);
    }

    @Override
    public void insert(Order entity) {
        entityManager.insert(entity);
    }

    @Override
    public void save(Order admin) {
        entityManager.insert(admin);
    }
}
