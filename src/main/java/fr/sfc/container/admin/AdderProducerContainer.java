package fr.sfc.container.admin;

import fr.sfc.common.Pack;
import fr.sfc.container.common.ListSearchDialog;
import fr.sfc.controller.admin.AdderProducerController;
import fr.sfc.entity.Company;
import fr.sfc.framework.controlling.Container;
import fr.sfc.framework.controlling.annotation.AutoController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AdderProducerContainer extends HBox implements Container {

    @AutoController
    private AdderProducerController controller;

    private ListSearchDialog<Pack<Company>> searchSIRETPopup = new ListSearchDialog<>();
    private final TextField lastnameTextField = new TextField();
    private final TextField firstnameTextField = new TextField();
    private final TextField siretTextField = new TextField();
    private final VBox textFieldContainerVBox = new VBox();
    private final TextField lastnameInput = new TextField();
    private final TextField firstnameInput = new TextField();
    private final TextField siretInput = new TextField();
    private final VBox textFieldTextContainerVBox = new VBox();
    private final Button addProducerButton = new Button();

    @Override
    public void setup() {
        textFieldContainerVBox.getChildren().add(lastnameTextField);
        textFieldContainerVBox.getChildren().add(firstnameTextField);
        textFieldContainerVBox.getChildren().add(siretTextField);
        textFieldContainerVBox.getChildren().add(addProducerButton);

        textFieldTextContainerVBox.getChildren().add(lastnameInput);
        textFieldTextContainerVBox.getChildren().add(firstnameInput);
        textFieldTextContainerVBox.getChildren().add(siretInput);

        lastnameTextField.setText("Nom");
        firstnameTextField.setText("Pr\u00E9nom");
        siretTextField.setText("SIRET");
        addProducerButton.setText("Ajout\u00E9 Producteur");

        lastnameTextField.setDisable(true);
        firstnameTextField.setDisable(true);
        siretTextField.setDisable(true);

        lastnameTextField.setFont(Font.font("Arial", 30));
        firstnameTextField.setFont(Font.font("Arial", 30));
        siretTextField.setFont(Font.font("Arial", 30));
        lastnameInput.setFont(Font.font("Arial", 30));
        firstnameInput.setFont(Font.font("Arial", 30));
        siretInput.setFont(Font.font("Arial", 30));
        addProducerButton.setFont(Font.font("Arial", 20));

        getChildren().add(textFieldContainerVBox);
        getChildren().add(textFieldTextContainerVBox);
    }

    public Button getAddProducerButton() {
        return addProducerButton;
    }

    public ListSearchDialog<Pack<Company>> getSearchSIRETPopup() {
        return searchSIRETPopup;
    }

    public TextField getLastnameTextField() {
        return lastnameTextField;
    }

    public TextField getFirstnameTextField() {
        return firstnameTextField;
    }

    public TextField getSiretTextField() {
        return siretTextField;
    }

    public VBox getTextFieldContainerVBox() {
        return textFieldContainerVBox;
    }

    public TextField getLastnameInput() {
        return lastnameInput;
    }

    public TextField getFirstnameInput() {
        return firstnameInput;
    }

    public TextField getSiretInput() {
        return siretInput;
    }

    public VBox getTextFieldTextContainerVBox() {
        return textFieldTextContainerVBox;
    }
}
