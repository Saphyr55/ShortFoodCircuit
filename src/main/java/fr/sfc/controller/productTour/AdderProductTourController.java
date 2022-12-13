package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.AdderProdutTourContainer;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.ProductTourRepository;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class AdderProductTourController implements Controller {

    @AutoContainer
    private AdderProdutTourContainer component;

    @Inject
    private ContainerManager containerManager;

    @Inject
    private ProductTourRepository productTourRepository;

    @Override
    public void setup() {

        final var productTours = productTourRepository.findAll();

        component.getProductTourListView().getItems().addAll(productTours.stream()
                .map(productTour -> productTour.getName() + " | Commenc\u00E9 le " + productTour.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .collect(Collectors.toSet()));

        component.getProductTourListView().prefWidthProperty().bind(component.widthProperty());
        component.getProductTourListView().prefHeightProperty().bind(component.heightProperty().subtract(100));


    }

    public void switchBetweenMapAndConfig() {

    }


}
