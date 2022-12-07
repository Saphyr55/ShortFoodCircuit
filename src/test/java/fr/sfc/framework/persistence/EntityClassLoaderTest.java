package fr.sfc.framework.persistence;

import fr.sfc.framework.entity.Admin;
import fr.sfc.framework.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class EntityClassLoaderTest {

    EntityClassLoader entityClassLoader;

    @BeforeEach
    void setUp() {
        entityClassLoader = new EntityClassLoader();
        entityClassLoader.setClassesName(Collections.singletonList(Admin.class.getName()));
        entityClassLoader.setEntityPackage("fr.sfc.framework.entity");
        entityClassLoader.load();
    }

    @Test
    void testContainsClassFromClassname() {
        assertFalse(entityClassLoader.getClasses().get(Admin.class).isEmpty());

    }

    @Test
    void testContainsClassFromPackage() {
        assertFalse(entityClassLoader.getClasses().get(User.class).isEmpty());
    }

}