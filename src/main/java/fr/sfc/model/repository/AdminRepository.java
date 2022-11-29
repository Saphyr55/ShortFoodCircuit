package fr.sfc.model.repository;

import fr.sfc.api.persistence.Repository;
import fr.sfc.model.entity.Admin;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.api.persistence.EntityManager;

import java.util.Set;

public class AdminRepository implements Repository<Admin> {

    @Inject
    private EntityManager entityManager;

    @Override
    public Set<Admin> findAll() {
        return entityManager.findAll(Admin.class);
    }

    @Override
    public Admin find(int id) {
        return entityManager.find(Admin.class, id);
    }

    @Override
    public long count() {
        return entityManager.count(Admin.class);
    }

    @Override
    public void delete(Admin admin) {
        entityManager.delete(admin);
    }

    @Override
    public void insert(Admin admin) {
        entityManager.insert(admin);
    }

    @Override
    public void save(Admin admin) {
        entityManager.insert(admin);
    }
}
