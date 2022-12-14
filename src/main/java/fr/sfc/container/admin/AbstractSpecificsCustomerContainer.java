package fr.sfc.container.admin;

import fr.sfc.framework.controlling.Container;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public abstract class AbstractSpecificsCustomerContainer extends VBox implements Container {

    protected TextField title;
    protected TextField name;
    protected TextField address;
    protected TextField phoneNumber;

    protected void addAll() {
        getChildren().add(title);
        getChildren().add(name);
        getChildren().add(address);
        getChildren().add(phoneNumber);
    }

    public void disableAll(boolean disable) {
        title.setDisable(true);
        name.setDisable(disable);
        address.setDisable(disable);
        phoneNumber.setDisable(disable);
    }

    public TextField getTitle() {
        return title;
    }
    public TextField getName() {
        return name;
    }
    public TextField getAddress() {
        return address;
    }
    public TextField getPhoneNumber() {
        return phoneNumber;
    }
}
