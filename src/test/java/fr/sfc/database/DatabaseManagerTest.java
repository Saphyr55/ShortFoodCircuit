package fr.sfc.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {

    @Test
    void init() {
        assertDoesNotThrow(DatabaseManager::init);
    }
}