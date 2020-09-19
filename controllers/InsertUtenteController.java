package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.dao.UtenteDaoImpl;
import elaborato_ingegneriaSW.models.*;
import elaborato_ingegneriaSW.utils.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class InsertUtenteController implements Initializable {

    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField cognomeTextField;
    @FXML
    private JFXTextField cfTextField;
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXTextField passwordTextField;
    @FXML
    private JFXComboBox ruoloComboBox;

    private final UtenteDaoImpl utenteDao = new UtenteDaoImpl();
    private final ComuneDaoImpl comuneDao = new ComuneDaoImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (RuoloUtente ruolo: RuoloUtente.values()) {
            ruoloComboBox.getItems().add(ruolo);
        }
    }

    @FXML
    private void insertUtenteAction(ActionEvent event) throws Exception {
        // Controllo se il cf è valido
        Pattern pattern = Pattern.compile("^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cfTextField.getText());
        boolean matchFound = matcher.find();
        if(nomeTextField.getText().isBlank() || cognomeTextField.getText().isBlank() || usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank() || cfTextField.getText().isBlank() || !(matchFound)){
            FXUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Dati non validi!", null, event);
            return;
        }

        //TODO: aggiungere comuni

        String nome = nomeTextField.getText();
        String cognome = cognomeTextField.getText();
        String cf = cfTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        RuoloUtente ruolo = (RuoloUtente) ruoloComboBox.getValue();

        Utente newUtente = new Utente(cognome, nome, username, password, ruolo, cf);
        if (utenteDao.addItem(newUtente) == null) {
            FXUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO UTENTE FALLITO", "Errore durante l'inserimento dell'utente!", null, event);
        } else {
            System.out.println("Utente inserito correttamente.");
        }

    }
}
