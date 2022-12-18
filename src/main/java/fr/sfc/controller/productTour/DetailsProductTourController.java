package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.DetailsProductTourContainer;
import fr.sfc.container.productTour.ListProductTourContainer;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.item.Tag;
import fr.sfc.framework.injection.Inject;

public class DetailsProductTourController implements Controller {

    public enum State {
        Map,
        Config
    }

    @AutoContainer
    private DetailsProductTourContainer container;

    @Inject
    @Tag("container:root.list")
    private ListProductTourContainer listProductTourContainer;

    private State state = State.Config;

    @Inject
    private ContainerManager containerManager;

    @Override
    public void setup() {
        switchBetweenMapAndConfig();
    }

    public void switchBetweenMapAndConfig() {

        listProductTourContainer.getSwitcherDetailsComponentButton().setOnAction(event -> {
            switch (state) {
                case Config -> {
                    state = State.Map;
                    listProductTourContainer.getSwitcherDetailsComponentButton().setText("Show Config");
                    container.setFor(container.getMapContainer());
                }
                case Map -> {
                    state = State.Config;
                    listProductTourContainer.getSwitcherDetailsComponentButton().setText("Show Map");
                    container.setFor(container.getSpecifiesProductTourContainer());
                }
            }
        });
    }

    public State getState() {
        return state;
    }
}
