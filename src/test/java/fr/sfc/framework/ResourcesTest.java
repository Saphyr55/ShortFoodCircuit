package fr.sfc.framework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesTest {

    @Test
    void testExistResource() {
        assertTrue(Resources.getFileResource("/configuration.yaml").exists());
    }
    
    @Test
    void testNotExistResource() {
        assertFalse(Resources.getFileResource("/fileWhoNotExist.c").exists());
    }

}