package fr.sfc.controller;

import fr.sfc.api.component.AutoComponent;
import fr.sfc.component.MainComponent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @AutoComponent
    private MainComponent component;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
