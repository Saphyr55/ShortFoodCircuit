package fr.sfc.framework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BackendApplicationConfigurationTest {


    @Test
    void testYamlFileConfig() {
        assertDoesNotThrow(() -> BackendApplicationConfiguration.File
                    .of(ResourcesUtils.getFileResource("/configuration.yaml"))
                    .create()
        );
    }
    
    @Test
    void testThrowYamlFileConfig() {
        assertThrows(RuntimeException.class, () -> BackendApplicationConfiguration.File
                .of(ResourcesUtils.getFileResource("fileWhoDoesntExist.yaml"))
                .create()
        );
    }

}