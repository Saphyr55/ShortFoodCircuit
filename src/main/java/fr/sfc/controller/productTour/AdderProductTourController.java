package fr.sfc.controller.productTour;

import fr.sfc.api.controlling.AutoComponent;
import fr.sfc.api.controlling.Controller;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.component.productTour.AdderProdutTourComponent;
import fr.sfc.repository.ProductTourRepository;
import javafx.collections.ObservableList;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class AdderProductTourController implements Controller {

    @AutoComponent
    private AdderProdutTourComponent component;

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

}
