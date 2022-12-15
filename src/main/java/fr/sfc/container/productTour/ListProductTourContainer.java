package fr.sfc.container.productTour;

import fr.sfc.controller.productTour.ListProductTourController;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class ListProductTourContainer extends GridPane implements Container {

    @AutoController
    private ListProductTourController controller;

    @SetContainer
    private ProductTourFrameContainer productTourFrameContainer;

    private TextField searchTextField;
    private List<ProductTour> productTourList;
    private ListView<String> productTourListCell;
    private Button adderProductTourButton;
    private Button switcherDetailsComponentButton;

    @Override
    public void setup() {
        productTourList = new ArrayList<>();
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

    public List<ProductTour> getProductTourList() {
        return productTourList;
    }

    public ProductTourFrameContainer getProductTourFrame() {
        return productTourFrameContainer;
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

    public ListProductTourController getController() {
        return controller;
    }

    public ProductTourFrameContainer getProductTourFrameContainer() {
        return productTourFrameContainer;
    }

    public ListView<String> getProductTourListCell() {
        return productTourListCell;
    }
}
