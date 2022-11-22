package fr.sfc.database;

import fr.sfc.model.entity.Admin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class QueryImplTest {

    private static QueryBuilder queryBuilder;

    @BeforeAll
    static void setup() {
        DatabaseManager.init();
        queryBuilder = DatabaseManager.TEST.createQueryBuilder()
                    .select("idAdmin")
                    .from(Admin.class)
                    .where("idAdmin = :idAdmin");
        System.out.println(queryBuilder.buildString());
    }

    @Test
    void setParameter() throws SQLException {
        Query request = queryBuilder.build();
        request.setParameter("idAdmin", "1").prepare();
        ResultSet resultSet = request.getResultSet();
        resultSet.next();
        assertEquals(1,resultSet.getInt(1));
     }

    @Test
    void execute() {

    }

    @Test
    void query() {

    }

    @Test
    void prepare() {

    }

    @Test
    void getResultSet() {
    }
}