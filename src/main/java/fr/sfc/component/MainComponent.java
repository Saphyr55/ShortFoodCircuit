package fr.sfc.component;

import fr.sfc.SFCApplication;
import fr.sfc.api.component.ComponentFXML;
import fr.sfc.api.component.Component;
import fr.sfc.controller.MainController;
import fr.sfc.api.controller.AutoController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.IOException;

@ComponentFXML(resource = "main.fxml")
public class MainComponent extends HBox implements Component {

    @AutoController
    private MainController controller;

    public MainComponent() {
        super();
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(new MainController());
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
