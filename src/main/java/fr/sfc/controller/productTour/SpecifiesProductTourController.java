package fr.sfc.controller.productTour;

import fr.sfc.common.IconsType;
import fr.sfc.common.Custom;
import fr.sfc.container.productTour.SpecifiesProductTourContainer;
import fr.sfc.entity.Customer;
import fr.sfc.entity.Order;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
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
    @FXML private ListView<Custom<Order>> orderListView;

    private ProductTour productTour;

    @Inject
    private CustomerRepository customerRepository;

    @Override
    public void setup() {

        responsive();

        // Set text
        setTextForNameProductTourTextField();
        setTextForNameOrderTextField();

        // Rempli les données de la commande en cliquant sur une commande
        orderListView.getSelectionModel().selectedItemProperty().addListener(this::onActionSelectElementListView);
    }

    @FXML
    public void openAdderOrderContainer() {
        if (productTour != null)
            container.getAdderOrderContainer().getStage().show();
    }

    private void onActionSelectElementListView(
                                ObservableValue<? extends Custom<Order>> observableV,
                                Custom<Order> oldV,
                                Custom<Order> newV) {

        // Si on n'a rien sélectionné on quitte la methode
        if (newV == null) return;

        setTextForDataOrderTextField(newV.get());

    }

    private void responsive() {
        listContainerVBox.prefWidthProperty().bind(container.widthProperty().divide(1.5));
        listContainerVBox.prefHeightProperty().bind(container.heightProperty());
        containerNameTextFieldHBox.prefWidthProperty().bind(containerHBoxVBox.widthProperty().multiply(0.50));
        containerDataTextFieldHBox.prefWidthProperty().bind(containerHBoxVBox.widthProperty().multiply(0.50));
    }

    private void setTextForNameProductTourTextField() {
        nameTitleProductTourTextField.setText("Tourn\u00E9e Propri\u00E9t\u00E9s...");
        nameProductTourTextField.setText("Nom");
        nameStartDateProductTourTextField.setText("Commenc\u00E9 le");
        nameEndDateProductTourTextField  .setText("Fin le");
        nameWeightProductTourTextField.setText("Poids (kg)");
        nameMatriculationVehicleProductTourTextField.setText("V\u00E9hicule");
    }

    private void setTextForNameOrderTextField() {
        nameTitleOrderTextField.setText("Commande Propri\u00E9t\u00E9s...");
        nameOrderTextField.setText("Nom Commande");
        nameCustomerOrderTextField.setText("Nom Client");
        nameStartDateOrderTextField.setText("Commenc\u00E9 le");
        nameEndDateOrderTextField.setText("Fin le");
        nameWeightOrderTextField.setText("Poids (kg)");
    }

    private void setTextForDataOrderTextField(Order order) {

        Customer customer = customerRepository.find(order.getIdCustomer());

        dataStartDateOrderTextField.setText(order.getStartLocalDateTime()
                .toString().replace('T', ' '));

        if (order.getEndLocalDateTime() != null)
            dataEntDateOrderTextField.setText(order.getEndLocalDateTime()
                    .toString().replace('T', ' '));
        else
            dataEntDateOrderTextField.setText("Non fini");

        dataOrderTextField.setText(order.getWording());
        dataCustomerOrderTextField.setText(customer.getName());
        dataWeightOrderTextField.setText(String.valueOf(order.getSIRET()));
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


    private ListCell<Custom<Order>> returnListCellWithImage(ListView<Custom<Order>> lv) {
        return new ListCell<>() {

            private final ImageView imageView = new ImageView();

            @Override
            public void updateItem(Custom<Order> orderCustom, boolean empty) {
                super.updateItem(orderCustom, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Order order = orderCustom.get();
                Image image = IconsType.LOADING_16x16;

                if (order.getEndLocalDateTime() != null) {

                    image = IconsType.CORRECT_16x16;

                    if (order.getEndLocalDateTime().isAfter(LocalDateTime.now()))
                        image = IconsType.WARNING_16x16;

                }


                imageView.setImage(image);
                setGraphic(imageView);
                setText(orderCustom.toString());

            }
        };
    }

    private void setDataProductTourTextField() {
        dataProductTourTextField.setText(productTour.getName());
        dataWeightProductTourTextField.setText(productTour.getWeight().toString());
        dataStartDateProductTourTextField.setText(productTour.getStartDateTime()
                .toString().replace('T', ' '));
        dataEndDateProductTourTextField.setText(productTour.getEndDateTime()
                .toString().replace('T', ' '));
        dataMatriculationVehicleProductTourTextField.setText(productTour.getMatriculation());
    }

    public ListView<Custom<Order>> getOrderListView() {
        return orderListView;
    }
}
