package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.ProductTourFrame;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.ProductTourRepository;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ProductToursFrameController implements Controller {

    @FXML
    private TextField tFName;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField tFWeight;
    @FXML
    private TextField tFIdCompany;
    @FXML
    private TextField tfSIRETCompany;
    @FXML
    private TextField tFVehicleId;
    @FXML
    private TextField tFMatriculation;


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
        LocalDateTime localStartDate = this.startDate.getValue().atStartOfDay();
        LocalDateTime localEndDate = this.endDate.getValue().atStartOfDay();
        Float weight = Float.valueOf(tFWeight.getText());
        Integer idCompany = Integer.parseInt(this.tFIdCompany.getText());
        Integer SIRET = Integer.parseInt(this.tfSIRETCompany.getText());
        Integer idVehicle = Integer.valueOf(this.tFVehicleId.getText());
        ProductTour newprodTour = new ProductTour(localStartDate,
                localEndDate,
                this.tFName.getText(),
                weight,
                idCompany,
                SIRET,
                idVehicle,
                this.tFMatriculation.getText());
        productTourRepository.insert(newprodTour);

    }

}
