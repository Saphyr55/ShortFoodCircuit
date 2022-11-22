package fr.sfc.model.repository;

import fr.sfc.database.Query;
import fr.sfc.model.entity.Admin;
import fr.sfc.persistence.EntityManager;

import java.util.HashSet;
import java.util.Set;

public class AdminRepository implements Repository<Admin> {

    public EntityManager entityManager;

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
