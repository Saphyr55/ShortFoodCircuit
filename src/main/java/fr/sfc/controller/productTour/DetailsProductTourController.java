package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.DetailsProductTourContainer;
import fr.sfc.container.productTour.ListProductTourContainer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.framework.item.Tag;

public class DetailsProductTourController implements Controller {

    public enum State {
        Map,
        Specifies
    }

    @AutoContainer
    private DetailsProductTourContainer container;

    @Inject
    @Tag("container:root.producer.list")
    private ListProductTourContainer listProductTourContainer;

    @Inject
    @Tag("controller:root.producer.details.map")
    private MapController mapController;

    private State state = State.Specifies;

    @Override
    public void setup() {
        switchBetweenMapAndConfig();
    }

    public void switchBetweenMapAndConfig() {

        listProductTourContainer.getSwitcherDetailsComponentButton().setOnAction(event -> {

            if (listProductTourContainer.getController().getCurrentProductTour() == null) return;

            switch (state) {
                case Specifies -> {
                    state = State.Map;
                    listProductTourContainer.getSwitcherDetailsComponentButton().setText("Show Specifies");
                    container.setFor(container.getMapContainer());
                    mapController.loadMap();
                }
                case Map -> {
                    state = State.Specifies;
                    listProductTourContainer.getSwitcherDetailsComponentButton().setText("Show Map");
                    container.setFor(container.getSpecifiesProductTourContainer());
                }
            }
        });
    }


}
