package fr.sfc.component.productTour;

import fr.sfc.api.controlling.Component;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AdderProdutTourComponent extends VBox implements Component {

    private TextField searchTextField;

    @Override
    public void setup() {
        searchTextField = new TextField();
        searchTextField.setPromptText("Search for product tour");
        getChildren().add(searchTextField);
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }
}
