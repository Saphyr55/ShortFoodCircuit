package fr.sfc.framework.controlling;

import fr.sfc.framework.Resources;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ContainerLoaderTest {

    ContainerLoader containerLoader;

    @BeforeEach
    void setUp() throws IOException {
        containerLoader = new ContainerLoader(
                FXMLLoader.load(Resources.getResource("/fxml/root.fxml")));
    }

    @Test
    void containsNode() {
        assertFalse(containerLoader.getNodes().isEmpty());
    }


}