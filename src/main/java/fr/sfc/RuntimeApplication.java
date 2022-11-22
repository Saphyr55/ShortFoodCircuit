package fr.sfc;

import fr.sfc.core.process.RuntimeEntity;
import fr.sfc.database.DatabaseManager;
import fr.sfc.model.entity.Admin;
import fr.sfc.persistence.EntityManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.annotation.processing.Processor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ServiceLoader;
import java.util.Set;

public class RuntimeApplication {


    public final static URL defaultPageURL = SFCApplication.class.getResource("default.fxml");
    private Stage primaryStage;
    private Scene scene;
    private static RuntimeApplication runtimeApplication;

    private RuntimeApplication(Stage primaryStage, String title, int width, int height) throws IOException {
        this.primaryStage = primaryStage;
        this.scene = new Scene(new FXMLLoader(defaultPageURL).load(), width, height);
        this.primaryStage.setTitle(title);
        this.primaryStage.setScene(scene);
    }

    public static RuntimeApplication setup(Stage primaryStage, String title, int width, int height) throws IOException {
        return (runtimeApplication = new RuntimeApplication(primaryStage, title, width, height));
    }

    public static void init(String... args) {
        DatabaseManager.init();
        RuntimeEntity runtimeEntity = new RuntimeEntity();
        runtimeEntity.run();
        EntityManager entityManager = new EntityManager(runtimeEntity);
        entityManager.findAll(Admin.class);
    }

    public static void run() {
        RuntimeApplication.runtimeApplication.primaryStage.show();
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

}
