package fr.sfc.framework.controlling;

import javafx.scene.control.Alert;

import java.util.Optional;

public final class SimpleAlertUtils {

    public static SimpleAlertBuilder createAlertConfirmation() {
        return createAlert(Alert.AlertType.CONFIRMATION);
    }

    public static SimpleAlertBuilder createAlertError() {
        return createAlert(Alert.AlertType.ERROR);
    }

    public static SimpleAlertBuilder createAlert(Alert.AlertType alertType) {
        return SimpleAlertBuilder.of().withAlertType(alertType);
    }

    public static Optional<SimpleAlertBuilder> createAlertErrorConditional(boolean condition) {
        if (condition) return Optional.of(createAlertError());
        return Optional.empty();
    }

    public static Optional<SimpleAlertBuilder> createAlertConfirmationConditional(boolean condition) {
        if (condition)
            return Optional.of(createAlertError());
        return Optional.empty();
    }

}
