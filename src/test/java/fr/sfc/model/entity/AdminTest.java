package fr.sfc.model.entity;

import fr.sfc.entity.Admin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void getId() {
        assertEquals(1, new Admin(1, "password").getId());
    }

    @Test
    void setId() {
        var admin = new Admin( "password");
        admin.setId(1);
        assertEquals(1, admin.getId());
    }

    @Test
    void getPassword() {
        assertEquals("password", new Admin( "password").getPassword());
    }

    @Test
    void setPassword() {
        var admin = new Admin( "password");
        admin.setPassword("some new password");
        assertEquals("some new password", admin.getPassword());
    }
    
}