package fr.sfc.api.core;

import fr.sfc.api.common.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    @Test
    void distanceTo() {
        assertEquals(4188563.8f, new Location("Test", 32, 32)
                .distanceTo(new Location("Test2", 64, 64)));
    }

}