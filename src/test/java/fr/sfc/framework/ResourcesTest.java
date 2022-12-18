package fr.sfc.framework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesTest {

    @Test
    void testExistResource() {
        assertTrue(ResourcesUtils.getFileResource("/configuration.yaml").exists());
    }
    
    @Test
    void testNotExistResource() {
        assertFalse(ResourcesUtils.getFileResource("/fileWhoNotExist.c").exists());
    }

}