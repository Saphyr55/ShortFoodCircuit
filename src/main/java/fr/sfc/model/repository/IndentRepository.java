package fr.sfc.model.repository;


import fr.sfc.api.persistence.EntityManager;
import fr.sfc.api.persistence.Repository;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.model.entity.Indent;

import java.util.Set;

public class IndentRepository implements Repository<Indent> {

    @Inject
    private EntityManager entityManager;

    @Override
    public Set<Indent> findAll() {
        return entityManager.findAll(Indent.class);
    }

    @Override
    public Indent find(int id) {
        return entityManager.find(Indent.class, id);
    }

    @Override
    public long count() {
        return entityManager.count(Indent.class);
    }

    @Override
    public void delete(Indent entity) {
        entityManager.delete(entity);
    }

    @Override
    public void insert(Indent entity) {
        entityManager.insert(entity);
    }

    @Override
    public void save(Indent admin) {
        entityManager.insert(admin);
    }
}
