package fr.sfc.repository;

import fr.sfc.entity.Vehicle;
import fr.sfc.framework.database.Query;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.persistence.EntityManager;
import fr.sfc.framework.persistence.Repository;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.queries.VehicleQueries;

import java.util.Optional;
import java.util.Set;

public class VehicleRepository implements Repository<Vehicle> {

    @Inject
    private EntityManager entityManager;
    @Inject
    private QueryFactory queryFactory;

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
    public void save(Vehicle entity) {
        entityManager.insert(entity);
    }


    public Optional<Vehicle> findByMatriculation(String matriculation) {

        try (Query query = queryFactory.createMagicQuery(
                "findByMatriculation", VehicleQueries.class, matriculation)) {

            return entityManager.wrapResultSetToEntities(Vehicle.class, query.executeQuery()).stream().findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
