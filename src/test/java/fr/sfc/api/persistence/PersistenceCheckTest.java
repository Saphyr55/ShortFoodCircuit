package fr.sfc.api.persistence;

import fr.sfc.api.persistence.annotation.Table;
import fr.sfc.api.persistence.exception.PersistenceAnnotationPresentException;
import fr.sfc.model.entity.Admin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceCheckTest {

    @Test
    void haveAnnotationTable() {
        assertTrue(PersistenceCheck.isTable(Admin.class));
    }

    @Test
    void haveAnnotation() {
        assertTrue(PersistenceCheck.haveAnnotation(Admin.class, Table.class));
    }

    @Test
    void throwHaveNotAnnotation() {
        assertDoesNotThrow( () -> PersistenceCheck.throwHaveNotAnnotation(Admin.class, Table.class));
        assertThrowsExactly(PersistenceAnnotationPresentException.class, () -> PersistenceCheck.throwHaveNotAnnotation(Admin.class, SuppressWarnings.class));
    }
}