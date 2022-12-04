package fr.sfc.repository;

import fr.sfc.api.persistence.EntityManager;
import fr.sfc.api.persistence.Repository;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.entity.ProductTour;

import java.util.Set;

public class ProductTourRepository implements Repository<ProductTour> {

    @Inject
    private EntityManager entityManager;

    @Override
    public Set<ProductTour> findAll() {
        return entityManager.findAll(ProductTour.class);
    }

    @Override
    public ProductTour find(int id) {
        return entityManager.find(ProductTour.class, id);
    }

    @Override
    public long count() {
        return entityManager.count(ProductTour.class);
    }

    @Override
    public void delete(ProductTour entity) {
        entityManager.delete(entity);
    }

    @Override
    public void insert(ProductTour entity) {
        entityManager.insert(entity);
    }

    @Override
    public void save(ProductTour entity) {
        entityManager.insert(entity);
    }

}
