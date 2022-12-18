package fr.sfc.repository.queries;

import fr.sfc.entity.Order;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.database.annotation.MagicQuery;

public class OrderQueries {

    @MagicQuery(
            request = "SELECT * FROM :table0 WHERE :id0 = ?",
            tables = Order.class,
            ids = ProductTour.class)
    private Void findByProductTour;


}
