package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.MalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.*;
import elaborato_ingegneriaSW.utils.AutoCompleteBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
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
            new AutoCompleteBox(provinciaFilterComboBox);

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            yearFilterComboBox.getItems().add(currentYear - 1);
            yearFilterComboBox.getItems().add(currentYear);
            new AutoCompleteBox(yearFilterComboBox);

            Set<MalattiaContagiosa> malattieContagiose = (Set<MalattiaContagiosa>) malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());
            decessiGridPane.getStyleClass().add("grid-pane");
            int row = 1;

            for (CausaDecesso causaDecesso: CausaDecesso.values()) {
                Label label = new Label(causaDecesso.getNome().toUpperCase());
                JFXTextField number = new JFXTextField();
                number.setId("number");

                Set<JFXTextField> inputs = new HashSet<>();
                inputs.add(number);

                form.put(causaDecesso.getNome(), inputs);
                decessiGridPane.addRow(row++, label, number);
            }
            for (MalattiaContagiosa m: malattieContagiose) {
                Label label = new Label(m.getNome().toUpperCase());
                JFXTextField number = new JFXTextField();
                number.setId("number");

                Set<JFXTextField> inputs = new HashSet<>();
                inputs.add(number);

                form.put(m.getNome(), inputs);
                decessiGridPane.addRow(row++, label, number);
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadDecessiAction(ActionEvent actionEvent) {
    }

    @FXML
    public void saveAction(ActionEvent actionEvent) {
    }
}
