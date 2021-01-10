package elaborato_ingegneriaSW.utils;

import javafx.scene.Node;
import javafx.scene.control.Alert;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

public class FXUtil {

    public static void Alert(Alert.AlertType alertType, String title, String headerText, String contentText, ActionEvent event) {
        Alert alert = new Alert(alertType);
        alert.initOwner(((Node)(event.getSource())).getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
