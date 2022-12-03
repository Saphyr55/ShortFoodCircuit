package fr.sfc;

import com.google.common.io.Resources;
import fr.sfc.api.BackendApplication;
import fr.sfc.api.BackendApplicationConfiguration;
import fr.sfc.component.MainComponent;
import fr.sfc.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class SFCApplication extends Application {

    public static URL index;

    @Override
    public void start(final Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Resources.getResource("root.fxml"));

        BackendApplicationConfiguration configuration = BackendApplicationConfiguration.Builder.of()
                .setRoot(root)
                .withEntityPackage("fr.sfc.model.entity")
                .withRepositoryPackage("fr.sfc.model.repository")
                .withDatabaseManager("db.ini", "sfc")
                .build();
        configuration.configure("sfc");
        configuration.createApplication(primaryStage, root, "Short Food Circuit", 980, 620).show();
    }

    public static void main(String[] args) {
        try {
            index = SFCApplication.class.getResource("index.html");
            Application.launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}