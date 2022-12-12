package fr.sfc.repository.queries;

import fr.sfc.entity.Company;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;
import fr.sfc.framework.database.annotation.MagicQuery;

public final class CompanyQueries {

    @MagicQuery(
            request = "SELECT * FROM :table0 WHERE SIRET = ?",
            tables = Company.class
    ) private Void findBySIRET;

}
