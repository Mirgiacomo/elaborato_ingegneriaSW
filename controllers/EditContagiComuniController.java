package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.*;
import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.ContagioDaoImpl;
import elaborato_ingegneriaSW.dao.MalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.models.*;
import elaborato_ingegneriaSW.utils.AutoCompleteBox;
import elaborato_ingegneriaSW.utils.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class EditContagiComuniController implements Initializable {
    @FXML
    private JFXComboBox<Comune> comuneFilterComboBox;
    @FXML
    private DatePicker weekFilterDatePicker;
    @FXML
    private JFXButton loadContagiButton;
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
        MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();

        try {
            Set<MalattiaContagiosa> malattieContagiose = (Set<MalattiaContagiosa>) malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());
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

        // TODO: Ogni persona avrà solo certi comuni associati, non tutti
        try {
            Set<Comune> comuni = comuneDao.getAllItems(ComuneDaoImpl.getCollectionName());

            for (Comune comune: comuni) {
                comuneFilterComboBox.getItems().add(comune);
            }
            //new AutoCompleteBox(comuneFilterComboBox);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica eventuali contagi già inseriti per il comune e la settimana selezionati
     * @param actionEvent
     */
    @FXML
    public void loadContagiAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
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

        int year = date.getYear();
        int week = date.get(weekFields.weekOfWeekBasedYear());

        List<Contagio> result = contagioDao.getFilteredItems(comune, week, year);

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
    }

    @FXML
    private void saveAction(ActionEvent event) {
        MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();

        Comune comune = comuneFilterComboBox.getSelectionModel().getSelectedItem();
        LocalDate date = weekFilterDatePicker.getValue();
        date = date.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));

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
                        String value;
                        switch (input.getId()) {
                            case "terapiaIntensiva":
                                value = input.getText();
                                if (value != null && !value.equals("")) {
                                    newContagio.setNumeroTerapiaIntensiva(Integer.parseInt(value));
                                }
                                break;
                            case "medicoBase":
                                value = input.getText();
                                if (value != null && !value.equals("")) {
                                    newContagio.setNumeroMedicoBase(Integer.parseInt(value));
                                }
                                break;
                        }
                    }

                    int totalComplications = 0;
                    if (formComplications.containsKey(entry.getKey())) {
                        for (JFXTextField complication: formComplications.get(entry.getKey())) {
                            // controllo se la malattia contagiosa ha la complicazione in oggetto
                            if (malattiaContagiosa.getComplications().contains(complication.getId())) {
                                String value = complication.getText();
                                int intValue = (value != null && !value.equals("")) ? Integer.parseInt(value) : 0;
                                newContagio.addComplication(complication.getId(), intValue);
                                totalComplications += intValue;
                            }
                        }
                    }
                    if (totalComplications > (newContagio.getNumeroMedicoBase() + newContagio.getNumeroTerapiaIntensiva())) {
                        System.err.println("numero di pazienti non valido!");
                    } else {
                        contagioDao.addItem(newContagio);
                    }
                    System.out.println(newContagio);
                }
            }
        } catch (NumberFormatException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "VALORI ERRATA", "Errore durante l'inserimento di alcuni dati! Prova con il punto al posto della virgola", null, event);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
