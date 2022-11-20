package fr.sfc.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable  {


    @FXML
    private WebView map;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final WebEngine webEngine = map.getEngine();
        webEngine.load("https://www.google.com/maps/");
    }

}