package fr.sfc.container.admin;

import fr.sfc.controller.admin.AdderCustomerController;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AdderCustomerContainer extends HBox implements Container {

    @AutoController
    private AdderCustomerController controller;

    private final TextField nameTextField = new TextField();
    private final TextField phoneTextField = new TextField();
    private final TextField addressCodeTextField = new TextField();
    private final TextField addressTextField = new TextField();
    private final VBox textFieldContainerVBox = new VBox();
    private final TextField nameInput = new TextField();
    private final TextField phoneInput = new TextField();
    private final TextField addressCodeInput = new TextField();
    private final TextField addressInput = new TextField();
    private final VBox dataFieldTextContainerVBox = new VBox();
    private final Button addButton = new Button();

    @Override
    public void setup() {

        textFieldContainerVBox.getChildren().add(nameTextField);
        dataFieldTextContainerVBox.getChildren().add(nameInput);

        textFieldContainerVBox.getChildren().add(phoneTextField);
        dataFieldTextContainerVBox.getChildren().add(phoneInput);

        textFieldContainerVBox.getChildren().add(addressCodeTextField);
        dataFieldTextContainerVBox.getChildren().add(addressCodeInput);

        textFieldContainerVBox.getChildren().add(addressTextField);
        dataFieldTextContainerVBox.getChildren().add(addressInput);

        textFieldContainerVBox.getChildren().add(addButton);

        nameTextField.setText("Nom");
        addressCodeTextField.setText("Adresse Postal");
        addressTextField.setText("Nom de rue");
        phoneTextField.setText("T\u00E9l\u00E9phone");
        addButton.setText("Ajout\u00E9 Client");

        nameTextField.setDisable(true);
        phoneTextField.setDisable(true);
        addressCodeTextField.setDisable(true);
        addressTextField.setDisable(true);

        nameTextField.setFont(Font.font("Arial", 30));
        phoneTextField.setFont(Font.font("Arial", 30));
        addressCodeTextField.setFont(Font.font("Arial", 30));
        addressTextField.setFont(Font.font("Arial", 30));

        nameInput.setFont(Font.font("Arial", 30));
        addressCodeInput.setFont(Font.font("Arial", 30));
        addressInput.setFont(Font.font("Arial", 30));
        phoneInput.setFont(Font.font("Arial", 30));

        addButton.setFont(Font.font("Arial", 20));

        getChildren().add(textFieldContainerVBox);
        getChildren().add(dataFieldTextContainerVBox);
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getPhoneTextField() {
        return phoneTextField;
    }

    public TextField getAddressCodeTextField() {
        return addressCodeTextField;
    }

    public TextField getAddressTextField() {
        return addressTextField;
    }

    public VBox getTextFieldContainerVBox() {
        return textFieldContainerVBox;
    }

    public TextField getNameInput() {
        return nameInput;
    }

    public TextField getPhoneInput() {
        return phoneInput;
    }

    public TextField getAddressCodeInput() {
        return addressCodeInput;
    }

    public TextField getAddressInput() {
        return addressInput;
    }

    public VBox getDataFieldTextContainerVBox() {
        return dataFieldTextContainerVBox;
    }

    public Button getAddButton() {
        return addButton;
    }
}
