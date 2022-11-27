package fr.sfc;

import fr.sfc.api.RuntimeApplication;
import fr.sfc.api.RuntimeApplicationConfiguration;
import fr.sfc.component.MainComponent;

import fr.sfc.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public final class SFCApplication extends Application {

    public static URL index;

    @Override
    public void start(final Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(SFCApplication.class.getResource("default.fxml")));

        RuntimeApplicationConfiguration configuration = RuntimeApplicationConfiguration.Builder.of()
                .withComponentPackage(root, MainComponent.class)
                .withControllerPackage(MainController.class)
                .withEntityPackage("fr.sfc.model.entity")
                .withRepositoryPackage("fr.sfc.model.repository")
                .withDatabaseManager("db.ini", "sfc")
                .build();

        configuration.configure("sfc");

        RuntimeApplication application = configuration.createApplication(primaryStage, root, "Short Food Circuit", 820, 680);
        application.show();

        primaryStage.setOnCloseRequest(event -> configuration.getDatabaseManager().shutdown());
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