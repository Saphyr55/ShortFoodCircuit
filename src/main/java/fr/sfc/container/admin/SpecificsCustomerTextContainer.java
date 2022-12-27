package fr.sfc.container.admin;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class SpecificsCustomerTextContainer extends AbstractSpecificsCustomerContainer {


    @Override
    public void setup() {

        title = new TextField("Client");
        title.setFont(Font.font("Arial", 30));
        name = new TextField("Nom");
        name.setFont(Font.font("Arial", 30));
        address = new TextField("Adresse");
        address.setFont(Font.font("Arial", 30));
        phoneNumber = new TextField("T\u00E9l\u00E9phone");
        phoneNumber.setFont(Font.font("Arial", 30));
        disableAll(true);
        addAll();

    }



}
