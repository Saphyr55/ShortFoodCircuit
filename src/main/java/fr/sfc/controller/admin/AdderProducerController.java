package fr.sfc.controller.admin;

import fr.sfc.container.admin.AdderProducerContainer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;

public class AdderProducerController implements Controller {

    @AutoContainer
    private AdderProducerContainer container;

    @Override
    public void setup() {

    }


}
