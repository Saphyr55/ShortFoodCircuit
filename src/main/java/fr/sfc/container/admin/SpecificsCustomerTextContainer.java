package fr.sfc.container.admin;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class SpecificsCustomerTextContainer extends AbstractSpecificsCustomerContainer {


    @Override
    public void setup() {

        title = new TextField("Customer");
        title.setFont(Font.font("Arial", 30));
        name = new TextField("Name");
        name.setFont(Font.font("Arial", 30));
        address = new TextField("Address");
        address.setFont(Font.font("Arial", 30));
        phoneNumber = new TextField("Phone Number");
        phoneNumber.setFont(Font.font("Arial", 30));
        disableAll(true);
        addAll();

    }



}
