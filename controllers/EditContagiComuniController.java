package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.*;
import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.ContagioDaoImpl;
import elaborato_ingegneriaSW.dao.MalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.models.*;
import elaborato_ingegneriaSW.utils.FXUtil;
import elaborato_ingegneriaSW.utils.LoggedUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class EditContagiComuniController implements Initializable {
    private Utente loggedUser;

    @FXML
    private AnchorPane editContagiRoot;
    @FXML
    private SearchableComboBox<Comune> comuneFilterComboBox;
    @FXML
    private DatePicker weekFilterDatePicker;
    @FXML
    private GridPane contagiGridPane;
    @FXML
    private JFXButton saveButton;

    private final ComuneDaoImpl comuneDao = new ComuneDaoImpl();
    private final ContagioDaoImpl contagioDao = new ContagioDaoImpl();

    private Map<String, Set<JFXTextField>> form = new HashMap<>();
    private Map<String, Set<JFXTextField>> formComplications = new HashMap<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedUser = LoggedUser.getLoggedUser();

        if (loggedUser != null) {
            weekFilterDatePicker.setValue(FXUtil.NOW_LOCAL_DATE());
            MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();

            try {
                Set<MalattiaContagiosa> malattieContagiose = malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());
                contagiGridPane.getStyleClass().add("grid-pane");
                int row = 1;

                for (MalattiaContagiosa m: malattieContagiose) {
                    Label label = new Label(m.getNome().toUpperCase());

                    JFXTextField terapiaIntensiva = new JFXTextField();
                    terapiaIntensiva.setId("terapiaIntensiva");

                    JFXTextField medicoBase = new JFXTextField();
                    medicoBase.setId("medicoBase");

                    Set<JFXTextField> inputs = new HashSet<>();
                    inputs.add(terapiaIntensiva);
                    inputs.add(medicoBase);

                    GridPane complications = new GridPane();
                    complications.setHgap(15);
                    complications.setVgap(15);

                    if (!m.getComplications().isEmpty()) {
                        int complicationsRow = 0;
                        Set<JFXTextField> inputsComplications = new HashSet<>();

                        for (String c: m.getComplications()) {
                            Label cLabel = new Label(c.toUpperCase());
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
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Set<Comune> comuni = new HashSet<>();

                if (loggedUser.getRuolo().equals(RuoloUtente.ADMIN)) {
                    comuni = comuneDao.getAllItems(ComuneDaoImpl.getCollectionName());
                } else if (loggedUser.getRuolo().equals(RuoloUtente.PERSONALE_CONTAGI)) {
                    comuni = loggedUser.getComuniAssociati();
                }
                for (Comune comune: comuni) {
                    comuneFilterComboBox.getItems().add(comune);
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            saveButton.setDisable(true);
        }
    }

    /**
     * Carica eventuali contagi già inseriti per il comune e la settimana selezionati
     * @param event
     */
    @FXML
    public void loadContagiAction(ActionEvent event) throws ExecutionException, InterruptedException {
        if (comuneFilterComboBox.getSelectionModel().isEmpty() || weekFilterDatePicker.getValue() == null) {
            saveButton.setDisable(true);
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE", "Selezionare un comune e una data!", null, event);
            return;
        }
        for (Map.Entry<String, Set<JFXTextField>> entry: form.entrySet()) {
            for (JFXTextField input: entry.getValue()) {
                input.setText("");
            }
            if (formComplications.containsKey(entry.getKey())) {
                for (JFXTextField complication : formComplications.get(entry.getKey())) {
                    complication.setText("");
                }
            }
        }

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();

        Comune comune = comuneFilterComboBox.getSelectionModel().getSelectedItem();
        LocalDate date = weekFilterDatePicker.getValue();
        date = date.with(TemporalAdjusters.previousOrSame(firstDayOfWeek)); // prende il lunedì della settimana selezionata (altrimenti se ci sono settimane a metà tra un anno e l'altro potrebbero esserci problemi

        if (date.compareTo(FXUtil.NOW_LOCAL_DATE()) > 0) {
            saveButton.setDisable(true);
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE", "La data selezionata non è valida, selezionare un giorno NON FUTURO!", null, event);
            return;
        }
        int year = date.getYear();
        int week = date.get(weekFields.weekOfWeekBasedYear());

        Set<Contagio> result = contagioDao.getFilteredItems(comune, week, year);

        if (result != null && !result.isEmpty()) {
            for (Contagio contagio : result) {
                Set<JFXTextField> inputs = null;
                if (form.containsKey(contagio.getMalattiaContagiosa().getNome())) {
                    inputs = form.get(contagio.getMalattiaContagiosa().getNome());
                }
                if (inputs != null && !inputs.isEmpty()) {
                    for (JFXTextField input : inputs) {
                        switch (input.getId()) {
                            case "terapiaIntensiva":
                                input.setText(String.valueOf(contagio.getNumeroTerapiaIntensiva()));
                                break;
                            case "medicoBase":
                                input.setText(String.valueOf(contagio.getNumeroMedicoBase()));
                                break;
                        }
                    }
                }
                if (formComplications.containsKey(contagio.getMalattiaContagiosa().getNome())) {
                    for (JFXTextField complication : formComplications.get(contagio.getMalattiaContagiosa().getNome())) {
                        complication.setText(String.valueOf(contagio.getComplications().get(complication.getId())));
                    }
                }
            }
        }
        saveButton.setDisable(false);
    }

    @FXML
    private void saveAction(ActionEvent event) {
        MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();

        if (comuneFilterComboBox.getSelectionModel().isEmpty() || weekFilterDatePicker.getValue() == null) {
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE SALVATAGGIO", "Selezionare un comune e una data!", null, event);
            return;
        }

        Comune comune = comuneFilterComboBox.getSelectionModel().getSelectedItem();
        LocalDate date = weekFilterDatePicker.getValue();
        date = date.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));

        if (date.compareTo(FXUtil.NOW_LOCAL_DATE()) > 0) {
            saveButton.setDisable(true);
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE", "La data selezionata non è valida, selezionare un giorno NON FUTURO!", null, event);
            return;
        }

        int year = date.getYear();
        int week = date.get(weekFields.weekOfWeekBasedYear());

        try {
            for (Map.Entry<String, Set<JFXTextField>> entry: form.entrySet()) {
                Contagio newContagio = new Contagio();
                newContagio.setComune(comune);
                newContagio.setYear(year);
                newContagio.setWeek(week);

                MalattiaContagiosa malattiaContagiosa = malattiaContagiosaDao.getItem(entry.getKey());
                if (malattiaContagiosa != null) {
                    newContagio.setMalattiaContagiosa(malattiaContagiosa);

                    for (JFXTextField input: entry.getValue()) {
                        int value = input.getText().isBlank() ? 0 : Integer.parseInt(input.getText());
                        switch (input.getId()) {
                            case "terapiaIntensiva":
                                newContagio.setNumeroTerapiaIntensiva(value);
                                break;
                            case "medicoBase":
                                newContagio.setNumeroMedicoBase(value);
                                break;
                        }
                    }

                    int maxComplications = 0;
                    if (formComplications.containsKey(entry.getKey())) {
                        for (JFXTextField complication: formComplications.get(entry.getKey())) {
                            // controllo se la malattia contagiosa ha la complicazione in oggetto
                            if (malattiaContagiosa.getComplications().contains(complication.getId())) {
                                int value = complication.getText().isBlank() ? 0 : Integer.parseInt(complication.getText());
                                newContagio.addComplication(complication.getId(), value);
                                if (value > maxComplications) {
                                    maxComplications = value;
                                }
                            }
                        }
                    }
                    if (maxComplications > (newContagio.getNumeroMedicoBase() + newContagio.getNumeroTerapiaIntensiva())) {
                        FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE INSERIMENTO", "Numero pazienti non valido!", null, event);
                        return;
                    } else {
                        contagioDao.addItem(newContagio);
                    }
                    // DEBUG
                    // System.out.println(newContagio);
                }
            }
            FXUtil.Alert(Alert.AlertType.INFORMATION, "SALVATAGGIO COMPLETATO", "Salvataggio completato con successo!", null, event);
        } catch (NumberFormatException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE SALVATAGGIO", "Errore durante il salvataggio!", null, event);
        } catch (InterruptedException | ExecutionException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE", "Errore durante l'esecuzione!", null, event);
            // DEBUG
            // e.printStackTrace();
        }
    }
}
