package fr.sfc.component.productTour;

import fr.sfc.api.controlling.AutoController;
import fr.sfc.api.controlling.Component;
import fr.sfc.controller.productTour.AdderProductTourController;
import fr.sfc.entity.ProductTour;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AdderProdutTourComponent extends GridPane implements Component {

    @AutoController
    private AdderProductTourController controller;

    private TextField searchTextField;
    private ListView<String> productTourListCell;
    private Button adderProductTourButton;

    @Override
    public void setup() {
        productTourListCell = new ListView<>();
        adderProductTourButton = new Button("Add Product Tour");
        searchTextField = new TextField();
        searchTextField.setPromptText("Search for product tour");
        addRow(0, searchTextField);
        addRow(1, productTourListCell);
        addRow(2, adderProductTourButton);
    }

    public void add(Node... nodes) {
        getChildren().addAll(nodes);
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    public Button getAdderProductTourButton() {
        return adderProductTourButton;
    }

    public ListView<String> getProductTourListView() {
        return productTourListCell;
    }

}
