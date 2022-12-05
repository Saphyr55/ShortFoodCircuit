package fr.sfc.repository.queries;

import fr.sfc.framework.database.Queryable;
import fr.sfc.framework.database.annotation.MagicQuery;
import fr.sfc.entity.Company;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;

public class ProductTourQueries implements Queryable<ProductTour> {

    @MagicQuery(
            request = "SELECT * FROM :table0 WHERE :id0 = ?",
            tables = ProductTour.class,
            ids = Vehicle.class)
    private Void findByVehicle;

    @MagicQuery(
            request = "SELECT * FROM :table0 WHERE :id0 = ?",
            tables = ProductTour.class,
            ids = Company.class)
    private Void findByCompany;

}
