package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.DecessoDaoImpl;
import elaborato_ingegneriaSW.dao.DecessoMalattiaContagiosaDaoImpl;
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
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class EditDecessiProvinceController implements Initializable {
    public GridPane contagiGridPane;
    @FXML
    private JFXComboBox<Provincia> provinciaFilterComboBox;
    @FXML
    private JFXComboBox<Integer> yearFilterComboBox;
    @FXML
    private JFXButton loadDecessiButton;
    @FXML
    private GridPane decessiGridPane;
    @FXML
    private JFXButton saveButton;

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
            //new AutoCompleteBox(provinciaFilterComboBox);

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            yearFilterComboBox.getItems().add(currentYear - 1);
            yearFilterComboBox.getItems().add(currentYear);
            //new AutoCompleteBox(yearFilterComboBox);

            Set<MalattiaContagiosa> malattieContagiose = (Set<MalattiaContagiosa>) malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());
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
    }

    @FXML
    public void loadDecessiAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        for (Map.Entry<String, Set<JFXTextField>> entry: form.entrySet()) {
            for (JFXTextField input: entry.getValue()) {
                input.setText("");
            }
        }

        Provincia provincia = provinciaFilterComboBox.getSelectionModel().getSelectedItem();
        int year = yearFilterComboBox.getSelectionModel().getSelectedItem();

        List<Decesso> resultDecessi = decessoDao.getFilteredItems(provincia, year);

        if (resultDecessi != null && !resultDecessi.isEmpty()) {
            for (Decesso decesso: resultDecessi) {
                Set<JFXTextField> inputs = null;
                if (form.containsKey(decesso.getCausaDecesso().getNome())) {
                    inputs = form.get(decesso.getCausaDecesso().getNome());
                }

                if (inputs != null && !inputs.isEmpty()) {
                    for (JFXTextField input : inputs) {
                        input.setText(String.valueOf(decesso.getNumeroMorti()));
                    }
                }
            }
        }

        List<DecessoMalattiaContagiosa> resultDecessiMalattiaContagiosa = decessoMalattiaContagiosaDao.getFilteredItems(provincia, year);

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
    }

    @FXML
    public void saveAction(ActionEvent event) {
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
                    System.out.println(entry.getKey());

                    DecessoMalattiaContagiosa newDecessoMalattiaContagiosa = new DecessoMalattiaContagiosa();
                    newDecessoMalattiaContagiosa.setYear(year);
                    newDecessoMalattiaContagiosa.setProvincia(provincia);
                    newDecessoMalattiaContagiosa.setCausaDecesso(CausaDecesso.MALATTIA_CONTAGIOSA);
                    newDecessoMalattiaContagiosa.setMalattiaContagiosa(malattiaContagiosa);

                    for (JFXTextField input: entry.getValue()) {
                        String value = input.getText();
                        newDecessoMalattiaContagiosa.setNumeroMorti(Integer.parseInt(value));
                    }
                    decessoMalattiaContagiosaDao.addItem(newDecessoMalattiaContagiosa);
                    System.out.println(newDecessoMalattiaContagiosa);
                } else {
                    Decesso newDecesso = new Decesso();

                    newDecesso.setYear(year);
                    newDecesso.setProvincia(provincia);
                    newDecesso.setCausaDecesso(CausaDecesso.valueOf(entry.getKey()));

                    for (JFXTextField input: entry.getValue()) {
                        String value = input.getText();
                        newDecesso.setNumeroMorti(Integer.parseInt(value));
                    }

                    decessoDao.addItem(newDecesso);
                    System.out.println(newDecesso);
                }
            }
        } catch (NumberFormatException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "VALORI ERRATI", "Errore durante l'inserimento di alcuni dati! Prova con il punto al posto della virgola", null, event);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
