package fr.sfc.controller.productTour;

import fr.sfc.common.Pack;
import fr.sfc.container.productTour.AdderProductTourContainer;
import fr.sfc.container.productTour.ListProductTourContainer;
import fr.sfc.entity.Company;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;
import fr.sfc.framework.controlling.SimpleAlertUtils;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.CompanyRepository;
import fr.sfc.repository.ProductTourRepository;
import fr.sfc.repository.VehicleRepository;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
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
    private ContainerManager containerManager;
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

        AtomicBoolean quit = new AtomicBoolean(false);
        AtomicReference<Vehicle> vehicle = new AtomicReference<>();

        String matriculation = tFMatriculation.getText().trim();

        vehicleRepository.findByMatriculation(matriculation).ifPresent(vehicle::set);

        SimpleAlertUtils.createAlertErrorConditional(
                "Error Form",
                "You need to specifies all fields",
                 fieldsIsNotEmpty(vehicle.get()))
                .ifPresent(alert -> {
                    alert.showAndWait();
                    quit.set(true);
                });

        if (quit.get()) return;

        ProductTour productTour = new ProductTour(
                startDate.getValue().atStartOfDay(),
                endDate.getValue().atStartOfDay(),
                tFName.getText(),
                Float.valueOf(tFWeight.getText()),
                currentCompany.getSIRET(),
                matriculation);
        productTour.setIdCompany(currentCompany.getId());
        productTour.setIdVehicle(vehicle.get().getId());

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successful");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to create a product tour");
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK)
                    productTourRepository.insert(productTour);
            });

            container.getStage().close();

            // Rafraichi la liste de tournées
            ListProductTourContainer listProductTourContainer = containerManager.getContainer("root.list");
            listProductTourContainer.getController().refresh();

            LOGGER.info("Product Tour {} has been created by {} id={}",
                    productTour.getName(),
                    currentCompany.getName(),
                    currentCompany.getId());

        } catch (Exception e) {

            SimpleAlertUtils.createAlertError(
                    "Error Form",
                    matriculation + " is already used")
                    .showAndWait();
            LOGGER.error(matriculation + " is already used", e);
        }

    }

    private boolean fieldsIsNotEmpty(Vehicle vehicle) {
        return  tFName.getText().trim().isBlank() ||
                startDate.getValue() == null ||
                endDate.getValue() == null ||
                vehicle == null ||
                tFWeight.getText().trim().isBlank() ||
                tFName.getText().trim().isBlank();
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
