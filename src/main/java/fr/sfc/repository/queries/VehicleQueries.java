package fr.sfc.repository.queries;

import fr.sfc.entity.Company;
import fr.sfc.entity.Vehicle;
import fr.sfc.framework.database.annotation.MagicQuery;

public class VehicleQueries {

    @MagicQuery(
            request = "SELECT * FROM :table0 WHERE matriculation = ?",
            tables = Vehicle.class)
    private Void findByMatriculation;
    
    @MagicQuery(
            request = "SELECT * FROM :table0 WHERE :id0 = ?",
            tables = Vehicle.class,
            ids = Company.class)
    private Void findByCompany;

}
