package fr.sfc;

import fr.sfc.api.BackendApplicationConfiguration;
import fr.sfc.api.Resources;
import fr.sfc.api.controlling.PathConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

@PathConfiguration(path = SFCApplication.FILE_CONF)
public final class SFCApplication extends Application {

    public final static String FILE_CONF = "/conf.yaml";

    @Override
    public void start(final Stage primaryStage) throws IOException {

        BackendApplicationConfiguration configuration = BackendApplicationConfiguration.File
                .of(Resources.getFileResource(FILE_CONF)).create();

        configuration.configure();
        configuration.createApplication(primaryStage, "Short Food Circuit", 980, 620).show();
    }

    public static void main(String[] args) {
        try {
            Application.launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}