package fr.sfc.framework.controlling;

import javafx.scene.control.Alert;

import java.util.Optional;

public final class SimpleAlertUtils {

    public static Alert createAlertConfirmation(String title, String content) {
        return createAlert(Alert.AlertType.CONFIRMATION, title, content);
    }

    public static Alert createAlertError(String title, String content) {
        return createAlert(Alert.AlertType.ERROR, title, content);
    }

    public static Alert createAlert(Alert.AlertType alertType, String title, String content) {
        return SimpleAlertBuilder.of()
                .withAlertType(alertType)
                .withContentText(content)
                .withTitle(title)
                .build();
    }

    public static Optional<Alert> createAlertErrorConditional(String title, String content, boolean condition) {
        if (condition)
            return Optional.of(createAlertError(title, content));
        return Optional.empty();
    }

}
