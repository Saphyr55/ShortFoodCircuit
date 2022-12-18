package fr.sfc.container.admin;

import fr.sfc.controller.admin.MainAdminController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.SetContainer;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MainAdminContainer extends GridPane implements Container {

    @AutoController
    private MainAdminController controller;

    @SetContainer
    private ListProducerContainer listProducer;
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
    private Pane parent;

    @Override
    public void setup() {
        parent = (Pane) getParent();

        // Producer container
        detailsProducer.getChildren().add(specificsTextProducer);
        detailsProducer.getChildren().add(specificsDataProducer);

        // Customer container
        detailsCustomer.getChildren().add(specificsTextCustomer);
        detailsCustomer.getChildren().add(specificsDataCustomer);

        detailsPane.getChildren().setAll(detailsProducer);

        responsive();

        addColumn(0, listProducer);
        addColumn(1, detailsPane);
    }

    private void responsive() {

        prefHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());

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

        listProducer.prefHeightProperty().bind(heightProperty());
        listProducer.prefWidthProperty().bind(widthProperty().divide(3.5d));
        listProducer.setMinWidth(300);

    }

    public MainAdminController getController() {
        return controller;
    }

    public ListProducerContainer getListProducer() {
        return listProducer;
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
