package fr.sfc.container.admin;

import fr.sfc.controller.admin.AdderProducerController;
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

    private final TextField lastnameTextField = new TextField();
    private final TextField firstnameTextField = new TextField();
    private final TextField siretTextField = new TextField();
    private final VBox textFieldContainerVBox = new VBox();
    private final TextField lastnameTextTextField = new TextField();
    private final TextField firstnameTextTextField = new TextField();
    private final TextField siretTextTextField = new TextField();
    private final VBox textFieldTextContainerVBox = new VBox();
    private final Button addProducerButton = new Button();

    @Override
    public void setup() {
        textFieldContainerVBox.getChildren().add(lastnameTextField);
        textFieldContainerVBox.getChildren().add(firstnameTextField);
        textFieldContainerVBox.getChildren().add(siretTextField);
        textFieldContainerVBox.getChildren().add(addProducerButton);

        textFieldTextContainerVBox.getChildren().add(lastnameTextTextField);
        textFieldTextContainerVBox.getChildren().add(firstnameTextTextField);
        textFieldTextContainerVBox.getChildren().add(siretTextTextField);

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
        lastnameTextTextField.setFont(Font.font("Arial", 30));
        firstnameTextTextField.setFont(Font.font("Arial", 30));
        siretTextTextField.setFont(Font.font("Arial", 30));
        addProducerButton.setFont(Font.font("Arial", 20));

        getChildren().add(textFieldContainerVBox);
        getChildren().add(textFieldTextContainerVBox);
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

    public TextField getLastnameTextTextField() {
        return lastnameTextTextField;
    }

    public TextField getFirstnameTextTextField() {
        return firstnameTextTextField;
    }

    public TextField getSiretTextTextField() {
        return siretTextTextField;
    }

    public VBox getTextFieldTextContainerVBox() {
        return textFieldTextContainerVBox;
    }
}
