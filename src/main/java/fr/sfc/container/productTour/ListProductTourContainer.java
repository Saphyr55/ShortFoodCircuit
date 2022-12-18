package fr.sfc.container.productTour;

import fr.sfc.common.Custom;
import fr.sfc.controller.productTour.ListProductTourController;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import fr.sfc.framework.item.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ListProductTourContainer extends GridPane implements Container {

    @AutoController
    private ListProductTourController controller;

    @SetContainer
    @Tag("adder")
    private AdderProductTourContainer adderProductTourContainer;
    
    private final TextField searchTextField;
    private final FilteredList<Custom<ProductTour>> filteredList;
    private final ObservableList<Custom<ProductTour>> observableList;
    private final ListView<Custom<ProductTour>> productTourListView;
    private final HBox hBox;
    private final Button adderProductTourButton;
    private final Button switcherDetailsComponentButton;

    public ListProductTourContainer() {
        hBox = new HBox();
        observableList = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(observableList);
        productTourListView = new ListView<>(filteredList);
        switcherDetailsComponentButton = new Button("Show Map");
        adderProductTourButton = new Button("Add Product Tour");
        searchTextField = new TextField();
    }

    @Override
    public void setup() {

        searchTextField.setPromptText("Search for product tour");
        hBox.getChildren().addAll(
                adderProductTourButton,
                switcherDetailsComponentButton
        );
        addRow(0, searchTextField);
        addRow(1, productTourListView);
        addRow(2, hBox);

        // responsive
        searchTextField.prefWidthProperty().bind(widthProperty());
        searchTextField.prefHeightProperty().bind(heightProperty().multiply(0.05));
        productTourListView.prefWidthProperty().bind(widthProperty());
        productTourListView.prefHeightProperty().bind(heightProperty());
    }

    public void add(Node... nodes) {
        getChildren().addAll(nodes);
    }

    public AdderProductTourContainer getProductTourFrame() {
        return adderProductTourContainer;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    public Button getAdderProductTourButton() {
        return adderProductTourButton;
    }

    public ListView<Custom<ProductTour>> getProductTourListView() {
        return productTourListView;
    }

    public Button getSwitcherDetailsComponentButton() {
        return switcherDetailsComponentButton;
    }

    public ListProductTourController getController() {
        return controller;
    }

    public AdderProductTourContainer getProductTourFrameContainer() {
        return adderProductTourContainer;
    }

    public FilteredList<Custom<ProductTour>> getFilteredList() {
        return filteredList;
    }

    public ObservableList<Custom<ProductTour>> getObservableList() {
        return observableList;
    }


}
