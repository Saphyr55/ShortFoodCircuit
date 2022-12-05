package fr.sfc.repository.queries;

import fr.sfc.api.database.Queryable;
import fr.sfc.api.database.annotation.MagicQuery;
import fr.sfc.entity.Company;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;

public class ProductTourQueries implements Queryable<ProductTour> {

    @MagicQuery(
            value = "SELECT * FROM :table0 WHERE :id0 = ?",
            tables = ProductTour.class,
            ids = Vehicle.class)
    private Void findByVehicle;

    @MagicQuery(
            value = "SELECT * FROM :table0 WHERE :id0 = ?",
            tables = ProductTour.class,
            ids = Company.class)
    private Void findByCompany;

}
