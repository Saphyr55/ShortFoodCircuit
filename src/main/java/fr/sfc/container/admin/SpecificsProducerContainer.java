package fr.sfc.container.admin;

import fr.sfc.framework.controlling.Container;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SpecificsProducerContainer extends AbstractSpecificsProducerContainer {

    @Override
    public void setup() {

        titleProducer = new TextField("-----------------------");
        titleProducer.setFont(Font.font("Arial", 30));
        titleProducer.setDisable(true);

        lastnameProducer = new TextField();
        lastnameProducer.setFont(Font.font("Arial", 30));
        lastnameProducer.setDisable(true);

        firstnameProducer = new TextField();
        firstnameProducer.setFont(Font.font("Arial", 30));
        firstnameProducer.setDisable(true);

        titleCompany = new TextField("-----------------------");
        titleCompany.setFont(Font.font("Arial", 30));
        titleCompany.setDisable(true);

        SIRETCompany = new TextField();
        SIRETCompany.setFont(Font.font("Arial", 30));
        SIRETCompany.setDisable(true);

        addressCompany = new TextField();
        addressCompany.setFont(Font.font("Arial", 30));
        addressCompany.setDisable(true);

        phoneNumber = new TextField();
        phoneNumber.setFont(Font.font("Arial", 30));
        phoneNumber.setDisable(true);

        countProductTour = new TextField();
        countProductTour.setFont(Font.font("Arial", 30));
        countProductTour.setDisable(true);

        getChildren().addAll(
                titleProducer,
                lastnameProducer,
                firstnameProducer,
                titleCompany,
                SIRETCompany,
                addressCompany,
                phoneNumber,
                countProductTour);

    }
}
