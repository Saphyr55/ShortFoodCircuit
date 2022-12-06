package fr.sfc;

import fr.sfc.framework.BackendApplicationConfiguration;
import fr.sfc.framework.Resources;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public final class SFCApplication extends Application {

    public static final String FILE_CONF = "/configuration.yaml";
    // private static final Logger LOGGER = Logger.getLogger(SFCApplication.class);

    @Override
    public void start(final Stage primaryStage) throws IOException {

        // LOGGER.info("Start Application");

        BackendApplicationConfiguration configuration = BackendApplicationConfiguration.File
                .of(Resources.getFileResource(FILE_CONF))
                .create();

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