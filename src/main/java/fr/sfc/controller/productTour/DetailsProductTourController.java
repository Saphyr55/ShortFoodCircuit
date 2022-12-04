package fr.sfc.controller.productTour;

import fr.sfc.api.controlling.AutoComponent;
import fr.sfc.api.controlling.Controller;
import fr.sfc.component.productTour.DetailsProductTourComponent;

public class DetailsProductTourController implements Controller {

    @AutoComponent
    private DetailsProductTourComponent detailsProductTourComponent;

    @Override
    public void setup() {

    }
}
