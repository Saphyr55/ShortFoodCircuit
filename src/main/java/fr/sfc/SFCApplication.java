package fr.sfc;

import fr.sfc.components.MainComponent;
import fr.sfc.components.MapViewComponent;

import java.io.IOException;

import fr.sfc.controllers.MainController;
import fr.sfc.database.Database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;

public final class SFCApplication extends Application {


    public static URL index;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(SFCApplication.class.getResource("default.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent, 320, 240);
    public final class SFCApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(SFCApplication.class.getResource("default.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello !");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            index = SFCApplication.class.getResource("index.html");
            launch();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}