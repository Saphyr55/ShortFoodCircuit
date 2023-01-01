package fr.sfc.container.productTour;

import fr.sfc.common.Pack;
import fr.sfc.container.common.ListSearchDialog;
import fr.sfc.controller.productTour.AdderOrderController;
import fr.sfc.entity.Customer;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import fr.sfc.framework.controlling.annotation.ContainerFXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

@ContainerFXML
public class AdderOrderContainer extends VBox implements Container {

    @AutoController
    private AdderOrderController controller;

    private final ListSearchDialog<Pack<Customer>> listSearchCustomerDialog;
    private final FXMLLoader loader;
    private final Stage stage;

    public AdderOrderContainer() throws IOException {
        stage = new Stage();
        listSearchCustomerDialog = new ListSearchDialog<>();
        loader = new FXMLLoader(Resources.getResource("/fxml/adderOrderContainer.fxml"));
        loader.setRoot(this);
        loader.load();
    }

    @Override
    public void setup() {
        stage.setMinHeight(350);
        stage.setMinWidth(325);
        stage.setScene(new Scene(this));
    }

    public ListSearchDialog<Pack<Customer>> getListSearchCustomerDialog() {
        return listSearchCustomerDialog;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }


}
