package fr.sfc.controller.productTour;

import fr.sfc.common.Custom;
import fr.sfc.common.IconsType;
import fr.sfc.container.productTour.ListProductTourContainer;
import fr.sfc.container.productTour.SpecifiesProductTourContainer;
import fr.sfc.entity.Order;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.framework.item.Tag;
import fr.sfc.repository.OrderRepository;
import fr.sfc.repository.ProductTourRepository;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.text.MessageFormat;
import java.time.LocalDateTime;
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
    @Inject
    @Tag("container:root.producer.details.specifies")
    private SpecifiesProductTourContainer specifiesProductTourContainer;

    private ObjectProperty<ProductTour> currentProductTour;

    @Override
    public void setup() {

        currentProductTour = new SimpleObjectProperty<>();
        // On récupère les tournées et les ajoute dans la liste, Met les icons dans la liste
        refresh();

        // on récupère la selection des tournées
        container.getProductTourListView().getSelectionModel().selectedItemProperty().addListener(this::selectItem);

        // Filtre la liste en fonction de l'observation du text field
        container.getSearchTextField().textProperty().addListener(this::filtre);

        // Ouvre la fenêtre d'ajout de tournée en appuyant sur le button
        container.getAdderProductTourButton().setOnAction(event -> container.getProductTourFrame().getStage().show());

    }

    private void filtre(Observable observable) {

        String filter = container.getSearchTextField().getText();

        if (filter == null || filter.length() == 0) {
            container.getFilteredList().setPredicate(productTourPack -> true);
            return;
        }

        container.getFilteredList().setPredicate(
                productTourPack -> productTourPack.toString().contains(filter));
    }

    private String toStringPt(ProductTour productTour) {
        return MessageFormat.format("{0} | Commencé le {1}", productTour.getName(), productTour.getStartDateTime().toString());
    }

    public void refresh() {
        container.getObservableList().setAll(productTourRepository.findAll().stream()
                .map(productTour -> Custom.of(productTour, this::toStringPt))
                .collect(Collectors.toSet()));
        container.getProductTourListView().setCellFactory(this::returnListCellWithImage);
        container.getProductTourListView().refresh();
    }

    private void selectItem(ObservableValue<? extends Custom<ProductTour>> observableV,
                            Custom<ProductTour> oldV,
                            Custom<ProductTour> newV) {

        // Si on n'a rien sélectionné on quitte la methode
        if (newV == null) return;

        currentProductTour.set(newV.get());

        Set<Order> orders =  orderRepository.findByProductTour(currentProductTour.get());

        Set<Custom<Order>> packsOrder = orders.stream()
                .map(order -> Custom.of(order, Order::getWording))
                .collect(Collectors.toSet());

        specifiesProductTourContainer.getController().setProductTour(currentProductTour.get());
        specifiesProductTourContainer.getController().getOrderListView().getItems().setAll(packsOrder);
        specifiesProductTourContainer.getController().refresh();
    }

    private ListCell<Custom<ProductTour>> returnListCellWithImage(ListView<Custom<ProductTour>> lv) {
        return new ListCell<>() {

            private final ImageView imageView = new ImageView();

            @Override
            public void updateItem(Custom<ProductTour> productTourCustom, boolean empty) {
                super.updateItem(productTourCustom, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                ProductTour productTour = productTourCustom.get();
                Image image = IconsType.WARNING_16x16;

                if (    productTour.getEndDateTime() != null &&
                        productTour.getEndDateTime().isBefore(LocalDateTime.now())) {

                    image = IconsType.LOADING_16x16;
                }

                imageView.setImage(image);
                setGraphic(imageView);
                setText(productTourCustom.toString());
            }
        };
    }

    public ObjectProperty<ProductTour> currentProductTourProperty() {
        return currentProductTour;
    }

    public ProductTour getCurrentProductTour() {
        return currentProductTour.get();
    }
}
