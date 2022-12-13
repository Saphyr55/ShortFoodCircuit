package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.AdderProductTourContainer;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.ProductTourRepository;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class AdderProductTourController implements Controller {

    @AutoContainer
    private AdderProductTourContainer container;

    @Inject
    private ContainerManager containerManager;

    @Inject
    private ProductTourRepository productTourRepository;

    @Override
    public void setup() {

        final var productTours = productTourRepository.findAll();

        container.getProductTourListView().getItems().addAll(productTours.stream()
                .map(productTour -> productTour.getName() + " | Commenc\u00E9 le " + productTour.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .collect(Collectors.toSet()));

        container.getProductTourListView().prefWidthProperty().bind(container.widthProperty());
        container.getProductTourListView().prefHeightProperty().bind(container.heightProperty().subtract(100));
        container.getAdderProductTourButton().setOnAction(event -> {
            container.getProductTourFrame().getFrame().show();
            System.out.println("test");
        });

    }

    public void switchBetweenMapAndConfig() {

    }


}
