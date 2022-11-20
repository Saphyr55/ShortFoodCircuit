package fr.sfc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public final class SFCApplication extends Application {

    public static URL index;
    public static RuntimeApplication application;

    @Override
    public void start(Stage stage) throws Exception {
        application = RuntimeApplication.setup(stage,"SFC", 880, 620);
        RuntimeApplication.run();
    }

    public static void main(String[] args) {
        try {
            index = SFCApplication.class.getResource("index.html");
            RuntimeApplication.init();
            Application.launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}