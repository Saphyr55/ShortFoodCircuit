package fr.sfc.repository;

import fr.sfc.api.database.Query;
import fr.sfc.api.database.QueryFactory;
import fr.sfc.api.database.annotation.MagicQuery;
import fr.sfc.api.persistence.EntityClassManager;
import fr.sfc.api.persistence.EntityManager;
import fr.sfc.api.persistence.Repository;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.entity.Company;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;
import fr.sfc.repository.queries.ProductTourQueries;
import org.intellij.lang.annotations.MagicConstant;

import java.util.Set;

public class ProductTourRepository implements Repository<ProductTour> {

    @Inject
    private EntityManager entityManager;

    @Inject
    private QueryFactory queryFactory;

    @Inject
    private EntityClassManager entityClassManager;

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

    public Set<ProductTour> findByVehicle(Vehicle vehicle) {

        try (Query query = queryFactory.createMagicQuery(
                "findByVehicle", ProductTourQueries.class, vehicle.getId())) {

            return entityManager.wrapResultSetToEntities(ProductTour.class, query.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<ProductTour> findByCompany(Company company) {
        try (Query query = queryFactory.createMagicQuery(
                "findByCompany",
                ProductTourQueries.class,
                company.getId())) {

            return entityManager.wrapResultSetToEntities(ProductTour.class, query.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
