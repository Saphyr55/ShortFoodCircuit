package fr.sfc.controller.productTour;

import fr.sfc.framework.controlling.annotation.AutoComponent;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.component.productTour.DetailsProductTourComponent;

public class DetailsProductTourController implements Controller {

    @AutoComponent
    private DetailsProductTourComponent detailsProductTourComponent;

    @Override
    public void setup() {

    }
}
