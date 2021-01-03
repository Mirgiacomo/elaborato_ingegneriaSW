package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.*;
import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.MalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.*;
import elaborato_ingegneriaSW.utils.AutoCompleteBox;
import elaborato_ingegneriaSW.utils.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class EditContagiComuniController implements Initializable {
    // TODO: Fix JFXDatePicker, da errore
    //@FXML
    //private JFXDatePicker meseFilterDataPicker;
    @FXML
    private JFXButton ricercaFilterButton;
    @FXML
    private JFXComboBox<Comune> comuneFilterComboBox;
    @FXML
    private GridPane contagiGridPane;
    @FXML
    private JFXButton saveButton;

    private final ComuneDaoImpl comuneDao = new ComuneDaoImpl();

    private Map<String, Set<JFXTextField>> form = new HashMap<>();

    private Map<String, Set<JFXTextField>> formComplications = new HashMap<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();
        try {
            Set<MalattiaContagiosa> malattieContagiose = (Set<MalattiaContagiosa>) malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());

            int row = 1;

            for (MalattiaContagiosa m: malattieContagiose) {
                Label label = new Label(m.getNome());
                JFXTextField terapiaIntensiva = new JFXTextField();
                terapiaIntensiva.setId("terapiaIntensiva");
                JFXTextField medicoBase = new JFXTextField();
                medicoBase.setId("medicoBase");

                Set<JFXTextField> inputs = new HashSet<>();
                inputs.add(terapiaIntensiva);
                inputs.add(medicoBase);

                GridPane complications = new GridPane();

                if (!m.getComplications().isEmpty()) {
                    int complicationsRow = 0;
                    Set<JFXTextField> inputsComplications = new HashSet<>();

                    for (String c: m.getComplications()) {
                        Label cLabel = new Label(c);
                        JFXTextField valueInput = new JFXTextField();
                        valueInput.setId(c);
                        inputsComplications.add(valueInput);

                        complications.addRow(complicationsRow, cLabel, valueInput);
                        complicationsRow++;
                    }
                    formComplications.put(m.getNome(), inputsComplications);
                }
                form.put(m.getNome(), inputs);
                contagiGridPane.addRow(row++, label, terapiaIntensiva, medicoBase, complications);
                // TODO: stile tabella
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // TODO: Ogni persona avrà solo certi comuni associati, non tutti
        try {
            Set<Comune> comuni = comuneDao.getAllItems(ComuneDaoImpl.getCollectionName());

            for (Comune comune: comuni) {
                comuneFilterComboBox.getItems().add(comune);
            }
            new AutoCompleteBox(comuneFilterComboBox);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchMalattieContagioseAction(ActionEvent event) {
        System.out.println(comuneFilterComboBox.getValue());
    }

    @FXML
    private void saveAction(ActionEvent event) {
        try {
            for (Map.Entry<String, Set<JFXTextField>> entry: form.entrySet()) {
                Contagio newContagio = new Contagio();
                newContagio.setMalattiaContagiosa(new MalattiaContagiosa(entry.getKey()));

                for (JFXTextField input: entry.getValue()) {
                    if (input.getId().equals("terapiaIntensiva")) {
                        String value = input.getText();
                        if (value != null && !value.equals("")) {
                            newContagio.setNumeroTerapiaIntensiva(Integer.parseInt(value));
                        }
                    } else if (input.getId().equals("medicoBase")) {
                        String value = input.getText();
                        if (value != null && !value.equals("")) {
                            newContagio.setNumeroMedicoBase(Integer.parseInt(value));
                        }
                    }
                }
                if (formComplications.containsKey(entry.getKey())) {
                    for (JFXTextField complication: formComplications.get(entry.getKey())) {
                        String value = complication.getText();
                        if (value != null) {
                            newContagio.addComplication(complication.getId(), Integer.parseInt(value));
                        }
                    }
                }
                System.out.println(newContagio);
            }
        } catch (NumberFormatException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "VALORI ERRATA", "Errore durante l'inserimento di alcuni dati! Prova con il punto al posto della virgola", null, event);
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
