package fr.sfc.repository;


import fr.sfc.entity.ProductTour;
import fr.sfc.framework.database.Query;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.persistence.EntityManager;
import fr.sfc.framework.persistence.Repository;
import fr.sfc.framework.injection.Inject;
import fr.sfc.entity.Order;
import fr.sfc.repository.queries.OrderQueries;

import java.util.Set;

public class OrderRepository implements Repository<Order> {

    @Inject
    private EntityManager entityManager;

    @Inject
    private QueryFactory queryFactory;

    @Override
    public Set<Order> findAll() {
        return entityManager.findAll(Order.class);
    }

    @Override
    public Order find(int id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public long count() {
        return entityManager.count(Order.class);
    }

    @Override
    public void delete(Order entity) {
        entityManager.delete(entity);
    }

    @Override
    public void insert(Order entity) {
        entityManager.insert(entity);
    }

    @Override
    public void save(Order entity) {
        entityManager.insert(entity);
    }

    public Set<Order> findByProductTour(ProductTour productTour) {

        try (Query query = queryFactory.createMagicQuery(
                "findByProductTour", OrderQueries.class,
                productTour.getId())) {

            return entityManager.wrapResultSetToEntities(Order.class, query.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
