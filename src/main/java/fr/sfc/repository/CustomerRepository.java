package fr.sfc.repository;

import fr.sfc.framework.persistence.EntityManager;
import fr.sfc.framework.persistence.Repository;
import fr.sfc.framework.injection.Inject;
import fr.sfc.entity.Customer;

import java.util.Set;

public class CustomerRepository implements Repository<Customer> {

    @Inject
    private EntityManager entityManager;

    @Override
    public Set<Customer> findAll() {
        return entityManager.findAll(Customer.class);
    }

    @Override
    public Customer find(int id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public long count() {
        return entityManager.count(Customer.class);
    }

    @Override
    public void delete(Customer entity) {
        entityManager.delete(entity);
    }

    @Override
    public void insert(Customer entity) {
        entityManager.insert(entity);
    }

    @Override
    public void save(Customer admin) {
        entityManager.insert(admin);
    }
}
