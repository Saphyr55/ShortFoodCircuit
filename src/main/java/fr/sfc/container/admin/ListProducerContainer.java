package fr.sfc.container.admin;

import javax.swing.text.html.ListView;

import fr.sfc.controller.admin.ListProducerController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ListProducerContainer extends GridPane implements Container {

    @AutoController
    private ListProducerController controller;

    @SetContainer
    private AdderProducerContainer adderProducerContainer;

    private final GridPane containerBottomButtons = new GridPane();
    private final Button adderButton = new Button("+");
    private final Button switchProducerCustomer = new Button();
    private final TextField searchTextField = new TextField();
    private ListView<String> listView;
    private FilteredList<String> filteredList;
    private ObservableList<String> observableList;

    private Stage stage = new Stage();
    private Scene scene;

    @Override
    public void setup() {
        scene = new Scene(adderProducerContainer, 800, 600);
        stage.setScene(scene);

        observableList = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(observableList);
        listView = new ListView<>(filteredList);

        containerBottomButtons.addColumn(1, switchProducerCustomer);
        containerBottomButtons.addColumn(2, adderButton);
        listView.prefHeightProperty().bind(heightProperty());

        containerBottomButtons.prefWidthProperty().bind(widthProperty());
        containerBottomButtons.prefHeightProperty().bind(heightProperty().divide(10));

        switchProducerCustomer.prefHeightProperty().bind(containerBottomButtons.heightProperty());
        switchProducerCustomer.prefWidthProperty().bind(containerBottomButtons.widthProperty().divide(0.75));
        adderButton.prefHeightProperty().bind(containerBottomButtons.heightProperty());
        adderButton.prefWidthProperty().bind(containerBottomButtons.widthProperty());

        searchTextField.setPromptText("Search producer");

        addRow(0, searchTextField);
        addRow(1, listView);
        addRow(2, containerBottomButtons);
    }

    public Stage getStage() {
        return stage;
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

}
