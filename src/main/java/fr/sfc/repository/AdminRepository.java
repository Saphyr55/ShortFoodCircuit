package fr.sfc.repository;

import fr.sfc.framework.persistence.EntityManager;
import fr.sfc.framework.persistence.Repository;
import fr.sfc.framework.injection.Inject;
import fr.sfc.entity.Admin;

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
