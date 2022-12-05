package fr.sfc.repository;

import fr.sfc.api.persistence.EntityManager;
import fr.sfc.api.persistence.Repository;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.entity.Vehicle;

import java.util.Set;

public class VehicleRepository implements Repository<Vehicle> {

    @Inject
    private EntityManager entityManager;

    @Override
    public Set<Vehicle> findAll() {
        return entityManager.findAll(Vehicle.class);
    }

    @Override
    public Vehicle find(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    @Override
    public long count() {
        return entityManager.count(Vehicle.class);
    }

    @Override
    public void delete(Vehicle entity) {
        entityManager.delete(entity);
    }

    @Override
    public void insert(Vehicle entity) {
        entityManager.insert(entity);
    }

    @Override
    public void save(Vehicle admin) {
        entityManager.insert(admin);
    }
}
