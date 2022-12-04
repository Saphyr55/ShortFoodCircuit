package fr.sfc.repository;

import fr.sfc.api.persistence.EntityManager;
import fr.sfc.api.persistence.Repository;
import fr.sfc.entity.Company;
import java.util.Set;
import fr.sfc.api.persistence.annotation.Inject;


public class CompanyRepository implements Repository<Company> {

    @Inject
    private EntityManager entityManager;
    
    @Override
    public Set<Company> findAll() {
        return entityManager.findAll(Company.class);
    }

    @Override
    public Company find(int id) {
        return entityManager.find(Company.class, id);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Company entity) {

    }

    @Override
    public void insert(Company entity) {

    }

    @Override
    public void save(Company admin) {

    }
}
