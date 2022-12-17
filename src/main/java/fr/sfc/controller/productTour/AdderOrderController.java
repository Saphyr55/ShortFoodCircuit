package fr.sfc.controller.productTour;

import fr.sfc.common.Pack;
import fr.sfc.container.productTour.AdderOrderContainer;
import fr.sfc.entity.Customer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.CustomerRepository;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;

import static fr.sfc.common.Phones.formatPhoneNumberFR;

public class AdderOrderController implements Controller {

    @AutoContainer
    private AdderOrderContainer container;

    @Inject
    private CustomerRepository customerRepository;

    @FXML TextField dataCustomerTextField;
    @FXML VBox containerDataTextField;
    @FXML VBox containerNameTextField;

    @Override
    public void setup() {

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

    @FXML
    public void openListCustomer() {
        container.getListSearchCustomerDialog().show();
    }

    private void listenSelectedPackCustomer(ObservableValue<? extends Pack<Customer>> o,
                                            Pack<Customer> old,
                                            Pack<Customer> current) {
        if (current == null) return;

        dataCustomerTextField.setText(current.get().getName() + " " +
                formatPhoneNumberFR(current.get().getPhoneNumber()));
    }

    private Pack<Customer> customerPack(Customer customer) {
        return Pack.of(customer, c -> c.getName() + " - " + formatPhoneNumberFR(c.getPhoneNumber()));
    }

}
