package fr.sfc.container.admin;

import fr.sfc.framework.controlling.Container;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public abstract class AbstractSpecificsProducerContainer extends VBox implements Container {

    protected TextField titleProducer;
    protected TextField lastnameProducer;
    protected TextField firstnameProducer;
    protected TextField titleCompany;
    protected TextField SIRETCompany;
    protected TextField addressCompany;
    protected TextField countProductTour;
    protected TextField phoneNumber;

    public TextField getTitleProducer() {
        return titleProducer;
    }

    public TextField getLastnameProducer() {
        return lastnameProducer;
    }

    public TextField getFirstnameProducer() {
        return firstnameProducer;
    }

    public TextField getAddressCompany() {
        return addressCompany;
    }

    public TextField getCountProductTour() {
        return countProductTour;
    }

    public TextField getSIRETCompany() {
        return SIRETCompany;
    }

    public TextField getTitleCompany() {
        return titleCompany;
    }

    public TextField getPhoneNumber() {
        return phoneNumber;
    }
}
