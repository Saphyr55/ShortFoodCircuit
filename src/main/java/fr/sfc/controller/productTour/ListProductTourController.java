package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.ListProductTourContainer;
import fr.sfc.container.productTour.SpecifiesProductTourContainer;
import fr.sfc.entity.Order;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.OrderRepository;
import fr.sfc.repository.ProductTourRepository;
import javafx.beans.value.ObservableValue;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class ListProductTourController implements Controller {

    @AutoContainer
    private ListProductTourContainer container;

    @Inject
    private ContainerManager containerManager;

    @Inject
    private ProductTourRepository productTourRepository;

    @Inject
    private OrderRepository orderRepository;

    @Override
    public void setup() {

        // on récupère les tournées
        container.getProductTourList().addAll(productTourRepository.findAll());

        // On ajoute les tournées dans la liste
        container.getProductTourListView().getItems().addAll(
                container.getProductTourList().stream()
                .map(this::toStringPt)
                .collect(Collectors.toSet()));

        // on récupère la selection
        container.getProductTourListView().getSelectionModel().selectedIndexProperty().addListener(this::selectItem);

        // responsive
        container.getProductTourListView().prefWidthProperty().bind(container.widthProperty());
        container.getProductTourListView().prefHeightProperty().bind(container.heightProperty().subtract(100));

        // Ouvre la fenêtre d'ajout de tournée en appuyant sur le button
        container.getAdderProductTourButton().setOnAction(event -> container.getProductTourFrame().getFrame().show());

    }

    private String toStringPt(ProductTour productTour) {
        return productTour.getName() + " | Commenc\u00E9 le " +
                productTour.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private void selectItem(ObservableValue<? extends Number> observableV,
                            Number oldV,
                            Number newV) {

        // Si on n'a rien sélectionné on quitte la methode
        if (newV.intValue() == -1) return;

        ProductTour ptSelected = container.getProductTourList().get(newV.intValue());
        Set<Order> orders =  orderRepository.findByProductTour(ptSelected);

        SpecifiesProductTourContainer specifiesProductTourContainer =
                containerManager.getContainer("root.details.specifies");

        specifiesProductTourContainer.getController().setProductTour(ptSelected);
        specifiesProductTourContainer.getController().setOrders(new ArrayList<>(orders));
        specifiesProductTourContainer.getController().refresh();
    }

}
