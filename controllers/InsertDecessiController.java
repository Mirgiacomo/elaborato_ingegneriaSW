package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Territorio;
import elaborato_ingegneriaSW.utils.AutoCompleteBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;


public class InsertDecessiController  implements Initializable {
    @FXML
    private JFXComboBox provinciaComboBox;
    @FXML
    private JFXComboBox annoComboBox;
    @FXML
    private TreeView provola;
    @FXML
    private JFXButton ricercaButton;
    @FXML
    private JFXButton insertDecessiButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void searchDecessiAction(ActionEvent event) {

    }

    @FXML
    private void insertDecessiAction(ActionEvent event) {

    }
}
