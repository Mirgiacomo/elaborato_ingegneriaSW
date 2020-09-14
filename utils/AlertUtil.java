package elaborato_ingegneriaSW.utils;

import elaborato_ingegneriaSW.MainApp;
import javafx.scene.control.Alert;

public class AlertUtil {

    public static void Alert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
