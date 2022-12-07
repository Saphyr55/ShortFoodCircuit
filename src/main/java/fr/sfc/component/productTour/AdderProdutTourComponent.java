package fr.sfc.component.productTour;

import fr.sfc.controller.productTour.AdderProductTourController;
import fr.sfc.framework.controlling.Component;
import fr.sfc.framework.controlling.annotation.AutoController;
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
    private Button showMapButton;

    @Override
    public void setup() {
        showMapButton = new Button("Map");
        productTourListCell = new ListView<>();
        adderProductTourButton = new Button("Add Product Tour");
        searchTextField = new TextField();
        searchTextField.setPromptText("Search for product tour");
        addRow(0, searchTextField);
        addRow(1, productTourListCell);
        addRow(2, adderProductTourButton);
        addRow(3, showMapButton);
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

    public Button getShowMapButton() {
        return showMapButton;
    }
}
