package fr.sfc.container.admin;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class SpecificsProducerTextContainer extends AbstractSpecificsProducerContainer {

    @Override
    public void setup() {

        titleProducer = new TextField("Producteur");
        titleProducer.setFont(Font.font("Arial", 30));
        titleProducer.setDisable(true);

        lastnameProducer = new TextField("Nom");
        lastnameProducer.setFont(Font.font("Arial", 30));
        lastnameProducer.setDisable(true);

        firstnameProducer = new TextField("Pr\u00E9nom");
        firstnameProducer.setFont(Font.font("Arial", 30));
        firstnameProducer.setDisable(true);

        titleCompany = new TextField("Entreprise");
        titleCompany.setFont(Font.font("Arial", 30));
        titleCompany.setDisable(true);

        SIRETCompany = new TextField("SIRET");
        SIRETCompany.setFont(Font.font("Arial", 30));
        SIRETCompany.setDisable(true);

        addressCompany = new TextField("Adresse");
        addressCompany.setFont(Font.font("Arial", 30));
        addressCompany.setDisable(true);

        phoneNumber = new TextField("T\u00E9l\u00E9phone");
        phoneNumber.setFont(Font.font("Arial", 30));
        phoneNumber.setDisable(true);

        countProductTour = new TextField("Nombre de tourn\u00E9e");
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
