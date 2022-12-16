package fr.sfc.container.common;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListSearchDialog<T> extends Stage {

    private final TextField inputSearch;
    private final ListView<T> listView;
    private final ObservableList<T> observableList;
    private final FilteredList<T> filteredList;
    private final VBox container;
    private final ObjectProperty<T> currentSelected;

    public ListSearchDialog() {
        currentSelected = new SimpleObjectProperty<>();
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
        container = new VBox();
        observableList = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(observableList);
        listView = new ListView<>(filteredList);
        inputSearch = new TextField();
        setScene(new Scene(container));
        setMinHeight(500);
        setMinWidth(250);
        container.getChildren().addAll(inputSearch, listView);

        currentSelected.bind(listView.getSelectionModel().selectedItemProperty());

        listView.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                onClose(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
        );

        listView.addEventHandler(KeyEvent.KEY_PRESSED, event ->
                onClose(event.getCode() == KeyCode.ENTER && currentSelected.get() != null)
        );
    }

    private void onClose(boolean use) {
        if (use) {
            listView.getSelectionModel().clearSelection();
            close();
        }
    }

    public ReadOnlyObjectProperty<T> currentSelectedProperty() {
        return currentSelected;
    }

    public T getCurrentSelectedProperty() {
        return currentSelected.get();
    }

    public VBox getContainer() {
        return container;
    }

    public FilteredList<T> getFilteredList() {
        return filteredList;
    }

    public TextField getInputSearch() {
        return inputSearch;
    }

    public ListView<T> getListView() {
        return listView;
    }

    public ObservableList<T> getObservableList() {
        return observableList;
    }



}
