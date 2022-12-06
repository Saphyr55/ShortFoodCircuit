package fr.sfc.framework.controlling;

import fr.sfc.framework.Resources;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ComponentManagerTest {

    ComponentManager componentManager;

    @BeforeEach
    void setUp() throws IOException {
        componentManager = new ComponentLoader(FXMLLoader
                .load(Resources.getResource("/fxml/root.fxml")))
                .createComponentManager();
    }

    @Test
    void testSize() {
        assertEquals(1, componentManager.getAllComponents().size());
    }

    @Test
    void testContains() {
        componentManager.getComponent("");
    }

}