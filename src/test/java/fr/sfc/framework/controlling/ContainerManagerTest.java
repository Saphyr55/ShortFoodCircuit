package fr.sfc.framework.controlling;

import fr.sfc.framework.ResourcesUtils;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ContainerManagerTest {

    private ContainerManager containerManager;

    @BeforeEach
    void setUp() throws IOException {
        FXMLLoader loader = new FXMLLoader(ResourcesUtils.getResource("/fxml/root.fxml"));
        containerManager = ContainerLoader.createContainerManager(loader.load());
        containerManager.detect();
    }

    @Test
    void detect() {
        assertDoesNotThrow(() -> {
            FXMLLoader loader = new FXMLLoader(ResourcesUtils.getResource("/fxml/root.fxml"));
            new ContainerLoader(loader.load());
        });
    }

    @Test
    void getContainer() {
        // assertInstanceOf(ContainerTest.class, containerManager.getContainer("root"));
    }

    @Test
    void getController() {
    }

    @Test
    void getAllContainers() {
    }

    @Test
    void getComponentFactory() {
    }

    @Test
    void getComponentControllerMap() {
    }

    @Test
    void getAllControllers() {
    }

    @Test
    void getComponentGraph() {
    }
}