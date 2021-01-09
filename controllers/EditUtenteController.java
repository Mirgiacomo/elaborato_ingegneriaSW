package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.google.common.hash.Hashing;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.dao.UtenteDaoImpl;
import elaborato_ingegneriaSW.models.*;
import elaborato_ingegneriaSW.utils.AutoCompleteBox;
import elaborato_ingegneriaSW.utils.FXUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

public class EditUtenteController implements Initializable, EditController<Utente> {

    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField cognomeTextField;
    @FXML
    private JFXTextField cfTextField;
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXPasswordField password1PasswordField;
    @FXML
    private JFXPasswordField password2PasswordField;
    @FXML
    private JFXComboBox ruoloComboBox;
    @FXML
    private JFXButton saveButton;
    @FXML
    private CheckComboBox comuniCheckComboBox;

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

        try {
            Set<Comune> comuni = comuneDao.getAllItems(ComuneDaoImpl.getCollectionName());

            for (Comune comune: comuni) {
                comuniCheckComboBox.getItems().add(comune);
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    @Override
    public void saveAction(ActionEvent event) throws ExecutionException, InterruptedException {

        // Controllo se il cf è valido
        Pattern pattern = Pattern.compile("^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cfTextField.getText());
        boolean matchFound = matcher.find();
        if(nomeTextField.getText().isBlank() || cognomeTextField.getText().isBlank() || usernameTextField.getText().isBlank() || password1PasswordField.getText().isBlank() || password2PasswordField.getText().isBlank() || cfTextField.getText().isBlank() || !(matchFound)){
            FXUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Dati non validi!", null, event);
            return;
        }

        String nome = nomeTextField.getText();
        String cognome = cognomeTextField.getText();
        String cf = cfTextField.getText();
        String username = usernameTextField.getText();
        String password1 = password1PasswordField.getText();
        String password2 = password2PasswordField.getText();
        if (!(password1.equals(password2))) {
            FXUtil.Alert(Alert.AlertType.ERROR, "PASSWORD ERRATE", "Le password non coincidono!", null, event);
        }
        RuoloUtente ruolo = (RuoloUtente) ruoloComboBox.getValue();

        ObservableList<Comune> comuni = comuniCheckComboBox.getCheckModel().getCheckedItems();
        Set<Comune> comuniAssociati = new HashSet<>();
        if (!comuni.isEmpty()) {
            for (Comune c: comuni) {
                comuniAssociati.add(c);
            }
        }

        System.out.println("provola");
        // comuniAssociati.add(new Comune("123456", "Bovolone", "2021-01-01", "85", Territorio.COLLINA, true, ));
        Utente newUtente = new Utente(cognome, nome, username, Hashing.sha256().hashString(password1, StandardCharsets.UTF_8).toString(), ruolo, cf, comuniAssociati);
        if (utenteDao.addItem(newUtente) == null) {
            FXUtil.Alert(Alert.AlertType.ERROR, "AGGIORNAMENTO UTENTE FALLITO", "Errore durante l'inserimento dell'utente!", null, event);
        } else {
            System.out.println("Utente inserito/aggiornato correttamente.");

            // Chiudo la pagina di insert dopo l'avvenuto inserimento
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void populateForm(Utente model) {
        nomeTextField.setText(model.getNome());
        cognomeTextField.setText(model.getCognome());
        usernameTextField.setText(model.getUsername());
        usernameTextField.setDisable(true);
        password1PasswordField.setText(model.getPassword());
        password1PasswordField.setDisable(true);
        password2PasswordField.setText(model.getPassword());
        password2PasswordField.setDisable(true);
        cfTextField.setText(model.getCf());
        ruoloComboBox.getSelectionModel().select(model.getRuolo());
        // TODO: selezionare i comuni giusti
    }

}
