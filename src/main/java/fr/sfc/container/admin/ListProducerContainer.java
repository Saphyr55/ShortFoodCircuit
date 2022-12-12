package fr.sfc.container.admin;

import fr.sfc.controller.admin.ListProducerController;
import fr.sfc.entity.Producer;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.Set;

public class ListProducerContainer extends GridPane implements Container {

    @AutoController
    private ListProducerController controller;

    private TextField searchTextField;
    private ListView<String> producerListCell;

    @Override
    public void setup() {
        producerListCell = new ListView<>();
        searchTextField = new TextField();

        producerListCell.prefHeightProperty().bind(heightProperty());
        producerListCell.prefWidthProperty().bind(heightProperty());

        searchTextField.setPromptText("Search producer");

        addRow(0, searchTextField);
        addRow(1, producerListCell);

    }

    public ListView<String> getProducerListCell() {
        return producerListCell;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }
}
