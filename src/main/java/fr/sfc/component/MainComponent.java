package fr.sfc.component;

import fr.sfc.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainComponent extends HBox {

    private MainComponent self;

    public MainComponent() {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(new MainController());
            self = fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
