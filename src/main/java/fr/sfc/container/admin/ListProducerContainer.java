package fr.sfc.container.admin;

import fr.sfc.controller.admin.ListProducerController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ListProducerContainer extends GridPane implements Container {

    @AutoController
    private ListProducerController controller;

    private final Button switchProducerCustomer = new Button();
    private final TextField searchTextField = new TextField();
    private ListView<String> listCell;
    private FilteredList<String> filteredList;
    private ObservableList<String> observableList;

    @Override
    public void setup() {
        observableList = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(observableList);
        listCell = new ListView<>(filteredList);

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

    public FilteredList<String> getFilteredList() {
        return filteredList;
    }

    public ObservableList<String> getObservableList() {
        return observableList;
    }
}
