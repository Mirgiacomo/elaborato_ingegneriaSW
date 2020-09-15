package elaborato_ingegneriaSW.utils;

import javafx.scene.Node;
import javafx.scene.control.Alert;

import javafx.event.ActionEvent;

public class AlertUtil {

    public static void Alert(Alert.AlertType alertType, String title, String headerText, String contentText, ActionEvent event) {
        Alert alert = new Alert(alertType);
        alert.initOwner(((Node)(event.getSource())).getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
