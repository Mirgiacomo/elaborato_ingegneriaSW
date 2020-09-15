package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author mirgi
 */
public class InsertComuneController implements Initializable {
    @FXML
    private JFXTextField ISTATTextField;
    @FXML
    private JFXDatePicker dataIstituzioneDataPicker;
    @FXML
    private JFXTextField nomeComuneTextField;
    @FXML
    private JFXTextField superficieTextField;
    @FXML
    private JFXComboBox tipoTerritorioComboBox;
    @FXML
    private JFXCheckBox fronteMareCheckBox;
    @FXML
    private JFXComboBox ProvinciaCollegataComboBox;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void insertComuneAction(ActionEvent event) throws ExecutionException, InterruptedException {
        System.out.println(ISTATTextField.getText());
    }
    
}
