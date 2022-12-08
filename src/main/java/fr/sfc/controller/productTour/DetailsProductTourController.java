package fr.sfc.controller.productTour;

import fr.sfc.component.productTour.AdderProdutTourComponent;
import fr.sfc.framework.controlling.ComponentManager;
import fr.sfc.framework.controlling.annotation.AutoComponent;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.component.productTour.DetailsProductTourComponent;
import fr.sfc.framework.persistence.annotation.Inject;

public class DetailsProductTourController implements Controller {

    @AutoComponent
    private DetailsProductTourComponent self;

    private State state = State.Config;

    @Inject
    private ComponentManager componentManager;


    @Override
    public void setup() {
        switchBetweenMapAndConfig();
    }

    public void switchBetweenMapAndConfig() {
        AdderProdutTourComponent adder = componentManager.getComponent("root.adderProductTour");
        adder.getSwitcherDetailsComponentButton().setOnAction(event -> {
            switch (state) {
                case Config -> {
                    adder.getSwitcherDetailsComponentButton().setText("Show Config");
                    self.setFor(self.getMapComponent());
                    state = State.Map;
                }
                case Map -> {
                    adder.getSwitcherDetailsComponentButton().setText("Show Map");
                    self.setFor(self.getConfigProductTourComponent());
                    state = State.Config;
                }
            }
        });
    }

    public enum State {
        Map,
        Config
    }

    public State getState() {
        return state;
    }
}
