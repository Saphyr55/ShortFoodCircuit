package fr.sfc.framework.persistence;

import fr.sfc.framework.entity.Admin;
import fr.sfc.framework.entity.User;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {

    }

    @Test
    void getFieldsFromEntity() {

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
    void formatInsert() {
        User user = new User("myUsername");
        user.setId(1);
        var entry = entityClassManager.replaceExceptId(user);
        assertEquals("username", entry.getKey());
        assertEquals("'myUsername'", entry.getValue());
    }

    @Test
    void testFormatInsertThrow() {
        assertThrows(RuntimeException.class, () -> entityClassManager.replaceExceptId(new Object()));
    }

    @Test
    void getClassEntities() {
        assertNull(entityClassManager.getClassEntities().get(Class.class));
        assertFalse(entityClassManager.getClassEntities().get(Admin.class).isEmpty());
    }


}