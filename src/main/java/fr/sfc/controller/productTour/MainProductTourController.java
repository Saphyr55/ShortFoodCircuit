package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.MainProductTourContainer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;

public class MainProductTourController implements Controller {

    @AutoContainer
    private MainProductTourContainer component;

    @Override
    public void setup() {
    }

}
