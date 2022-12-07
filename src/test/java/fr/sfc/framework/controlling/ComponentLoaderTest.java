package fr.sfc.framework.controlling;

import fr.sfc.framework.Resources;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ComponentLoaderTest {

    ComponentLoader componentLoader;

    @BeforeEach
    void setUp() throws IOException {
        componentLoader = new ComponentLoader(
                FXMLLoader.load(Resources.getResource("/fxml/root.fxml")));
    }

    @Test
    void containsNode() {
        assertFalse(componentLoader.getNodes().isEmpty());
    }


}