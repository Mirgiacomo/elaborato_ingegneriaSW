package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class InsertPersonaleContrattoController implements Initializable {

    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField cognomeTextField;
    @FXML
    private JFXDatePicker dataNascitaDatePicker;
    @FXML
    private JFXTextField cfTextField;
    @FXML
    private JFXComboBox comuneAssociatoComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void insertPersonaleAction(ActionEvent event) throws ExecutionException, InterruptedException {
        System.out.println(cognomeTextField.getText());
    }
}
