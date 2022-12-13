package fr.sfc.repository;

import fr.sfc.entity.ProductTour;
import fr.sfc.framework.database.Query;
import fr.sfc.framework.database.QueryFactory;
import fr.sfc.framework.persistence.EntityManager;
import fr.sfc.framework.persistence.Repository;
import fr.sfc.entity.Company;

import java.util.Optional;
import java.util.Set;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.queries.CompanyQueries;
import fr.sfc.repository.queries.ProductTourQueries;


public class CompanyRepository implements Repository<Company> {

    @Inject
    private EntityManager entityManager;

    @Inject
    private QueryFactory queryFactory;

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

    public Optional<Company> findBySIRET(Integer SIRET) {

        try (Query query = queryFactory.createMagicQuery(
                "findBySIRET", CompanyQueries.class, SIRET)) {

            return entityManager.wrapResultSetToEntities(Company.class, query.executeQuery()).stream().findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
