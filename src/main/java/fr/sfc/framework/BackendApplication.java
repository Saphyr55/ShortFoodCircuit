package fr.sfc.framework;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class BackendApplication {

    private static BackendApplication backendApplication;
    private final Stage primaryStage;
    private final Scene scene;
    private final Parent parent;
    private final BackendApplicationConfiguration backendApplicationConfiguration;

    public BackendApplication(final BackendApplicationConfiguration configuration,
                              final Stage primaryStage,
                              final Parent root,
                              final String title,
                              int width,
                              int height) {

        this.parent = root;
        this.primaryStage = primaryStage;
        this.backendApplicationConfiguration = configuration;
        this.scene = new Scene(root, width, height);
        this.primaryStage.setTitle(title);
        this.primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> configuration.getDatabaseManager().shutdown());
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

    public BackendApplicationConfiguration getConfig() {
        return backendApplicationConfiguration;
    }

    public static void set(BackendApplication application) {
        backendApplication = application;
    }

    public static BackendApplication getCurrentApplication() {
        return backendApplication;
    }

    public Parent getParent() {
        return parent;
    }
}
