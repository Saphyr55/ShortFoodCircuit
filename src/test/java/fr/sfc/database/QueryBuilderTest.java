package fr.sfc.database;

import fr.sfc.model.Admin;
import fr.sfc.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryBuilderTest {

    private QueryBuilder queryBuilder;

    @BeforeEach
    void setup() {
        queryBuilder = QueryBuilder.of();
    }

    @Test
    void select() {
        assertEquals("SELECT id", QueryBuilder.of().select("id").buildString());
        assertEquals("SELECT id,password", QueryBuilder.of().select("id", "password").buildString());
    }

    @Test
    void from() {
        assertEquals(" FROM admin", QueryBuilder.of().from("admin").buildString());
        assertEquals(" FROM admin,customer", QueryBuilder.of().from("admin", "customer").buildString());
    }

    @Test
    void classFrom() {
        assertEquals(" FROM admin", QueryBuilder.of().from(Admin.class).buildString());
        assertEquals(" FROM admin,customer", QueryBuilder.of().from(Admin.class, Customer.class).buildString());
    }

    @Test
    void fromRename() {
        assertEquals(" FROM admin a", QueryBuilder.of().from(Admin.class, "a").buildString());
    }

    @Test
    void where() {
        assertEquals(" WHERE id=0", QueryBuilder.of().where("id=0").buildString());
    }

    @Test
    void innerJoin() {
        assertEquals(" INNER JOIN admin ON id=0", QueryBuilder.of().innerJoin("admin","id=0").buildString());
    }

    @Test
    void innerJoinClass() {
        assertEquals(" INNER JOIN customer ON id=0", QueryBuilder.of().innerJoin(Customer.class, "id=0").buildString());
    }

    @Test
    void and() {
        assertEquals(" WHERE id=0 AND password='password'", QueryBuilder.of().where("id=0").and("password='password'").buildString());
    }

    @Test
    void or() {
        assertEquals(" WHERE id=0 OR password='password'", QueryBuilder.of().where("id=0").or("password='password'").buildString());
    }


    @Test
    void innerJoinRename() {
        assertEquals(" INNER JOIN customer c ON c.id=0", QueryBuilder.of().innerJoin(Customer.class, "c", "c.id=0").buildString());
    }

    @Test
    void buildRequest() {

    }

    @Test
    void buildString() {
        assertEquals("SELECT c.id FROM customer c INNER JOIN admin a ON a.id=c.id WHERE a.id>=0 AND a.id=<10", QueryBuilder.of()
                .select("c.id")
                .from(Customer.class, "c")
                .innerJoin(Admin.class, "a", "a.id=c.id")
                .where("a.id>=0")
                .and("a.id=<10")
                .buildString());
    }


}