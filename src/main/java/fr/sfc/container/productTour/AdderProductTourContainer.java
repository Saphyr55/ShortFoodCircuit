package fr.sfc.container.productTour;

import fr.sfc.controller.productTour.AdderProductTourController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AdderProductTourContainer extends GridPane implements Container {

    @AutoController
    private AdderProductTourController controller;

    @SetContainer
    private ProductTourFrame productTourFrame;

    private TextField searchTextField;
    private ListView<String> productTourListCell;
    private Button adderProductTourButton;
    private Button switcherDetailsComponentButton;

    @Override
    public void setup() {
        switcherDetailsComponentButton = new Button("Show Map");
        productTourListCell = new ListView<>();
        adderProductTourButton = new Button("Add Product Tour");
        searchTextField = new TextField();
        searchTextField.setPromptText("Search for product tour");
        addRow(0, searchTextField);
        addRow(1, productTourListCell);
        addRow(2, adderProductTourButton);
        addRow(3, switcherDetailsComponentButton);
    }

    public void add(Node... nodes) {
        getChildren().addAll(nodes);
    }

    public ProductTourFrame getProductTourFrame() {
        return productTourFrame;
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

    public Button getSwitcherDetailsComponentButton() {
        return switcherDetailsComponentButton;
    }
}
