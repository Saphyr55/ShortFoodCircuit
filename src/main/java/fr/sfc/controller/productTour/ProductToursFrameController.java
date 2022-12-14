package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.ProductTourFrame;
import fr.sfc.entity.ProductTour;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.ProductTourRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class ProductToursFrameController implements Controller {

    @FXML
    private VBox containerLabel;
    @FXML
    private VBox containerTextField;
    @FXML
    private TextField tFName;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField tFWeight;
    @FXML
    private TextField tfSIRETCompany;
    @FXML
    private TextField tFMatriculation;
    @FXML
    private Button buttonFinish;
    @FXML
    private Label labelError;


    @AutoContainer
    private ProductTourFrame container;

    @Inject
    private ProductTourRepository productTourRepository;
    @Inject
    private Stage stage;

    @Override
    public void setup() {
        // final var productTours = productTourRepository.findAll(); // Pas besoin pour le moment

        // Bindings container labels
        containerLabel.prefWidthProperty().bind(container.widthProperty().divide(2));
        containerLabel.prefHeightProperty().bind(container.heightProperty());

        // Bindings container text fields
        containerTextField.prefWidthProperty().bind(container.widthProperty().divide(2));
        containerTextField.prefHeightProperty().bind(container.heightProperty());

        // Bindings text fields and date pickers
        tFName.prefWidthProperty().bind(containerTextField.widthProperty());
        tfSIRETCompany.prefWidthProperty().bind(containerTextField.widthProperty());
        tFWeight.prefWidthProperty().bind(containerTextField.widthProperty());
        tFMatriculation.prefWidthProperty().bind(containerTextField.widthProperty());
        startDate.prefWidthProperty().bind(containerLabel.widthProperty());
        endDate.prefWidthProperty().bind(containerLabel.widthProperty());

        // TODO: A bind correctement en fonction de ou te veux le placer
        buttonFinish.prefWidthProperty().bind(containerLabel.widthProperty());
    }

    @FXML
    public void EventButtonAddProductToursFinishAction() {
        if (tfSIRETCompany.getText() == "" || tFMatriculation.getText() == ""){
            this.labelError.setText("need information");
        }
        else {
            this.labelError.setText("");
            ProductTour newProdTour = new ProductTour();
            newProdTour.setSIRET(Integer.parseInt(tfSIRETCompany.getText()));
            newProdTour.setMatriculation(tFMatriculation.getText());
            if( ! startDate.getValue().atStartOfDay().isEqual(null)){
                newProdTour.setStartDateTime(startDate.getValue().atStartOfDay());
            }
            if( ! endDate.getValue().atStartOfDay().isEqual(null)){
                newProdTour.setEndDateTime(endDate.getValue().atStartOfDay());
            }
            if( ! tFWeight.getText().trim().isEmpty()){
                newProdTour.setWeight(Float.valueOf(tFWeight.getText()));
            }
            if( ! tFName.getText().trim().isEmpty()){
                newProdTour.setName((tFName.getText()));
            }
            productTourRepository.insert(newProdTour);
        }
    }

}
