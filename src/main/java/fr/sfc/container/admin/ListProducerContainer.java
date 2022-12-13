package fr.sfc.container.admin;

import fr.sfc.controller.admin.ListProducerController;
import fr.sfc.entity.Producer;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.Set;

public class ListProducerContainer extends GridPane implements Container {

    @AutoController
    private ListProducerController controller;

    private TextField searchTextField = new TextField();
    private ListView<String> producerListCell = new ListView<>();
    private Button switchProducerClient = new Button();

    @Override
    public void setup() {

        producerListCell.prefHeightProperty().bind(heightProperty());
        producerListCell.prefWidthProperty().bind(widthProperty());

        switchProducerClient.prefWidthProperty().bind(widthProperty());
        switchProducerClient.prefHeightProperty().bind(heightProperty().divide(10));

        searchTextField.setPromptText("Search producer");

        addRow(0, searchTextField);
        addRow(1, producerListCell);
        addRow(2, switchProducerClient);
    }

    public ListView<String> getProducerListCell() {
        return producerListCell;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }
}
