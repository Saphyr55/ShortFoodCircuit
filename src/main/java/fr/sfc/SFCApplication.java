package fr.sfc;

import fr.sfc.model.entity.Vehicle;
import fr.sfc.model.repository.AdminRepository;
import fr.sfc.persistence.EntityManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public final class SFCApplication extends Application {

    public static URL index;

    public AdminRepository adminRepository;

    @Override
    public void start(Stage primaryStage) throws Exception {
        RuntimeApplication.setup(primaryStage,"SFC", 880, 620);
        RuntimeApplication.run();
    }

    public static void main(String[] args) {
        try {
            RuntimeApplication.init();
            index = SFCApplication.class.getResource("index.html");
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}