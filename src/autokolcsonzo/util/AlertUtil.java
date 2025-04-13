package autokolcsonzo.util;

import javafx.scene.control.Alert;

public class AlertUtil {
    //A visszajelzések kezelését segítő osztály. Azért tettem külön osztályba, mert több ablakban is használom, így
    //egyszerúbb kezelni, és szebb a kódstruktúra

    public static void showInfo(String message) {
        showAlert(Alert.AlertType.INFORMATION, "Információ", message);
    }

    public static void showError(String message) {
        showAlert(Alert.AlertType.ERROR, "Hiba", message);
    }

    public static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
