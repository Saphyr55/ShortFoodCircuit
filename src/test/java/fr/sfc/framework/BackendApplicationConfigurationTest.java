package fr.sfc.framework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BackendApplicationConfigurationTest {


    @Test
    void testYamlFileConfig() {
        assertDoesNotThrow(() -> BackendApplicationConfiguration.File
                    .of(Resources.getFileResource("/configuration.yaml"))
                    .create()
        );
    }

    @Test
    void testThrowYamlFileConfig() {
        assertThrows(RuntimeException.class, () -> BackendApplicationConfiguration.File
                .of(Resources.getFileResource("fileWhoDoesntExist.yaml"))
                .create()
        );
    }

}