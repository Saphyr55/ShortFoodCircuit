package fr.sfc.container.admin;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class SpecificsCustomerDataContainer extends AbstractSpecificsCustomerContainer {

    @Override
    public void setup() {
        title = new TextField();
        title.setFont(Font.font("Arial", 30));
        name = new TextField();
        name.setFont(Font.font("Arial", 30));
        address = new TextField();
        address.setFont(Font.font("Arial", 30));
        phoneNumber = new TextField();
        phoneNumber.setFont(Font.font("Arial", 30));
        addAll();
        disableAll(true);
    }

}
