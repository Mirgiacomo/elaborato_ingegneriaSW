package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;


public class InsertMalattieContagioseController implements Initializable {
    @FXML
    private JFXComboBox comuneFilterComboBox;
    @FXML
    private JFXDatePicker meseFilterDatePicker;
    @FXML
    private JFXButton ricercaFilterButton;
    @FXML
    private JFXTextField episodiInfluenzaliMedicoCuranteTextField;
    @FXML
    private JFXTextField polmoniteMedicoCuranteTextField;
    @FXML
    private JFXTextField meningiteMedicoCuranteTextField;
    @FXML
    private JFXTextField epatiteMedicoCuranteTextField;
    @FXML
    private JFXTextField morbilloMedicoCuranteTextField;
    @FXML
    private JFXTextField tubercolosiMedicoCuranteTextField;
    @FXML
    private JFXTextField gastroenteriteMedicoCuranteTextField;
    @FXML
    private JFXTextField episodiInfluenzaliTerapiaIntensivaTextField;
    @FXML
    private JFXTextField polmoniteTerapiaIntensivaTextField;
    @FXML
    private JFXTextField meningiteTerapiaIntensivaTextField;
    @FXML
    private JFXTextField epatiteTerapiaIntensivaTextField;
    @FXML
    private JFXTextField morbilloTerapiaIntensivaTextField;
    @FXML
    private JFXTextField tubercolosiTerapiaIntensivaTextField;
    @FXML
    private JFXTextField gastroenteriteTerapiaIntensivaTextField;
    @FXML
    private JFXCheckBox complicanzeRespiratorieCheckBox;
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
