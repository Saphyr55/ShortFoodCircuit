package fr.sfc.api;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RuntimeApplication {

    private static RuntimeApplication runtimeApplication;
    private final Stage primaryStage;
    private final Scene scene;
    private final RuntimeApplicationConfiguration runtimeApplicationConfiguration;

    public RuntimeApplication(final Stage primaryStage, final Parent parent, final RuntimeApplicationConfiguration configuration) {
        this.primaryStage = primaryStage;
        this.runtimeApplicationConfiguration = configuration;
        this.scene = new Scene(parent,
                configuration.getInitialWidth(),
                configuration.getInitialHeight()
        );
        this.primaryStage.setTitle(configuration.getInitialTitle());
        this.primaryStage.setScene(scene);
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

}
