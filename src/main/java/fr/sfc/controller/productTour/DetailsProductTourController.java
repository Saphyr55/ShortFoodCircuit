package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.AdderProductTourContainer;
import fr.sfc.container.productTour.DetailsProductTourContainer;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.persistence.annotation.Inject;

public class DetailsProductTourController implements Controller {

    @AutoContainer
    private DetailsProductTourContainer self;

    private State state = State.Config;

    @Inject
    private ContainerManager containerManager;

    @Override
    public void setup() {
        switchBetweenMapAndConfig();
    }

    public void switchBetweenMapAndConfig() {

        AdderProductTourContainer adder = containerManager.getContainer("root.adderProductTour");

        adder.getSwitcherDetailsComponentButton().setOnAction(event -> {
            switch (state) {
                case Config -> {
                    adder.getSwitcherDetailsComponentButton().setText("Show Config");
                    self.setFor(self.getMapContainer());
                    state = State.Map;
                }
                case Map -> {
                    adder.getSwitcherDetailsComponentButton().setText("Show Map");
                    self.setFor(self.getConfigProductTourContainer());
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
