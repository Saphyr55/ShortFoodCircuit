package fr.sfc.controller.productTour;

import fr.sfc.common.Pack;
import fr.sfc.container.productTour.AdderOrderContainer;
import fr.sfc.controller.ConnectionController;
import fr.sfc.entity.Company;
import fr.sfc.entity.Customer;
import fr.sfc.entity.Order;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.SimpleAlertUtils;
import fr.sfc.framework.controlling.TimeTextField;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.framework.item.Tag;
import fr.sfc.repository.CustomerRepository;
import fr.sfc.repository.OrderRepository;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static fr.sfc.common.Phones.formatPhoneNumberFR;

public class AdderOrderController implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdderOrderController.class);

    @AutoContainer
    private AdderOrderContainer container;

    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private ContainerManager containerManager;
    @Inject
    @Tag("controller:root.producer.list.adder")
    private AdderProductTourController adderProductTourController;
    @Inject
    @Tag("controller:root.producer.list")
    private ListProductTourController listProductTourController;
    @Inject
    @Tag("controller:root")
    private ConnectionController connectionController;

    private ObjectProperty<Pack<Customer>> customerObservableValue;

    @FXML TextField dataCustomerTextField;
    @FXML VBox containerDataTextField;
    @FXML VBox containerNameTextField;
    @FXML DatePicker dataStartDate;
    @FXML TimeTextField dataStartHour;
    @FXML DatePicker dataEndDate;
    @FXML TimeTextField dataEndHour;
    @FXML TextField dataWeight;
    @FXML TextField dataWording;

    @Override
    public void setup() {

        customerObservableValue = new SimpleObjectProperty<>();

        // Recuperate all customers and map it to a pack
        container.getListSearchCustomerDialog().getObservableList().setAll(
                customerRepository.findAll().stream() // get all customer and stream it
                .map(this::customerPack) // map to a pack customer
                .collect(Collectors.toSet()));

        // Listen the current customer selected in the customer list
        container.getListSearchCustomerDialog().currentSelectedProperty().addListener(this::listenSelectedPackCustomer);

        // Bindings container labels (responsive)
        containerNameTextField.prefWidthProperty().bind(container.widthProperty().divide(2));
        containerNameTextField.prefHeightProperty().bind(container.heightProperty());

        // Bindings container text fields (responsive)
        containerDataTextField.prefWidthProperty().bind(container.widthProperty().divide(2));
        containerDataTextField.prefHeightProperty().bind(container.heightProperty());

    }

    /**
     * Ouvre la liste de client
     */
    @FXML
    public void openListCustomer() {
        container.getListSearchCustomerDialog().show();
    }

    /**
     * Confirmation de l'envoi d'une commande
     * <p>
     * TODO: A finir
     */
    @FXML
    public void sendOrder() {

        if (createAlertIfEmptyField()) return;

        Customer customer = customerObservableValue.get().get();
        ProductTour productTour = listProductTourController.getCurrentProductTour();
        Order order = createOrder(customer, productTour, connectionController.getCompanyConnected());

        try {

            if (createAlertConfirmation(order)) return;

            listProductTourController.updateOrders(productTour);

            LOGGER.info( "Order `{}` has been created by `{}` id=`{}` for `{}` id=`{}" ,
                    order.getWording(),
                    connectionController.getCompanyConnected().getNameOwner(),
                    connectionController.getCompanyConnected().getId(),
                    customer.getName(), customer.getId());

            container.getStage().hide();

        } catch (Exception e) {
            LOGGER.error("Error while order creation", e);
        }

    }

    /**
     * TODO: A finir
     *
     * @param order
     * @param orders
     * @return
     */
    private boolean checkDate(Order order, Set<Order> orders) {

        return false;
    }

    private boolean createAlertConfirmation(Order order) {
        AtomicBoolean quit = new AtomicBoolean(false);
        SimpleAlertUtils.createAlertConfirmation()
                .withTitle("Confirmation")
                .withHeaderText("Voulez-vous ajout\u00E9 une commande ?")
                .withOnOkButton(alert -> orderRepository.insert(order))
                .withOnCancelButton(alert -> quit.set(true))
                .buildShowAndWait();
        return quit.get();
    }

    /**
     * Cr??er un ordre depuis les champs de saisie
     *
     * @param customer customer s??lectionner
     * @param productTour product tour courant
     * @param company company courant
     * @return order
     */
    private Order createOrder(Customer customer, ProductTour productTour, Company company) {
        return new Order(
                getLocalDateTimeFromStart(),
                getLocalDateTimeFromEnd(),
                dataWording.getText(),
                Float.parseFloat(dataWeight.getText()),
                productTour.getId(),
                customer.getId(),
                company.getId(),
                company.getSIRET());
    }

    /**
     * Convertie les start text field date et hour en a local datetime
     *
     * @return local date time depuis les champs de start
     */
    private LocalDateTime getLocalDateTimeFromStart() {
        return dataStartDate.getValue().atStartOfDay()
                .toLocalDate().atTime(
                        dataStartHour.getHours(),
                        dataStartHour.getMinutes(),
                        dataStartHour.getSeconds());
    }

    /**
     * Convertie les end text field date et hour en a local datetime
     *
     * @return local date time depuis les champs d'end
     */
    private LocalDateTime getLocalDateTimeFromEnd() {
        return dataEndDate.getValue().atStartOfDay()
                .toLocalDate().atTime(
                        dataEndHour.getHours(),
                        dataEndHour.getMinutes(),
                        dataEndHour.getSeconds());
    }

    /**
     * V??rifie s'il y a champ qui est vides
     *
     * @return s'il y a un champ vide return true, sinon false
     */
    private boolean checkForEmptyFields() {
        return  customerObservableValue.getValue() == null ||
                dataCustomerTextField.getText().isBlank() ||
                dataWeight.getText().isBlank() ||
                dataWording.getText().isBlank() ||
                dataEndDate.getValue() == null ||
                dataStartDate.getValue() == null;
    }

    /**
     * Cr??er une alerte s'il y a un champ qui n'a pas ??t?? saisie
     *
     * @return si l'alerte a ??t?? affich??e return true, sinon false
     */
    private boolean createAlertIfEmptyField() {
        AtomicBoolean quit = new AtomicBoolean(false);
        SimpleAlertUtils.createAlertErrorConditional(checkForEmptyFields())
                .ifPresent(simpleAlertBuilder -> {
                    simpleAlertBuilder
                    .withTitle("Erreur dans le formulaire")
                    .withContentText("Un des champs n'est pas remplis")
                    .buildShowAndWait();
                    quit.set(true);
        });
        return quit.get();
    }

    /**
     * Met ?? jour le customer en fonction de la selection de liste de customer
     *
     * @param o observable
     * @param old odl
     * @param current current
     */
    private void listenSelectedPackCustomer(ObservableValue<? extends Pack<Customer>> o,
                                            Pack<Customer> old,
                                            Pack<Customer> current) {
        if (current == null) return;

        customerObservableValue.set(current);

        dataCustomerTextField.setText(current.get().getName() + " " +
                formatPhoneNumberFR(current.get().getPhoneNumber()));
    }

    /**
     * Custom to string du customer
     *
     * @param customer customer
     * @return custom de customer
     */
    private Pack<Customer> customerPack(Customer customer) {
        return Pack.of(customer, c -> c.getName() + " - " + formatPhoneNumberFR(c.getPhoneNumber()));
    }

}
