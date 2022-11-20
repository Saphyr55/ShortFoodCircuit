package fr.sfc.database;

import fr.sfc.model.entity.Admin;
import fr.sfc.model.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryImplBuilderTest {

    private Database db;

    @BeforeEach
    void setup() {
        db = DatabaseManager.TEST;
    }

    @Test
    void select() {
        assertEquals("SELECT id", db.createQueryBuilder().select("id").buildString());
        assertEquals("SELECT id,password", db.createQueryBuilder().select("id", "password").buildString());
    }

    @Test
    void from() {
        assertEquals(" FROM admin", db.createQueryBuilder().from("admin").buildString());
        assertEquals(" FROM admin,customer", db.createQueryBuilder().from("admin", "customer").buildString());
    }

    @Test
    void classFrom() {
        assertEquals(" FROM admin", db.createQueryBuilder().from(Admin.class).buildString());
        assertEquals(" FROM admin,customer", db.createQueryBuilder().from(Admin.class, Customer.class).buildString());
    }

    @Test
    void fromRename() {
        assertEquals(" FROM admin a", db.createQueryBuilder().from(Admin.class, "a").buildString());
    }

    @Test
    void where() {
        assertEquals(" WHERE id=0", db.createQueryBuilder().where("id=0").buildString());
    }

    @Test
    void innerJoin() {
        assertEquals(" INNER JOIN admin ON id=0", db.createQueryBuilder().innerJoin("admin","id=0").buildString());
    }

    @Test
    void innerJoinClass() {
        assertEquals(" INNER JOIN customer ON id=0", db.createQueryBuilder().innerJoin(Customer.class, "id=0").buildString());
    }

    @Test
    void and() {
        assertEquals(" WHERE id=0 AND password='password'", db.createQueryBuilder().where("id=0").and("password='password'").buildString());
    }

    @Test
    void or() {
        assertEquals(" WHERE id=0 OR password='password'", db.createQueryBuilder().where("id=0").or("password='password'").buildString());
    }


    @Test
    void innerJoinRename() {
        assertEquals(" INNER JOIN customer c ON c.id=0", db.createQueryBuilder().innerJoin(Customer.class, "c", "c.id=0").buildString());
    }

    @Test
    void buildRequest() {

    }

    @Test
    void buildString() {
        assertEquals("SELECT c.id FROM customer c INNER JOIN admin a ON a.id=c.id WHERE a.id>=0 AND a.id=<10", db.createQueryBuilder()
                .select("c.id")
                .from(Customer.class, "c")
                .innerJoin(Admin.class, "a", "a.id=c.id")
                .where("a.id>=0")
                .and("a.id=<10")
                .buildString());
    }


}