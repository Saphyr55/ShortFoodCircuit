package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.ProductTourFrame;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.ProductTourRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ProductToursFrameController implements Controller {
    @FXML
    private VBox vBox;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField tFName;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField tFWeight;
    @FXML
    private TextField tfSIRETCompany;
    @FXML
    private TextField tFMatriculation;
    @FXML
    private Button buttonFinish;

    @AutoContainer
    private ProductTourFrame container;

    @Inject
    private ProductTourRepository productTourRepository;
    @Inject
    private Stage stage;

    @Override
    public void setup() {
        System.out.println("test1");
        final var productTours = productTourRepository.findAll();
        System.out.println("test2");
        anchorPane.prefHeightProperty().bind(vBox.heightProperty());
        anchorPane.prefWidthProperty().bind(vBox.widthProperty());
        tFName.prefWidthProperty().bind(anchorPane.widthProperty().divide(2));
        tfSIRETCompany.prefWidthProperty().bind(anchorPane.widthProperty().divide(2));
        tFWeight.prefWidthProperty().bind(anchorPane.widthProperty().divide(2));
        tFMatriculation.prefWidthProperty().bind(anchorPane.widthProperty().divide(2));
        startDate.prefWidthProperty().bind(anchorPane.widthProperty().divide(2));
        endDate.prefWidthProperty().bind(anchorPane.widthProperty().divide(2));
        buttonFinish.prefWidthProperty().bind(anchorPane.widthProperty().divide(2));



    }

    @FXML
    public void EventButtonAddProductToursFinishAction() {
        LocalDateTime localStartDate = this.startDate.getValue().atStartOfDay();
        LocalDateTime localEndDate = this.endDate.getValue().atStartOfDay();
        Float weight = Float.valueOf(tFWeight.getText());
        Integer SIRET = Integer.parseInt(this.tfSIRETCompany.getText());
        ProductTour newprodTour = new ProductTour(localStartDate,
                localEndDate,
                this.tFName.getText(),
                weight,
                SIRET,
                this.tFMatriculation.getText());
        productTourRepository.insert(newprodTour);

    }

}
