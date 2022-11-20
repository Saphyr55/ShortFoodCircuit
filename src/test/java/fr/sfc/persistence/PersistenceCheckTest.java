package fr.sfc.persistence;

import fr.sfc.model.Admin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceCheckTest {

    @Test
    void haveAnnotationEntity() {
        assertTrue(PersistenceCheck.isEntity(Admin.class));
    }

    @Test
    void haveAnnotationTable() {
        assertTrue(PersistenceCheck.isEntity(Admin.class));
    }

    @Test
    void haveAnnotation() {
        assertTrue(PersistenceCheck.haveAnnotation(Admin.class, Entity.class));
    }

    @Test
    void throwHaveNotAnnotation() {
        assertDoesNotThrow( () -> PersistenceCheck.throwHaveNotAnnotation(Admin.class, Entity.class));
        assertThrowsExactly(PersistenceAnnotationPresentException.class, () -> PersistenceCheck.throwHaveNotAnnotation(Admin.class, SuppressWarnings.class));
    }
}