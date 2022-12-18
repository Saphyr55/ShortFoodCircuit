package fr.sfc.framework.persistence;

import fr.sfc.framework.entity.Admin;
import fr.sfc.framework.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityClassManagerTest {

    EntityClassManager entityClassManager;

    @BeforeEach
    void setUp() {
        EntityClassLoader entityClassLoader = new EntityClassLoader();
        entityClassLoader.setEntityPackage("fr.sfc.framework.entity");
        entityClassLoader.load();
        entityClassManager = entityClassLoader.createClassManager();
    }

    @Test
    void getValueId() {
        User user = new User();
        user.setId(90);
        assertEquals(90, entityClassManager.getValueId(user));
    }

    @Test
    void getIdName() {
        assertEquals("idAdmin", entityClassManager.getIdName(Admin.class));
    }

    @Test
    void getNameTable() {
        assertEquals( "administrator", entityClassManager.getNameTable(Admin.class));
    }

    @Test
    void getClassEntities() {
        assertNull(entityClassManager.getClassEntities().get(Class.class));
        assertFalse(entityClassManager.getClassEntities().get(Admin.class).isEmpty());
    }


}