package fr.sfc.framework.controlling;

import fr.sfc.framework.ResourcesUtils;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ContainerLoaderTest {

    ContainerLoader containerLoader;

    @BeforeEach
    void setUp() throws IOException {
        FXMLLoader loader = new FXMLLoader(ResourcesUtils.getResource("/fxml/root.fxml"));
        containerLoader = new ContainerLoader(loader.load());
    }

    @Test
    void containsNode() {
        assertFalse(containerLoader.getNodes().isEmpty());
    }


}