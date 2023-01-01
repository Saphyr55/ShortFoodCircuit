package fr.sfc.common;

import fr.sfc.entity.AdminTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackTest {

    private Pack<AdminTest> adminTestPack;

    @BeforeEach
    void setUp() {
        adminTestPack = Pack.of(new AdminTest(), adminTest1 -> "test");
    }

    @Test
    void testToString() {
        assertEquals(adminTestPack.toString(), "test");
    }

}