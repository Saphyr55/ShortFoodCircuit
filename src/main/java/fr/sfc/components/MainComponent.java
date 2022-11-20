package fr.sfc.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainComponent extends HBox implements IComponent<MainComponent> {

    private MainComponent self;

    public MainComponent() {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            self = fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public MainComponent getSelf() {
        return self;
    }
}
