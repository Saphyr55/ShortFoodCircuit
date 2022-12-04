package fr.sfc.controller.productTour;

import fr.sfc.api.BackendApplication;
import fr.sfc.api.controlling.AutoComponent;
import fr.sfc.api.controlling.Controller;
import fr.sfc.component.MainComponent;
import fr.sfc.component.productTour.AdderProdutTourComponent;

public class AdderProductTourController implements Controller {

    @AutoComponent
    private AdderProdutTourComponent component;

    @Override
    public void setup() {
        component.getProductTourListCell().prefWidthProperty().bind(component.widthProperty());
    }

}
