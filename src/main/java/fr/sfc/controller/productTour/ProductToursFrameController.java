package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.ProductTourFrame;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.ProductTourRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ProductToursFrameController implements Controller {

    @FXML
    private TextField tFName;

    @AutoContainer
    private ProductTourFrame container;

    @Inject
    private ProductTourRepository productTourRepository;
    private Stage stage;

    @Override
    public void setup() {
        final var productTours = productTourRepository.findAll();
    }

    @FXML
    public void EventButtonAddProductToursFinishAction() {
        System.out.println(tFName);
    }

}
