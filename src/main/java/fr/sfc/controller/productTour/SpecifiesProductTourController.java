package fr.sfc.controller.productTour;

import fr.sfc.IconsType;
import fr.sfc.common.Pack;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @FXML private ListView<Pack<Order>> orderListView;

    private ProductTour productTour;

    @Inject
    private CustomerRepository customerRepository;

    @Override
    public void setup() {
        responsive();
        setTextForNameProductTourTextField();
        setTextForNameOrderTextField();

        orderListView.getSelectionModel().selectedItemProperty().addListener(this::onActionSelectElementListView);
    }

    private void onActionSelectElementListView(
                                ObservableValue<? extends Pack<Order>> observableV,
                                Pack<Order> oldV,
                                Pack<Order> newV) {

        // Si on n'a rien sélectionné on quitte la methode
        if (newV == null || newV.get() == null) return;

        setTextForDataOrderTextField(newV.get());
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

    private void setTextForDataOrderTextField(Order order) {

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

    public void refresh() {
        orderListView.setCellFactory(this::returnListCellWithImage);
        orderListView.refresh();
        setDataProductTourTextField();
    }


    private ListCell<Pack<Order>> returnListCellWithImage(ListView<Pack<Order>> lv) {
        return new ListCell<>() {

            private final ImageView imageView = new ImageView();

            @Override
            public void updateItem(Pack<Order> orderPack, boolean empty) {
                super.updateItem(orderPack, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Order order = orderPack.get();
                Image image = IconsType.LOADING_16x16;

                if (order.getEndLocalDateTime() != null) {

                    image = IconsType.CORRECT_16x16;

                    if (order.getEndLocalDateTime().isAfter(LocalDateTime.now()))
                        image = IconsType.WARNING_16x16;

                }


                imageView.setImage(image);
                setGraphic(imageView);
                setText(orderPack.toString());

            }
        };
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

    public ListView<Pack<Order>> getOrderListView() {
        return orderListView;
    }
}
