package fr.sfc.controller.productTour;

import fr.sfc.common.Custom;
import fr.sfc.container.productTour.AdderOrderContainer;
import fr.sfc.container.productTour.ListProductTourContainer;
import fr.sfc.entity.Customer;
import fr.sfc.entity.Order;
import fr.sfc.framework.item.Tag;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.TimeTextField;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.CustomerRepository;
import fr.sfc.repository.OrderRepository;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;

import static fr.sfc.common.Phones.formatPhoneNumberFR;

public class AdderOrderController implements Controller {

    @AutoContainer
    private AdderOrderContainer container;

    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private ContainerManager containerManager;

    @Inject
    @Tag("container.root.list")
    private ListProductTourContainer listProductTourContainer;

    private ObjectProperty<Custom<Customer>> customerObservableValue;

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
        customerObservableValue.bind(container.getListSearchCustomerDialog().currentSelectedProperty());
        customerObservableValue.addListener(this::listenSelectedPackCustomer);

        // Bindings container labels (responsive)
        containerNameTextField.prefWidthProperty().bind(container.widthProperty().divide(2));
        containerNameTextField.prefHeightProperty().bind(container.heightProperty());

        // Bindings container text fields (responsive)
        containerDataTextField.prefWidthProperty().bind(container.widthProperty().divide(2));
        containerDataTextField.prefHeightProperty().bind(container.heightProperty());

    }

    @FXML
    public void openListCustomer() {
        container.getListSearchCustomerDialog().show();
    }

    @FXML
    public void sendOrder() {

    }

    private Order createOrder(Customer customer) {
        /*
        ProductTour productTour = listProductTourContainer.getController().getCurrentProductTour();

        var order = new Order(
                dataStartDate.getValue().atStartOfDay()
                        .toLocalDate().atTime(
                                dataStartHour.getHours(),
                                dataStartHour.getMinutes(),
                                dataStartHour.getSeconds()),
                dataEndDate.getValue().atStartOfDay()
                        .toLocalDate().atTime(
                                dataEndHour.getHours(),
                                dataEndHour.getMinutes(),
                                dataEndHour.getSeconds()),
                dataWording.getText(),
                Float.parseFloat(dataWeight.getText()),
                productTour.getId(),
                customer.getId(),


        );

         */
        return null;
    }

    private boolean checkForEmptyFields(Customer customer) {
        return  customer == null ||
                dataCustomerTextField.getText().isBlank() ||
                dataWeight.getText().isBlank() ||
                dataWording.getText().isBlank() ||
                dataEndDate.getValue() == null ||
                dataStartDate.getValue() == null;
    }

    private void listenSelectedPackCustomer(ObservableValue<? extends Custom<Customer>> o,
                                            Custom<Customer> old,
                                            Custom<Customer> current) {
        if (current == null) return;

        dataCustomerTextField.setText(current.get().getName() + " " +
                formatPhoneNumberFR(current.get().getPhoneNumber()));
    }

    private Custom<Customer> customerPack(Customer customer) {
        return Custom.of(customer, c -> c.getName() + " - " + formatPhoneNumberFR(c.getPhoneNumber()));
    }

}
