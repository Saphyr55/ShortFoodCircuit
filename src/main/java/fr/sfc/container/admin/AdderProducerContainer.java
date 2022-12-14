package fr.sfc.container.admin;

import fr.sfc.framework.controlling.Container;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AdderProducerContainer extends HBox implements Container {

    private TextField lastnameTextField = new TextField();
    private TextField firstnameTextField = new TextField();
    private TextField siretTextField = new TextField();

    private VBox textFieldContainerVBox = new VBox();

    private TextField lastnameTextTextField = new TextField();
    private TextField firstnameTextTextField = new TextField();
    private TextField siretTextTextField = new TextField();

    private VBox textFieldTextContainerVBox = new VBox();

    private Button addProducerButton = new Button();

    @Override
    public void setup() {
        textFieldContainerVBox.getChildren().add(lastnameTextField);
        textFieldContainerVBox.getChildren().add(firstnameTextField);
        textFieldContainerVBox.getChildren().add(siretTextField);
        textFieldContainerVBox.getChildren().add(addProducerButton);

        textFieldTextContainerVBox.getChildren().add(lastnameTextTextField);
        textFieldTextContainerVBox.getChildren().add(firstnameTextTextField);
        textFieldTextContainerVBox.getChildren().add(siretTextTextField);

        lastnameTextField.setText("Lastname");
        firstnameTextField.setText("Firstname");
        siretTextField.setText("Siret");
        addProducerButton.setText("Add Producer");

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

        this.getChildren().add(textFieldContainerVBox);
        this.getChildren().add(textFieldTextContainerVBox);
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
