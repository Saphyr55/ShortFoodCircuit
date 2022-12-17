package fr.sfc.common;

import fr.sfc.entity.AdminTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomTest {

    private Custom<AdminTest> adminTestCustom;

    @BeforeEach
    void setUp() {
        adminTestCustom = Custom.of(new AdminTest(), adminTest1 -> "test");
    }

    @Test
    void testToString() {
        assertEquals(adminTestCustom.toString(), "test");
    }

}