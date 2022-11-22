package fr.sfc.component;

import fr.sfc.controller.MainController;
import fr.sfc.controller.AutoController;
import fr.sfc.resource.Resource;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

@Component(source = "main.fxml")
public class MainComponent extends HBox {

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
