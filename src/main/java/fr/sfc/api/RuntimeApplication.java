package fr.sfc.api;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RuntimeApplication {

    private static RuntimeApplication runtimeApplication;
    private final Stage primaryStage;
    private final Scene scene;
    private RuntimeApplicationConfiguration runtimeApplicationConfiguration;

    public RuntimeApplication(Stage primaryStage, Parent parent, RuntimeApplicationConfiguration configuration) {
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

    public static RuntimeApplication application() {
        return runtimeApplication;
    }

    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
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

    public static RuntimeApplication get() {
        return runtimeApplication;
    }

}
