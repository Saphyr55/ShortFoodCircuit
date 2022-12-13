package fr.sfc.container.admin;

import fr.sfc.framework.controlling.Container;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SpecificsProducerTextContainer extends AbstractSpecificsProducerContainer {

    @Override
    public void setup() {

        titleProducer = new TextField("Producer");
        titleProducer.setFont(Font.font("Arial", 30));
        titleProducer.setDisable(true);

        lastnameProducer = new TextField("Lastname");
        lastnameProducer.setFont(Font.font("Arial", 30));
        lastnameProducer.setDisable(true);

        firstnameProducer = new TextField("Firstname");
        firstnameProducer.setFont(Font.font("Arial", 30));
        firstnameProducer.setDisable(true);

        titleCompany = new TextField("Company");
        titleCompany.setFont(Font.font("Arial", 30));
        titleCompany.setDisable(true);

        SIRETCompany = new TextField("SIRET");
        SIRETCompany.setFont(Font.font("Arial", 30));
        SIRETCompany.setDisable(true);

        addressCompany = new TextField("Address");
        addressCompany.setFont(Font.font("Arial", 30));
        addressCompany.setDisable(true);

        phoneNumber = new TextField("Phone Number");
        phoneNumber.setFont(Font.font("Arial", 30));
        phoneNumber.setDisable(true);

        countProductTour = new TextField("Count Product Tour");
        countProductTour.setFont(Font.font("Arial", 30));
        countProductTour.setDisable(true);

        getChildren().add(titleProducer);
        getChildren().add(lastnameProducer);
        getChildren().add(firstnameProducer);
        getChildren().add(titleCompany);
        getChildren().add(SIRETCompany);
        getChildren().add(addressCompany);
        getChildren().add(phoneNumber);
        getChildren().add(countProductTour);
    }

}
