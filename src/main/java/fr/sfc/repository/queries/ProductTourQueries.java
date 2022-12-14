package fr.sfc.repository.queries;

import fr.sfc.entity.Company;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;
import fr.sfc.framework.database.annotation.MagicQuery;

public final class ProductTourQueries {

    @MagicQuery(
            request = "SELECT * FROM :table0 WHERE :id0 = ?",
            tables = ProductTour.class,
            ids = Vehicle.class)
    private Void findByVehicle;

    @MagicQuery(
            request = "SELECT * FROM :table0 WHERE :id0 = ?",
            tables = ProductTour.class,
            ids = Company.class
    ) private Void findByCompany;


    @MagicQuery(
            request = "SELECT Count(*) FROM :table0 WHERE :id0 = ? ",
            tables = ProductTour.class,
            ids = Company.class
    ) private Void countProductTourByCompany;

}
