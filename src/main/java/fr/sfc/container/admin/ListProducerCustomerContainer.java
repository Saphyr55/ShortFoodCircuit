package fr.sfc.container.admin;

import fr.sfc.controller.admin.ListProducerCustomerController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ListProducerCustomerContainer extends GridPane implements Container {

    @AutoController
    private ListProducerCustomerController controller;

    @SetContainer
    private AdderProducerContainer adderProducerContainer;
    @SetContainer
    private AdderCustomerContainer adderCustomerContainer;

    private final GridPane containerBottomButtons = new GridPane();
    private final Button adderButton = new Button("Ajout\u00E9");
    private final Button deleteButton = new Button("Supprimer");
    private final Button switchProducerCustomer = new Button("Client | Producteur");
    private final TextField searchTextField = new TextField();
    private ListView<String> listView;
    private FilteredList<String> filteredList;
    private ObservableList<String> observableList;
    private final Stage adderProducerStage = new Stage();
    private final Stage adderCustomerStage = new Stage();

    @Override
    public void setup() {
        adderProducerStage.setScene(new Scene(adderProducerContainer, 550, 400));
        adderCustomerStage.setScene(new Scene(adderCustomerContainer, 550, 400));

        observableList = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(observableList);
        listView = new ListView<>(filteredList);

        containerBottomButtons.addColumn(1, switchProducerCustomer);
        containerBottomButtons.addColumn(2, adderButton);
        containerBottomButtons.addColumn(3, deleteButton);
        listView.prefHeightProperty().bind(heightProperty());

        responsive();

        searchTextField.setPromptText("Rechercher...");

        addRow(0, searchTextField);
        addRow(1, listView);
        addRow(2, containerBottomButtons);
    }

    private void responsive() {
        containerBottomButtons.prefWidthProperty().bind(widthProperty());
        containerBottomButtons.prefHeightProperty().bind(heightProperty().divide(10));

        switchProducerCustomer.prefHeightProperty().bind(containerBottomButtons.heightProperty());
        switchProducerCustomer.prefWidthProperty().bind(containerBottomButtons.widthProperty());
        adderButton.prefWidthProperty().bind(containerBottomButtons.widthProperty().multiply(0.25));
        deleteButton.prefWidthProperty().bind(containerBottomButtons.widthProperty().multiply(0.25));
        switchProducerCustomer.prefWidthProperty().bind(containerBottomButtons.widthProperty().multiply(0.50));
    }

    public Stage getAdderProducerStage() {
        return adderProducerStage;
    }

    public Stage getAdderCustomerStage() {
        return adderCustomerStage;
    }

    public ListView<String> getListView() {
        return listView;
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

    public Button getAdderButton() {
        return adderButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}
