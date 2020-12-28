package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.*;
import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.MalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.Comune;
import elaborato_ingegneriaSW.models.MalattiaContagiosa;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.utils.AutoCompleteBox;
import elaborato_ingegneriaSW.utils.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;


public class EditContagiComuniController implements Initializable {
    @FXML
    private JFXComboBox comuneFilterComboBox;

    // TODO: Fix JFXDatePicker, da errore
    //@FXML
    //private JFXDatePicker meseFilterDataPicker;

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

    private final ComuneDaoImpl comuneDao = new ComuneDaoImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();
        try {
            Set<MalattiaContagiosa> malattieContagiose = (Set<MalattiaContagiosa>) malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());
            for (MalattiaContagiosa m: malattieContagiose) {
                // TODO: caricamento interfaccia dinamicamente
                System.out.println(m);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // TODO: Ogni persona avrà solo certi comuni associati, non tutti
        try {
            Set<Comune> comuni = comuneDao.getAllItems(ComuneDaoImpl.getCollectionName());

            for (Comune comune: comuni) {
                comuneFilterComboBox.getItems().add(comune.getNome());
            }
            new AutoCompleteBox(comuneFilterComboBox);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchMalattieContagioseAction(ActionEvent event) {
        System.out.println(comuneFilterComboBox.getValue());
    }

    @FXML
    private void insertMalattieContagioseAction(ActionEvent event) {
        Integer numeroEpisodiInfluenzaliMedicoCurante = null;
        Integer numeroPolmoniteMedicoCurante = null;
        Integer numeroMeningiteMedicoCurante = null;
        Integer numeroEpatiteMedicoCurante = null;
        Integer numeroMorbilloMedicoCurante = null;
        Integer numeroTubercolosiMedicoCurante = null;
        Integer numeroGastroenteriteMedicoCurante = null;

        Integer numeroEpisodiInfluenzaliTerapiaIntensiva = null;
        Integer numeroPolmoniteTerapiaIntensiva = null;
        Integer numeroMeningiteTerapiaIntensiva = null;
        Integer numeroEpatiteTerapiaIntensiva = null;
        Integer numeroMorbilloTerapiaIntensiva = null;
        Integer numeroTubercolosiTerapiaIntensiva = null;
        Integer numeroGastroenteriteTerapiaIntensiva = null;

        try {
            numeroEpisodiInfluenzaliMedicoCurante = Integer.parseInt(episodiInfluenzaliMedicoCuranteTextField.getText());
            numeroPolmoniteMedicoCurante = Integer.parseInt(polmoniteMedicoCuranteTextField.getText());
            numeroMeningiteMedicoCurante = Integer.parseInt(meningiteMedicoCuranteTextField.getText());
            numeroEpatiteMedicoCurante = Integer.parseInt(epatiteMedicoCuranteTextField.getText());
            numeroMorbilloMedicoCurante = Integer.parseInt(morbilloMedicoCuranteTextField.getText());
            numeroTubercolosiMedicoCurante = Integer.parseInt(tubercolosiMedicoCuranteTextField.getText());
            numeroGastroenteriteMedicoCurante = Integer.parseInt(gastroenteriteMedicoCuranteTextField.getText());

            numeroEpisodiInfluenzaliTerapiaIntensiva = Integer.parseInt(episodiInfluenzaliTerapiaIntensivaTextField.getText());
            numeroPolmoniteTerapiaIntensiva = Integer.parseInt(polmoniteTerapiaIntensivaTextField.getText());
            numeroMeningiteTerapiaIntensiva = Integer.parseInt(meningiteTerapiaIntensivaTextField.getText());
            numeroEpatiteTerapiaIntensiva = Integer.parseInt(epatiteTerapiaIntensivaTextField.getText());
            numeroMorbilloTerapiaIntensiva = Integer.parseInt(morbilloTerapiaIntensivaTextField.getText());
            numeroTubercolosiTerapiaIntensiva = Integer.parseInt(tubercolosiTerapiaIntensivaTextField.getText());
            numeroGastroenteriteTerapiaIntensiva = Integer.parseInt(gastroenteriteTerapiaIntensivaTextField.getText());
        } catch (NumberFormatException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "VALORI ERRATA", "Errore durante l'inserimento di alcuni dati! Prova con il punto al posto della virgola", null, event);
            return;
        }
          /*
        Regione newRegione = new Regione(nome, capoluogo, superficie);

        // Controllo se c'è una regione con lo stesso nome
        if(regioneDao.getItem(newRegione.getNome().toLowerCase()) == null) {
            if (regioneDao.addItem(newRegione) == null) {
                FXUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Errore durante l'inserimento!", null, event);
            } else {
                System.out.println("Regione inserita correttamente.");

                // Chiudo la pagina di insert dopo l'avvenuto inserimento
                Stage stage = (Stage) insertRegioneButton.getScene().getWindow();
                stage.close();
            }
        } else {
            if (regioneDao.updateItem(newRegione) == null) {
                FXUtil.Alert(Alert.AlertType.ERROR, "AGGIORNAMENTO FALLITO", "Errore durante l'aggiornamento!", null, event);
            } else {
                System.out.println("Regione aggiornata correttamente.");

                // Chiudo la pagina di insert dopo l'avvenuto inserimento
                Stage stage = (Stage) insertRegioneButton.getScene().getWindow();
                stage.close();
            }
        }     */
    }
}
