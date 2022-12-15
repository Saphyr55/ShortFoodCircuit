package fr.sfc.controller.productTour;

import java.util.concurrent.atomic.AtomicReference;

import fr.sfc.container.productTour.ProductTourFrameContainer;
import fr.sfc.entity.Company;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.CompanyRepository;
import fr.sfc.repository.ProductTourRepository;
import fr.sfc.repository.VehicleRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ProductTourFrameController implements Controller {

    @AutoContainer
    private ProductTourFrameContainer container;

    @FXML private VBox containerLabel;
    @FXML private VBox containerTextField;
    @FXML private TextField tFName;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private TextField tFWeight;
    @FXML private TextField tfSIRETCompany;
    @FXML private TextField tFMatriculation;
    @FXML private Button buttonFinish;
    @FXML private Label labelError;

    @Inject
    private ProductTourRepository productTourRepository;
    @Inject
    private CompanyRepository companyRepository;
    @Inject
    private VehicleRepository vehicleRepository;

    @Override
    public void setup() {
        // Bindings container labels
        containerLabel.prefWidthProperty().bind(container.widthProperty().divide(2));
        containerLabel.prefHeightProperty().bind(container.heightProperty());

        // Bindings container text fields
        containerTextField.prefWidthProperty().bind(container.widthProperty().divide(2));
        containerTextField.prefHeightProperty().bind(container.heightProperty());

        // Bindings text fields and date pickers
        tFName.prefWidthProperty().bind(containerTextField.widthProperty());
        tfSIRETCompany.prefWidthProperty().bind(containerTextField.widthProperty());
        tFWeight.prefWidthProperty().bind(containerTextField.widthProperty());
        tFMatriculation.prefWidthProperty().bind(containerTextField.widthProperty());
        startDate.prefWidthProperty().bind(containerLabel.widthProperty());
        endDate.prefWidthProperty().bind(containerLabel.widthProperty());

        buttonFinish.prefWidthProperty().bind(containerLabel.widthProperty());
    }

    @FXML
    public void EventButtonAddProductToursFinishAction() {

        AtomicReference<Company> currentCompany = new AtomicReference<>();
        AtomicReference<Vehicle> vehicle = new AtomicReference<>();

        String matriculation = tFMatriculation.getText().toUpperCase();

        if (tfSIRETCompany.getText().equals("") || matriculation.equals("")){
            labelError.setText("need information");
            return;
        }

        companyRepository.findBySIRET(Integer.valueOf(tfSIRETCompany.getText())).ifPresent(currentCompany::set);
        vehicleRepository.findByMatriculation(matriculation).ifPresent(vehicle::set);

        if (startDate.getValue() == null || endDate.getValue() == null) {
            labelError.setText("You need to specifies the date");
            return;
        }

        if (vehicle.get() == null || currentCompany.get() == null) {
            labelError.setText("company or vehicle doesn't exist");
            return;
        }

        ProductTour productTour = new ProductTour();
        productTour.setIdCompany(currentCompany.get().getId());
        productTour.setIdVehicle(vehicle.get().getId());
        productTour.setSIRET(Integer.parseInt(tfSIRETCompany.getText()));
        productTour.setMatriculation(matriculation);
        productTour.setStartDateTime(startDate.getValue().atStartOfDay());
        productTour.setEndDateTime(endDate.getValue().atStartOfDay());

        if (!tFWeight.getText().trim().isEmpty())
            productTour.setWeight(Float.valueOf(tFWeight.getText()));

        if (!tFName.getText().trim().isEmpty())
            productTour.setName(tFName.getText());

        productTourRepository.insert(productTour);


    }

}
