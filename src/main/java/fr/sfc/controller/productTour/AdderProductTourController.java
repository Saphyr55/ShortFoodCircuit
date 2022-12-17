package fr.sfc.controller.productTour;

import fr.sfc.common.Pack;
import fr.sfc.container.productTour.AdderProductTourContainer;
import fr.sfc.container.productTour.ListProductTourContainer;
import fr.sfc.entity.Company;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.SimpleAlertUtils;
import fr.sfc.framework.controlling.TimeTextField;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.CompanyRepository;
import fr.sfc.repository.ProductTourRepository;
import fr.sfc.repository.VehicleRepository;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    @FXML private TimeTextField endHourTextField;
    @FXML private TimeTextField startHourTextField;

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

        container.getSearchMatriculationDialog().getObservableList().setAll(
                vehicleRepository.findByCompany(currentCompany).stream()
                .filter(this::checkVehicleWeight)
                .map(this::vehiclePack)
                .collect(Collectors.toSet()));

        tFMatriculation.setDisable(current == null || current.isBlank());
    }

    private Pack<Vehicle> vehiclePack(Vehicle vehicle) {
        return Pack.of(vehicle, vehicle1 ->
                vehicle1.getMatriculation() + " " +
                vehicle1.getMaxWeight() + "kg");
    }

    @FXML
    public void buttonAddProductTourEvent() {

        AtomicReference<Vehicle> vehicle = new AtomicReference<>();
        String matriculation = tFMatriculation.getText().trim();
        vehicleRepository.findByMatriculation(matriculation).ifPresent(vehicle::set);

        // quitte la methode si un des champs est vide
        if (createAlertFieldsEmpty(vehicle.get())) return;

        ProductTour productTour = createProductTourFromTextField(vehicle.get());

        try {

            // confirme l'insertion de la tournée, quitte la methode si cancel
            if (createConfirmAlertInsertProductTour(productTour)) return;

            // Rafraichi la liste de tournées
            ListProductTourContainer listProductTourContainer = containerManager.getContainer("root.list");
            assert listProductTourContainer != null;
            listProductTourContainer.getController().refresh();

            LOGGER.info( "Product Tour {} has been created by {} id={}" ,
                    productTour.getName(),
                    currentCompany.getName(),
                    currentCompany.getId());

        } catch (Exception e) {

            createAlertCreationError();
            LOGGER.error(matriculation + " is already used", e);
        }

        container.getStage().close();

    }

    @FXML
    public void onClickMatriculationTextField() {
        container.getSearchMatriculationDialog().getListView().refresh();
        container.getSearchMatriculationDialog().show();
    }

    private boolean createConfirmAlertInsertProductTour(ProductTour productTour) {
        AtomicBoolean quit = new AtomicBoolean(false);
        SimpleAlertUtils.createAlertConfirmation()
                .withTitle( "Successful" )
                .withContentText( "Do you want to create a product tour" )
                .withOnOkButton( alert -> productTourRepository.insert(productTour) )
                .withOnCancelButton( alert -> quit.set(true) )
                .buildShowAndWait();
        return quit.get();
    }

    private boolean createAlertFieldsEmpty(Vehicle vehicle) {
        AtomicBoolean quit = new AtomicBoolean(false);
        SimpleAlertUtils.createAlertErrorConditional(fieldsIsNotEmpty(vehicle))
                .ifPresent(simpleAlertBuilder -> {
                    simpleAlertBuilder
                        .withTitle( "Error Form" )
                        .withContentText( "You need to specifies all fields" )
                        .buildShowAndWait();
                    quit.set(true);
                });
        return quit.get();
    }

    private void createAlertCreationError() {
        SimpleAlertUtils.createAlertError()
                .withTitle( "Error Form" )
                .withContentText( "Error while creation product tour" )
                .buildShowAndWait();
    }

    private ProductTour createProductTourFromTextField(Vehicle vehicle) {

        var pt = new ProductTour(

                startDate.getValue().atStartOfDay()
                        .toLocalDate().atTime(
                        startHourTextField.getHours(),
                        startHourTextField.getMinutes(),
                        startHourTextField.getSeconds()),

                endDate.getValue().atStartOfDay()
                        .toLocalDate().atTime(
                        endHourTextField.getHours(),
                        endHourTextField.getMinutes(),
                        endHourTextField.getSeconds()),

                tFName.getText(),

                Float.valueOf(tFWeight.getText()),

                currentCompany.getSIRET(),

                vehicle.getMatriculation());

        pt.setIdCompany(currentCompany.getId());
        pt.setIdVehicle(vehicle.getId());

        return pt;
    }

    private boolean fieldsIsNotEmpty(Vehicle vehicle) {
        return  tFName.getText().trim().isBlank() ||
                startDate.getValue() == null ||
                endDate.getValue() == null ||
                vehicle == null ||
                tFWeight.getText().trim().isBlank() ||
                tFName.getText().trim().isBlank() ||
                endHourTextField.getText().trim().isBlank() ||
                startHourTextField.getText().trim().isBlank();
    }

    private boolean checkVehicleWeight(Vehicle vehicle) {

        boolean is = tFWeight != null &&
                    !tFWeight.getText().isBlank() &&
                    vehicle.getMaxWeight() > Float.parseFloat(tFWeight.getText());

        if (!is) tFMatriculation.setText("");

        return is;
    }

}
