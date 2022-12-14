package fr.sfc.container.admin;

import fr.sfc.controller.admin.ListProducerController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ListProducerContainer extends GridPane implements Container {

    @AutoController
    private ListProducerController controller;

    private final TextField searchTextField = new TextField();
    private final ListView<String> listCell = new ListView<>();
    private final Button switchProducerCustomer = new Button();

    @Override
    public void setup() {

        listCell.prefHeightProperty().bind(heightProperty());
        listCell.prefWidthProperty().bind(widthProperty());

        switchProducerCustomer.prefWidthProperty().bind(widthProperty());
        switchProducerCustomer.prefHeightProperty().bind(heightProperty().divide(10));

        searchTextField.setPromptText("Search producer");

        addRow(0, searchTextField);
        addRow(1, listCell);
        addRow(2, switchProducerCustomer);
    }

    public ListView<String> getListCell() {
        return listCell;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    public Button getSwitchProducerCustomer() {
        return switchProducerCustomer;
    }
}
