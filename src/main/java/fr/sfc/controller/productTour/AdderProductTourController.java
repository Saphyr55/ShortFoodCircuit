package fr.sfc.controller.productTour;

import fr.sfc.common.Pack;
import fr.sfc.container.productTour.AdderProductTourContainer;
import fr.sfc.controller.ConnectionController;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.SimpleAlertUtils;
import fr.sfc.framework.controlling.TimeTextField;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.framework.item.Tag;
import fr.sfc.repository.CompanyRepository;
import fr.sfc.repository.ProductTourRepository;
import fr.sfc.repository.VehicleRepository;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    @Inject
    @Tag("controller:root.producer.list")
    private ListProductTourController listProductTourController;
    @Inject
    @Tag("controller:root")
    private ConnectionController connectionController;

    @Override
    public void setup() {

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

        current.asOptional().ifPresent(vehicle -> tFMatriculation.setText(vehicle.getMatriculation()));
    }

    private void listenWeightTextField(ObservableValue<? extends String> o,
                                       String old,
                                       String current) {

       container.getSearchMatriculationDialog().getObservableList().setAll(
       vehicleRepository.findByCompany(connectionController.getCompanyConnected()).stream()
               .filter(this::checkVehicleWeight)
               .map(this::customVehicle)
               .collect(Collectors.toSet()));


        tFMatriculation.setDisable(current == null || current.isBlank());
    }

    private Pack<Vehicle> customVehicle(Vehicle vehicle) {
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
            listProductTourController.refresh();

            LOGGER.info( "Product Tour {} has been created by {} id={}" ,
                    productTour.getName(),
                    connectionController.getCompanyConnected().getNameOwner(),
                    connectionController.getCompanyConnected().getId());

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

    private boolean createConfirmAlertInsertProductTour(@NotNull ProductTour productTour) {
        AtomicBoolean quit = new AtomicBoolean(false);
        SimpleAlertUtils.createAlertConfirmation()
                .withTitle("Confirmation")
                .withContentText("Voulez-vous cr\u00E9er une tourn\u00E9e")
                .withOnOkButton( alert -> productTourRepository.insert(productTour) )
                .withOnCancelButton( alert -> quit.set(true) )
                .buildShowAndWait();
        return quit.get();
    }

    private boolean createAlertFieldsEmpty(@Nullable Vehicle vehicle) {
        AtomicBoolean quit = new AtomicBoolean(false);
        SimpleAlertUtils.createAlertErrorConditional(fieldsIsNotEmpty(vehicle))
                .ifPresent(simpleAlertBuilder -> { simpleAlertBuilder
                        .withTitle("Erreur formulaire")
                        .withContentText("Un des champs n'est pas remplis")
                        .buildShowAndWait();
                        quit.set(true);
                });
        return quit.get();
    }

    private void createAlertCreationError() {
        SimpleAlertUtils.createAlertError()
                .withTitle("Erreur formulaire")
                .withContentText("Erreur pendant la creation d'un tourn\u00E9e")
                .buildShowAndWait();
    }

    private ProductTour createProductTourFromTextField(@NotNull Vehicle vehicle) {

        var company = connectionController.getCompanyConnected();

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

                company.getSIRET(),

                vehicle.getMatriculation());

        pt.setIdCompany(company.getId());
        pt.setIdVehicle(vehicle.getId());

        return pt;
    }

    private boolean fieldsIsNotEmpty(@Nullable Vehicle vehicle) {
        return  tFName.getText().trim().isBlank() ||
                startDate.getValue() == null ||
                endDate.getValue() == null ||
                vehicle == null ||
                tFWeight.getText().trim().isBlank() ||
                tFName.getText().trim().isBlank() ||
                endHourTextField.getText().trim().isBlank() ||
                startHourTextField.getText().trim().isBlank();
    }

    private boolean checkVehicleWeight(@NotNull Vehicle vehicle) {

        boolean is = tFWeight != null &&
                    !tFWeight.getText().isBlank() &&
                    vehicle.getMaxWeight() > Float.parseFloat(tFWeight.getText());

        if (!is) tFMatriculation.setText("");

        return is;
    }

}
