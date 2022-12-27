package fr.sfc.container.admin;

import fr.sfc.controller.admin.MainAdminController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import fr.sfc.framework.item.Tag;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MainAdminContainer extends GridPane implements Container {

    @AutoController
    private MainAdminController controller;

    @SetContainer
    @Tag(value = "list")
    private ListProducerCustomerContainer listProducerCustomer;
    @SetContainer
    private SpecificsProducerDataContainer specificsDataProducer;
    @SetContainer
    private SpecificsProducerTextContainer specificsTextProducer;
    @SetContainer
    private SpecificsCustomerDataContainer specificsDataCustomer;
    @SetContainer
    private SpecificsCustomerTextContainer specificsTextCustomer;

    private final Pane detailsPane = new Pane();
    private final HBox detailsProducer = new HBox();
    private final HBox detailsCustomer = new HBox();

    @Override
    public void setup() {

        // Producer container
        detailsProducer.getChildren().add(specificsTextProducer);
        detailsProducer.getChildren().add(specificsDataProducer);

        // Customer container
        detailsCustomer.getChildren().add(specificsTextCustomer);
        detailsCustomer.getChildren().add(specificsDataCustomer);

        detailsPane.getChildren().setAll(detailsProducer);

        responsive();

        addColumn(0, listProducerCustomer);
        addColumn(1, detailsPane);
    }

    public void update() {
        listProducerCustomer.getObservableList().setAll();
        listProducerCustomer.getListView().refresh();
    }

    private void responsive() {

        detailsPane.prefHeightProperty().bind(heightProperty());
        detailsPane.prefWidthProperty().bind(widthProperty());

        detailsProducer.minHeightProperty().bind(detailsPane.heightProperty());
        detailsProducer.prefWidthProperty().bind(detailsPane.widthProperty());
        specificsDataProducer.minHeightProperty().bind(detailsProducer.heightProperty());
        specificsDataProducer.prefWidthProperty().bind(detailsProducer.widthProperty().divide(2));
        specificsTextProducer.minHeightProperty().bind(detailsProducer.heightProperty());
        specificsTextProducer.prefWidthProperty().bind(detailsProducer.widthProperty().divide(2));

        detailsCustomer.minHeightProperty().bind(detailsPane.heightProperty());
        detailsCustomer.prefWidthProperty().bind(detailsPane.widthProperty());
        specificsDataCustomer.minHeightProperty().bind(detailsCustomer.heightProperty());
        specificsDataCustomer.prefWidthProperty().bind(detailsCustomer.widthProperty().divide(2));
        specificsTextCustomer.minHeightProperty().bind(detailsCustomer.heightProperty());
        specificsTextCustomer.prefWidthProperty().bind(detailsCustomer.widthProperty().divide(2));

        listProducerCustomer.prefHeightProperty().bind(heightProperty());
        listProducerCustomer.prefWidthProperty().bind(widthProperty().divide(3.5d));
        listProducerCustomer.setMinWidth(300);

    }

    public MainAdminController getController() {
        return controller;
    }

    public ListProducerCustomerContainer getListProducerCustomer() {
        return listProducerCustomer;
    }

    public SpecificsProducerDataContainer getSpecificsDataProducer() {
        return specificsDataProducer;
    }

    public SpecificsProducerTextContainer getSpecificsTextProducer() {
        return specificsTextProducer;
    }


    public Pane getDetailsPane() {
        return detailsPane;
    }

    public SpecificsCustomerTextContainer getSpecificsTextCustomer() {
        return specificsTextCustomer;
    }

    public SpecificsCustomerDataContainer getSpecificsDataCustomer() {
        return specificsDataCustomer;
    }

    public HBox getDetailsCustomer() {
        return detailsCustomer;
    }

    public HBox getDetailsProducer() {
        return detailsProducer;
    }

}
