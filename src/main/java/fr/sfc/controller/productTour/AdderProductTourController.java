package fr.sfc.controller.productTour;

import fr.sfc.common.Pack;
import fr.sfc.container.productTour.AdderProductTourContainer;
import fr.sfc.entity.Company;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.CompanyRepository;
import fr.sfc.repository.ProductTourRepository;
import fr.sfc.repository.VehicleRepository;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class AdderProductTourController implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdderProductTourController.class);

    @AutoContainer
    private AdderProductTourContainer container;

    @FXML private VBox containerNameTextField;
    @FXML private VBox containerDataTextField;
    @FXML private TextField tFName;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private TextField tFWeight;
    @FXML private TextField tFMatriculation;

    @Inject
    private ProductTourRepository productTourRepository;
    @Inject
    private CompanyRepository companyRepository;
    @Inject
    private VehicleRepository vehicleRepository;

    private Company currentCompany; // TODO: A enlever quand la page de connexion sera faite

    @Override
    public void setup() {

        currentCompany = companyRepository.find(1);

        // Listen the current company and vehicle
        container.getSearchMatriculationDialog().currentSelectedProperty().addListener(this::listenSelectedPackVehicle);

        // Listen text field
        tFWeight.textProperty().addListener(this::listenWeightTextField);

        // Bindings container labels
        containerNameTextField.prefWidthProperty().bind(container.widthProperty().divide(2));
        containerNameTextField.prefHeightProperty().bind(container.heightProperty());

        // Bindings container text fields
        containerDataTextField.prefWidthProperty().bind(container.widthProperty().divide(2));
        containerDataTextField.prefHeightProperty().bind(container.heightProperty());

        // Bindings date pickers
        startDate.prefWidthProperty().bind(containerNameTextField.widthProperty());
        endDate.prefWidthProperty().bind(containerNameTextField.widthProperty());

    }

    private void listenSelectedPackVehicle(ObservableValue<? extends Pack<Vehicle>> o,
                                           Pack<Vehicle> old,
                                           Pack<Vehicle> current) {
        if (current == null) return;

        tFMatriculation.setText(current.get().getMatriculation());
    }

    private void listenWeightTextField(ObservableValue<? extends String> o,
                                       String old,
                                       String current) {

        Set<Pack<Vehicle>> vehicles = vehicleRepository
                .findByCompany(currentCompany)
                .stream()
                .filter(this::checkVehicleWeight)
                .map(vehicle -> Pack.of(vehicle,
                                vehicle1 -> vehicle1.getMatriculation() + " " +
                                vehicle1.getMaxWeight() + "kg"))
                .collect(Collectors.toSet());

        container.getSearchMatriculationDialog().getObservableList().setAll(vehicles);

        tFMatriculation.setDisable(current == null || current.isBlank());
    }

    @FXML
    public void buttonAddProductTourEvent() {

        AtomicReference<Vehicle> vehicle = new AtomicReference<>();

        String matriculation = tFMatriculation.getText().trim();

        vehicleRepository.findByMatriculation(matriculation).ifPresent(vehicle::set);

        if (tFName.getText().trim().isBlank() ||
            startDate.getValue() == null ||
            endDate.getValue() == null ||
            vehicle.get() == null ||
            tFWeight.getText().trim().isBlank() ||
            tFName.getText().trim().isBlank()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Form");
            alert.setHeaderText(null);
            alert.setContentText("You need to specifies all fields");
            alert.showAndWait();
            return;
        }

        ProductTour productTour = new ProductTour();
        productTour.setIdCompany(currentCompany.getId());
        productTour.setIdVehicle(vehicle.get().getId());
        productTour.setMatriculation(matriculation);
        productTour.setStartDateTime(startDate.getValue().atStartOfDay());
        productTour.setEndDateTime(endDate.getValue().atStartOfDay());
        productTour.setSIRET(currentCompany.getSIRET());
        productTour.setWeight(Float.valueOf(tFWeight.getText()));
        productTour.setName(tFName.getText());

        try {
            productTourRepository.insert(productTour);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText(null);
            alert.setContentText("Product Tour has been");
            alert.showAndWait();

            container.getStage().close();

            LOGGER.info("Product Tour {} id={} has been created by {} id={}",
                    productTour.getName(), productTour.getId(),
                    currentCompany.getName(), currentCompany.getId());

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Form");
            alert.setHeaderText(null);
            alert.setContentText(matriculation + " is already used");
            alert.showAndWait();

            LOGGER.error(matriculation + " is already used", e);
        }

    }

    @FXML
    public void onClickMatriculationTextField() {
        container.getSearchMatriculationDialog().getListView().refresh();
        container.getSearchMatriculationDialog().show();
    }

    private boolean checkVehicleWeight(Vehicle vehicle) {

        boolean is = tFWeight != null &&
                    !tFWeight.getText().isBlank() &&
                    vehicle.getMaxWeight() > Float.parseFloat(tFWeight.getText());

        if (!is) tFMatriculation.setText("");

        return is;
    }

}
