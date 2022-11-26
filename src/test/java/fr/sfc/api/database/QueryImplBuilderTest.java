package fr.sfc.api.database;

import fr.sfc.model.entity.Admin;
import fr.sfc.model.entity.Customer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryImplBuilderTest {

    private static Database dbTest;
    private static final DatabaseManager databaseManager = new DatabaseManager(
            "db.ini",
            "test");

    @BeforeAll
    static void setup() {
        databaseManager.configure();
        dbTest = databaseManager.getDatabase("test");
    }
    
    @AfterAll
    static void teardown() {
        databaseManager.shutdown();
    }

    @Test
    void select() {
        assertEquals("SELECT id", dbTest.createQueryBuilder().select("id").buildString());
        assertEquals("SELECT id,password", dbTest.createQueryBuilder().select("id", "password").buildString());
    }

    @Test
    void from() {
        assertEquals(" FROM admin", dbTest.createQueryBuilder().from("admin").buildString());
        assertEquals(" FROM admin,customer", dbTest.createQueryBuilder().from("admin", "customer").buildString());
    }

    @Test
    void classFrom() {
        assertEquals(" FROM admin", dbTest.createQueryBuilder().from(Admin.class).buildString());
        assertEquals(" FROM admin,customer", dbTest.createQueryBuilder().from(Admin.class, Customer.class).buildString());
    }

    @Test
    void fromRename() {
        assertEquals(" FROM admin a", dbTest.createQueryBuilder().from(Admin.class, "a").buildString());
    }

    @Test
    void where() {
        assertEquals(" WHERE id=0", dbTest.createQueryBuilder().where("id=0").buildString());
    }

    @Test
    void innerJoin() {
        assertEquals(" INNER JOIN admin ON id=0", dbTest.createQueryBuilder().innerJoin("admin","id=0").buildString());
    }

    @Test
    void innerJoinClass() {
        assertEquals(" INNER JOIN customer ON id=0", dbTest.createQueryBuilder().innerJoin(Customer.class, "id=0").buildString());
    }

    @Test
    void and() {
        assertEquals(" WHERE id=0 AND password='password'", dbTest.createQueryBuilder().where("id=0").and("password='password'").buildString());
    }

    @Test
    void or() {
        assertEquals(" WHERE id=0 OR password='password'", dbTest.createQueryBuilder().where("id=0").or("password='password'").buildString());
    }


    @Test
    void innerJoinRename() {
        assertEquals(" INNER JOIN customer c ON c.id=0", dbTest.createQueryBuilder().innerJoin(Customer.class, "c", "c.id=0").buildString());
    }

    @Test
    void buildRequest() {

    }

    @Test
    void buildString() {
        assertEquals("SELECT c.id FROM customer c INNER JOIN admin a ON a.id=c.id WHERE a.id>=0 AND a.id=<10", dbTest.createQueryBuilder()
                .select("c.id")
                .from(Customer.class, "c")
                .innerJoin(Admin.class, "a", "a.id=c.id")
                .where("a.id>=0")
                .and("a.id=<10")
                .buildString());
    }


}