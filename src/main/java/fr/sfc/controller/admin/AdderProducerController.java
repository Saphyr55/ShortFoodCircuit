package fr.sfc.controller.admin;

import fr.sfc.common.Pack;
import fr.sfc.container.admin.AdderProducerContainer;
import fr.sfc.entity.Company;
import fr.sfc.entity.Producer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.SimpleAlertUtils;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.framework.item.Tag;
import fr.sfc.repository.CompanyRepository;
import fr.sfc.repository.ProducerRepository;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class AdderProducerController implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdderProducerController.class);

    @AutoContainer
    private AdderProducerContainer container;

    @Inject
    private ProducerRepository producerRepository;
    @Inject
    private CompanyRepository companyRepository;
    @Inject
    @Tag("controller:root.admin.list")
    private ListProducerCustomerController listProducerCustomerController;

    private Company companySelected;

    @Override
    public void setup() {

        container.getSearchSIRETPopup().getObservableList().setAll(
                companyRepository.findAll().stream().map(this::customProducer).collect(Collectors.toSet()));

        container.getAddProducerButton().setOnAction(this::onAddButton);

        container.getSiretInput().setOnMousePressed(event ->  container.getSearchSIRETPopup().show());

        container.getSearchSIRETPopup().currentSelectedProperty().addListener(this::listenSelectedPackVehicle);
    }

    private Pack<Company> customProducer(Company company) {
        return Pack.of(company, company1 -> String.valueOf(company1.getSIRET()));
    }

    private void listenSelectedPackVehicle(ObservableValue<? extends Pack<Company>> o,
                                           Pack<Company> old,
                                           Pack<Company> current) {
        if (current == null) return;

        companySelected = current.get();
        container.getSiretInput().setText(String.valueOf(current.get().getSIRET()));
    }

    private void onAddButton(ActionEvent event) {

        if (createAlertFieldsEmpty()) return;

        String firstname = container.getFirstnameInput().getText();
        String lastname = container.getLastnameInput().getText();
        Producer producer = new Producer("password", lastname, firstname, companySelected.getId(), companySelected.getSIRET());

        try {
            if (createConfirmAlertInsertProductTour(producer)) return;

            listProducerCustomerController.fillListView();

            LOGGER.info( "Producer `{} {}` has been created", producer.getLastname().toUpperCase(), producer.getFirstname());

        } catch (Exception e) {

            createAlertCreationError();
            LOGGER.error("Error during producer creation", e);
        }

    }

    private void createAlertCreationError() {
        SimpleAlertUtils.createAlertError()
                .withTitle("Erreur formulaire")
                .withContentText("Erreur pendant la creation d'un tourn\u00E9e")
                .buildShowAndWait();
    }

    private boolean createConfirmAlertInsertProductTour(Producer producer) {
        AtomicBoolean quit = new AtomicBoolean(false);
        SimpleAlertUtils.createAlertConfirmation()
                .withTitle("Confirmation")
                .withContentText("Voulez-vous cr\u00E9er une producteur")
                .withOnOkButton(alert -> producerRepository.insert(producer))
                .withOnCancelButton(alert -> quit.set(true))
                .buildShowAndWait();
        return quit.get();
    }


    private boolean createAlertFieldsEmpty() {
        AtomicBoolean quit = new AtomicBoolean(false);
        SimpleAlertUtils.createAlertErrorConditional(fieldsIsNotEmpty())
                .ifPresent(simpleAlertBuilder -> { simpleAlertBuilder
                        .withTitle("Erreur formulaire")
                        .withContentText("Un des champs n'est pas remplis")
                        .buildShowAndWait();
                         quit.set(true);
                });
        return quit.get();
    }

    private boolean fieldsIsNotEmpty() {
        return  container.getFirstnameInput().getText().isBlank() ||
                container.getLastnameInput().getText().isBlank() ||
                container.getSiretInput().getText().isBlank() ||
                companySelected == null;
    }

}
