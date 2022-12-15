package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.DetailsProductTourContainer;
import fr.sfc.container.productTour.ListProductTourContainer;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;

public class DetailsProductTourController implements Controller {

    public enum State {
        Map,
        Config
    }

    @AutoContainer
    private DetailsProductTourContainer container;

    private State state = State.Config;

    @Inject
    private ContainerManager containerManager;

    @Override
    public void setup() {
        switchBetweenMapAndConfig();
    }

    public void switchBetweenMapAndConfig() {

        ListProductTourContainer adder = containerManager.getContainer("root.adderProductTour");

        adder.getSwitcherDetailsComponentButton().setOnAction(event -> {
            switch (state) {
                case Config -> {
                    state = State.Map;
                    adder.getSwitcherDetailsComponentButton().setText("Show Config");
                    container.setFor(container.getMapContainer());
                }
                case Map -> {
                    state = State.Config;
                    adder.getSwitcherDetailsComponentButton().setText("Show Map");
                    container.setFor(container.getSpecifiesProductTourContainer());
                }
            }
        });
    }

    public State getState() {
        return state;
    }
}
