package fr.sfc.api;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class RuntimeApplication {

    private static RuntimeApplication runtimeApplication;
    private final Stage primaryStage;
    private final Scene scene;
    private final Parent parent;
    private final RuntimeApplicationConfiguration runtimeApplicationConfiguration;

    public RuntimeApplication(final RuntimeApplicationConfiguration configuration,
                              final Stage primaryStage, final Parent parent,
                              final String title, int width, int height) {
        this.primaryStage = primaryStage;
        this.runtimeApplicationConfiguration = configuration;
        this.scene = new Scene(parent, width, height);
        this.primaryStage.setTitle(title);
        this.primaryStage.setScene(scene);
        this.parent = parent;
    }

    public void show() {
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getScene() {
        return scene;
    }

    public RuntimeApplicationConfiguration getConfig() {
        return runtimeApplicationConfiguration;
    }

    public static void set(RuntimeApplication application) {
        runtimeApplication = application;
    }

    public static RuntimeApplication getCurrentApplication() {
        return runtimeApplication;
    }

    public Parent getParent() {
        return parent;
    }
}
