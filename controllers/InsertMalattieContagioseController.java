package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;


public class InsertMalattieContagioseController implements Initializable {
    @FXML
    private JFXComboBox comuneComboBox;
    @FXML
    private JFXDatePicker meseDatePicker;
    @FXML
    private JFXButton ricercaButton;
    @FXML
    private JFXButton insertMalattieButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void searchMalattieContagioseAction(ActionEvent event) {

    }

    @FXML
    private void insertMalattieAction(ActionEvent event) {

    }
}
