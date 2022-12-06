package fr.sfc.framework.persistence;

import fr.sfc.framework.entity.Admin;
import fr.sfc.framework.persistence.annotation.Table;
import fr.sfc.framework.persistence.exception.PersistenceAnnotationPresentException;
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
        assertDoesNotThrow(() -> PersistenceCheck.throwHaveNotAnnotation(Admin.class, Table.class));
        assertThrowsExactly(PersistenceAnnotationPresentException.class, () -> PersistenceCheck.throwHaveNotAnnotation(Admin.class, SuppressWarnings.class));
    }
}