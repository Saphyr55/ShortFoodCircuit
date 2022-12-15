package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.SpecifiesProductTourContainer;
import fr.sfc.entity.Customer;
import fr.sfc.entity.Order;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.CustomerRepository;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class SpecifiesProductTourController implements Controller {

    @AutoContainer
    private SpecifiesProductTourContainer container;

    @FXML private VBox listContainerVBox;
    @FXML private Button addOrderButton;
    @FXML private TextField nameProductTourTextField;
    @FXML private TextField nameStartDateProductTourTextField;
    @FXML private TextField nameEndDateProductTourTextField;
    @FXML private TextField nameWeightProductTourTextField;
    @FXML private TextField nameMatriculationVehicleProductTourTextField;
    @FXML private TextField dataProductTourTextField;
    @FXML private TextField dataStartDateProductTourTextField;
    @FXML private TextField dataEndDateProductTourTextField;
    @FXML private TextField dataWeightProductTourTextField;
    @FXML private TextField dataMatriculationVehicleProductTourTextField;
    @FXML private TextField dataCustomerOrderTextField;
    @FXML private TextField dataWeightOrderTextField;
    @FXML private TextField dataEntDateOrderTextField;
    @FXML private TextField dataStartDateOrderTextField;
    @FXML private TextField dataOrderTextField;
    @FXML private TextField nameCustomerOrderTextField;
    @FXML private TextField nameWeightOrderTextField;
    @FXML private TextField nameEndDateOrderTextField;
    @FXML private TextField nameStartDateOrderTextField;
    @FXML private TextField nameOrderTextField;
    @FXML private TextField nameTitleOrderTextField;
    @FXML private TextField nameTitleProductTourTextField;
    @FXML private HBox containerNameTextFieldHBox;
    @FXML private VBox containerHBoxVBox;
    @FXML private HBox containerDataTextFieldHBox;
    @FXML private ListView<String> orderListView;

    private List<Order> orders;
    private ProductTour productTour;

    @Inject
    private CustomerRepository customerRepository;

    @Override
    public void setup() {
        responsive();
        setTextForNameProductTourTextField();
        setTextForNameOrderTextField();

        orderListView
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(this::onActionSelectElementListView);
    }

    private void onActionSelectElementListView(
                                ObservableValue<? extends Number> observableV,
                                Number oldV,
                                Number newV) {

        // Si on n'a rien sélectionné on quitte la methode
        if (newV.intValue() == -1) return;

        setTextForNameOrderTextField(orders.get(newV.intValue()));

    }

    private void responsive() {
        listContainerVBox.prefWidthProperty().bind(container.widthProperty().divide(1.5));
        listContainerVBox.prefHeightProperty().bind(container.heightProperty());
        containerHBoxVBox.prefHeightProperty().bind(container.heightProperty());
        containerHBoxVBox.prefWidthProperty().bind(container.widthProperty().divide(1.5));
        containerHBoxVBox.prefHeightProperty().bind(container.heightProperty());
        containerHBoxVBox.prefWidthProperty().bind(container.widthProperty());
        containerNameTextFieldHBox.prefWidthProperty().bind(containerHBoxVBox.widthProperty().divide(2));
        containerDataTextFieldHBox.prefWidthProperty().bind(containerHBoxVBox.widthProperty().divide(2));
    }

    private void setTextForNameProductTourTextField() {
        nameTitleProductTourTextField.setText("Product Tour Details...");
        nameProductTourTextField.setText("Name");
        nameStartDateProductTourTextField.setText("Start Date");
        nameEndDateProductTourTextField.setText("End Date");
        nameWeightProductTourTextField.setText("Weight (kg)");
        nameMatriculationVehicleProductTourTextField.setText("Vehicle");
    }

    private void setTextForNameOrderTextField() {
        nameTitleOrderTextField.setText("Order Details...");
        nameOrderTextField.setText("Name Order");
        nameCustomerOrderTextField.setText("Name Customer");
        nameStartDateOrderTextField.setText("Start Date");
        nameEndDateOrderTextField.setText("End Date");
        nameWeightOrderTextField.setText("Weight (kg)");
    }

    private void setTextForNameOrderTextField(Order order) {

        Customer customer = customerRepository.find(order.getIdCustomer());

        dataStartDateOrderTextField.setText(order.getStartLocalDateTime()
                .format(DateTimeFormatter.ISO_LOCAL_DATE));

        if (order.getEndLocalDateTime() != null)
            dataEntDateOrderTextField.setText(order.getEndLocalDateTime()
                .format(DateTimeFormatter.ISO_LOCAL_DATE));
        else
            dataEntDateOrderTextField.setText("Not finish");

        dataOrderTextField.setText(order.getWording());
        dataCustomerOrderTextField.setText(customer.getName());
        dataWeightOrderTextField.setText(order.getSIRET());
    }

    public ProductTour getProductTour() {
        return productTour;
    }

    public void setProductTour(ProductTour productTour) {
        this.productTour = productTour;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void refresh() {
        orderListView.getItems().setAll(orders.stream().map(Order::getWording).toList());
        orderListView.refresh();
        setDataProductTourTextField();
    }

    private void setDataProductTourTextField() {
        dataProductTourTextField.setText(productTour.getName());
        dataWeightProductTourTextField.setText(productTour.getWeight().toString());
        dataStartDateProductTourTextField.setText(productTour.getStartDateTime()
                .format(DateTimeFormatter.ISO_LOCAL_DATE));
        dataEndDateProductTourTextField.setText(productTour.getEndDateTime()
                .format(DateTimeFormatter.ISO_LOCAL_DATE));
        dataMatriculationVehicleProductTourTextField.setText(productTour.getMatriculation());
    }

}
