package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.DecessoDaoImpl;
import elaborato_ingegneriaSW.dao.DecessoMalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.dao.MalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.*;
import elaborato_ingegneriaSW.utils.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class EditDecessiProvinceController implements Initializable {
    @FXML
    private SearchableComboBox<Provincia> provinciaFilterComboBox;
    @FXML
    private SearchableComboBox<Integer> yearFilterComboBox;
    @FXML
    private JFXButton loadDecessiButton;
    @FXML
    private GridPane decessiGridPane;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton azzeraButton;

    private final DecessoDaoImpl decessoDao = new DecessoDaoImpl();
    private final DecessoMalattiaContagiosaDaoImpl decessoMalattiaContagiosaDao = new DecessoMalattiaContagiosaDaoImpl();

    private final ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

    private Map<String, Set<JFXTextField>> form = new HashMap<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();

        try {
            Set<Provincia> province = provinciaDao.getAllItems(ProvinciaDaoImpl.getCollectionName());

            for (Provincia provincia: province) {
                provinciaFilterComboBox.getItems().add(provincia);
            }

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            yearFilterComboBox.getItems().add(currentYear - 1);
            yearFilterComboBox.getItems().add(currentYear);

            Set<MalattiaContagiosa> malattieContagiose = malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());
            decessiGridPane.getStyleClass().add("grid-pane");
            int row = 1;

            for (CausaDecesso causaDecesso: CausaDecesso.values()) {
                if (!causaDecesso.equals(CausaDecesso.MALATTIA_CONTAGIOSA)) {
                    Label label = new Label(causaDecesso.getNome().toUpperCase());
                    JFXTextField number = new JFXTextField();
                    number.setId("number");

                    Set<JFXTextField> inputs = new HashSet<>();
                    inputs.add(number);

                    form.put(causaDecesso.name(), inputs);
                    decessiGridPane.addRow(row++, label, number);
                }
            }
            for (MalattiaContagiosa m: malattieContagiose) {
                Label label = new Label(m.getNome().toUpperCase());
                JFXTextField number = new JFXTextField();
                number.setId("number");

                Set<JFXTextField> inputs = new HashSet<>();
                inputs.add(number);

                form.put(m.generateId(), inputs);
                decessiGridPane.addRow(row++, label, number);
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        saveButton.setDisable(true);
    }

    @FXML
    public void loadDecessiAction(ActionEvent event) throws ExecutionException, InterruptedException {
        if (provinciaFilterComboBox.getSelectionModel().isEmpty() || yearFilterComboBox.getSelectionModel().isEmpty()) {
            saveButton.setDisable(true);
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE", "Selezionare una provincia e un anno!", null, event);
            return;
        }
        for (Map.Entry<String, Set<JFXTextField>> entry: form.entrySet()) {
            for (JFXTextField input: entry.getValue()) {
                input.setText("");
            }
        }

        Provincia provincia = provinciaFilterComboBox.getSelectionModel().getSelectedItem();
        int year = yearFilterComboBox.getSelectionModel().getSelectedItem();

        Set<Decesso> resultDecessi = decessoDao.getFilteredItems(provincia, year);

        if (resultDecessi != null && !resultDecessi.isEmpty()) {
            for (Decesso decesso: resultDecessi) {
                Set<JFXTextField> inputs = null;
                if (form.containsKey(decesso.getCausaDecesso().name())) {
                    inputs = form.get(decesso.getCausaDecesso().name());
                }

                if (inputs != null && !inputs.isEmpty()) {
                    for (JFXTextField input : inputs) {
                        input.setText(String.valueOf(decesso.getNumeroMorti()));
                    }
                }
            }
        }

        Set<DecessoMalattiaContagiosa> resultDecessiMalattiaContagiosa = decessoMalattiaContagiosaDao.getFilteredItems(provincia, year);

        if (resultDecessiMalattiaContagiosa != null && !resultDecessiMalattiaContagiosa.isEmpty()) {
            for (Decesso decesso: resultDecessiMalattiaContagiosa) {
                Set<JFXTextField> inputs = null;
                DecessoMalattiaContagiosa decessoMalattiaContagiosa = (DecessoMalattiaContagiosa) decesso;
                if (form.containsKey(decessoMalattiaContagiosa.getMalattiaContagiosa().generateId())) {
                    inputs = form.get(decessoMalattiaContagiosa.getMalattiaContagiosa().generateId());
                }

                if (inputs != null && !inputs.isEmpty()) {
                    for (JFXTextField input : inputs) {
                        input.setText(String.valueOf(decesso.getNumeroMorti()));
                    }
                }
            }
        }
        saveButton.setDisable(false);
    }

    @FXML
    public void saveAction(ActionEvent event) {
        if (provinciaFilterComboBox.getSelectionModel().isEmpty() || yearFilterComboBox.getSelectionModel().isEmpty()) {
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE SALVATAGGIO", "Selezionare un comune e una data! Se premi SELEZIONA, perdi eventuali dati inseriti", null, event);
            return;
        }

        Provincia provincia = provinciaFilterComboBox.getSelectionModel().getSelectedItem();
        int year = yearFilterComboBox.getSelectionModel().getSelectedItem();

        try {
            for (Map.Entry<String, Set<JFXTextField>> entry: form.entrySet()) {
                boolean isMalattiaContagiosa = true;
                for (CausaDecesso causaDecesso: CausaDecesso.values()) {
                    if (!causaDecesso.equals(CausaDecesso.MALATTIA_CONTAGIOSA) && causaDecesso.name().equals(entry.getKey())) {
                        isMalattiaContagiosa = false;
                        break;
                    }
                }

                if (isMalattiaContagiosa) {
                    MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();
                    MalattiaContagiosa malattiaContagiosa = malattiaContagiosaDao.getItem(entry.getKey());

                    int numeroMorti = 0;
                    for (JFXTextField input: entry.getValue()) {
                        numeroMorti = input.getText().isBlank() ? 0 : Integer.parseInt(input.getText());
                    }
                    DecessoMalattiaContagiosa newDecessoMalattiaContagiosa = new DecessoMalattiaContagiosa(provincia, year, numeroMorti, malattiaContagiosa);
                    decessoMalattiaContagiosaDao.saveItem(newDecessoMalattiaContagiosa);
                    // DEBUG
                    // System.out.println(newDecessoMalattiaContagiosa);
                } else {
                    CausaDecesso causaDecesso = CausaDecesso.valueOf(entry.getKey());
                    int numeroMorti = 0;

                    for (JFXTextField input: entry.getValue()) {
                        numeroMorti = input.getText().isBlank() ? 0 : Integer.parseInt(input.getText());
                    }
                    Decesso newDecesso = new Decesso(provincia, year, numeroMorti, causaDecesso);
                    decessoDao.saveItem(newDecesso);
                    // DEBUG
                    // System.out.println(newDecesso);
                }
            }
            FXUtil.Alert(Alert.AlertType.INFORMATION, "SALVATAGGIO COMPLETATO", "Salvataggio completato con successo!", null, event);
        } catch (NumberFormatException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "VALORI ERRATI", "Errore durante il salvataggio!", null, event);
        } catch (InterruptedException | ExecutionException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE", "Errore durante l'esecuzione!", null, event);
            // DEBUG
            // e.printStackTrace();
        }
    }

    public void azzeraAction(ActionEvent actionEvent) {
        for (Map.Entry<String, Set<JFXTextField>> entry: form.entrySet()) {
            for (JFXTextField input: entry.getValue()) {
                input.setText("");
            }
        }
    }
}
