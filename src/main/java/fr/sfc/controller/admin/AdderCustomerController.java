package fr.sfc.controller.admin;

import com.google.gson.Gson;
import fr.sfc.container.admin.AdderCustomerContainer;
import fr.sfc.container.admin.ListProducerCustomerContainer;
import fr.sfc.entity.Customer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.SimpleAlertUtils;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.framework.item.Tag;
import fr.sfc.repository.CustomerRepository;
import javafx.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdderCustomerController implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdderCustomerController.class);

    private static final String URL_OSM_QUERY = "https://nominatim.openstreetmap.org/search?q=";
    private static final String FORMAT_JSON = "&format=json";
    private static final String POLYGON = "&polygon=1";
    private static final String ADDRESS_DETAILS = "&addressdetails=1";

    @AutoContainer
    private AdderCustomerContainer container;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    @Tag("container:root.admin.list")
    private ListProducerCustomerContainer listProducerCustomerContainer;

    @Inject
    @Tag("controller:root.admin.list")
    private ListProducerCustomerController listProducerCustomerController;

    @Override
    public void setup() {
        container.getAddButton().setOnAction(this::onAddButton);
    }

    public void onAddButton(ActionEvent event) {

        // quitte la methode si un des champs est vide
        if (createAlertIfEmptyField()) return;

        Customer customer = createCustomerFromTextField();

        try {

            // confirme l'insertion de la tournée, quitte la methode si cancel
            if (createConfirmAlertInsertCustomer(customer)) return;

            listProducerCustomerController.fillListView();

            LOGGER.info("Customer {} has been created", customer);

        } catch (Exception e) {
            createAlertCreationError();
        }

        listProducerCustomerContainer.getAdderCustomerStage().close();
    }

    private void createAlertCreationError() {
        SimpleAlertUtils.createAlertError()
                .withTitle("Erreur formulaire")
                .withContentText("Erreur pendant la creation d'un client")
                .buildShowAndWait();
    }

    private boolean createConfirmAlertInsertCustomer(Customer customer) {
        AtomicBoolean quit = new AtomicBoolean(false);
        SimpleAlertUtils.createAlertConfirmation()
                .withTitle("Confirmation")
                .withContentText("Voulez-vous cr\u00E9er une tourn\u00E9e")
                .withOnOkButton( alert -> customerRepository.insert(customer) )
                .withOnCancelButton( alert -> quit.set(true) )
                .buildShowAndWait();
        return quit.get();
    }

    /**
     * Vérifie s'il y a champ qui est vides
     *
     * @return s'il y a un champ vide return true, sinon false
     */
    private boolean checkForEmptyFields() {
        return container.getAddressCodeInput().getText().isBlank() ||
                container.getPhoneInput().getText().isBlank() ||
                container.getAddressInput().getText().isBlank() ||
                container.getNameInput().getText().isBlank();
    }

    /**
     * Créer une alerte s'il y a un champ qui n'a pas été saisie
     *
     * @return si l'alerte a été affichée return true, sinon false
     */
    private boolean createAlertIfEmptyField() {
        AtomicBoolean quit = new AtomicBoolean(false);
        SimpleAlertUtils.createAlertErrorConditional(checkForEmptyFields()).ifPresent(simpleAlertBuilder -> {
            simpleAlertBuilder
                    .withTitle("Erreur dans le formulaire")
                    .withContentText("Un des champs n'est pas remplis")
                    .buildShowAndWait();
            quit.set(true);
        });
        return quit.get();
    }

    private Customer createCustomerFromTextField() {
        String name = container.getNameInput().getText();
        String phone = container.getPhoneInput().getText();
        String address = container.getAddressInput().getText() + " " +
                        container.getAddressCodeInput().getText();
        Map<String, Float> coordinates = getCoordinates(address);
        return new Customer(name, phone, address, coordinates.get("lat"), coordinates.get("lon"));
    }

    private Map<String, Float> getCoordinates(String address) {
        try {

            HttpURLConnection conn = (HttpURLConnection) new URL(
                    URL_OSM_QUERY + URLEncoder.encode(address, StandardCharsets.UTF_8) +
                    FORMAT_JSON + POLYGON + ADDRESS_DETAILS)
                    .openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200)
                LOGGER.error("Failed : HTTP error code : " + conn.getResponseCode(), new RuntimeException());

            StringBuilder full = new StringBuilder();
            new BufferedReader(new InputStreamReader((conn.getInputStream()))).lines().forEach(full::append);
            Map<?, ?>[] map = new Gson().fromJson(full.toString(), Map[].class);

            conn.disconnect();

            return Map.of(
                    "lat", Float.parseFloat((String) map[0].get("lat")),
                    "lon", Float.parseFloat((String) map[0].get("lon")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Map.of();
    }

}
