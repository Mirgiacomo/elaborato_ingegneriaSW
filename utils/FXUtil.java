package elaborato_ingegneriaSW.utils;

import javafx.scene.Node;
import javafx.scene.control.Alert;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class FXUtil {

    public static void Alert(Alert.AlertType alertType, String title, String headerText, String contentText, ActionEvent event) {
        Alert alert = new Alert(alertType);
        alert.initOwner(((Node)(event.getSource())).getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static final LocalDate NOW_LOCAL_DATE (){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date , formatter);
    }
}
