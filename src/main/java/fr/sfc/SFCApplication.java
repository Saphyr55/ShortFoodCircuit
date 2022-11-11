package fr.sfc;

import fr.sfc.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public final class SFCApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SFCApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello !");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            launch();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}