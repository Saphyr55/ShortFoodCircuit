package fr.sfc.framework.controlling;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.function.Consumer;

public final class SimpleAlertBuilder {

    private String title;
    private String headerText;
    private String contentText;
    private Alert.AlertType alertType;
    private Consumer<ButtonType> onShowAndWait;
    private Consumer<Alert> onOkButton;
    private Consumer<Alert> onCancelButton;

    public static SimpleAlertBuilder of() {
        return new SimpleAlertBuilder();
    }

    private SimpleAlertBuilder() {
        title = "";
        alertType = Alert.AlertType.NONE;
        headerText = null;
        contentText = null;
        onOkButton = alert -> { };
        onCancelButton = alert -> { };
        onShowAndWait = b -> { };
    }

    public SimpleAlertBuilder withAlertType(Alert.AlertType alertType) {
        this.alertType = alertType;
        return this;
    }

    public SimpleAlertBuilder withHeaderText(String headerText) {
        this.headerText = headerText;
        return this;
    }

    public SimpleAlertBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public SimpleAlertBuilder withContentText(String contentText) {
        this.contentText = contentText;
        return this;
    }

    public SimpleAlertBuilder withOnOkButton(Consumer<Alert> onOkButton) {
        this.onOkButton = onOkButton;
        return this;
    }

    public SimpleAlertBuilder withOnCancelButton(Consumer<Alert> onCancelButton) {
        this.onCancelButton = onCancelButton;
        return this;
    }

    public Alert buildShowAndWait() {
        return setOnShowAndWait(build());
    }

    private Alert setOnShowAndWait(Alert alert) {
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.OK))
                onOkButton.accept(alert);
            else if (buttonType.equals(ButtonType.CANCEL)) {
                onCancelButton.accept(alert);
            }
        });
        return alert;
    }

    public Alert build() {
        var alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);
        return alert;
    }

}
