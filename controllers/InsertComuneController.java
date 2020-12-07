package elaborato_ingegneriaSW.controllers;

import java.net.URL;
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
import elaborato_ingegneriaSW.models.Comune;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Territorio;
import elaborato_ingegneriaSW.utils.AutoCompleteBox;
import elaborato_ingegneriaSW.utils.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class InsertComuneController extends AbstractController implements Initializable {
    @FXML
    private JFXTextField codiceISTATTextField;
    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXDatePicker dataIstituzioneDataPicker;
    @FXML
    private JFXTextField superficieTextField;
    @FXML
    private JFXComboBox territorioComboBox;
    @FXML
    private JFXCheckBox fronteMareCheckBox;
    @FXML
    private JFXComboBox provinciaComboBox;

    private final ComuneDaoImpl comuneDao = new ComuneDaoImpl();
    private final ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Set<Provincia> province = provinciaDao.getAllItems(ProvinciaDaoImpl.getCollectionName());

            for (Provincia provincia: province) {
                provinciaComboBox.getItems().add(provincia);
            }
            new AutoCompleteBox(provinciaComboBox);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Territorio territorio: Territorio.values()) {
            territorioComboBox.getItems().add(territorio);
        }
        new AutoCompleteBox(territorioComboBox);
    }

    @FXML
    private void insertComuneAction(ActionEvent event) throws ExecutionException, InterruptedException {
        Double superficie = null;
        try {
            superficie = Double.parseDouble(superficieTextField.getText());
        } catch (NumberFormatException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "SUPERFICIE ERRATA", "Errore durante l'inserimento della superficie! Prova con il punto al posto della virgola", null, event);
            return;
        }
        // Controllo se il codice ISTAT è valido
        Pattern pattern = Pattern.compile("^[0-9]{6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(codiceISTATTextField.getText());
        boolean matchFound = matcher.find();
        if(codiceISTATTextField.getText().isBlank() || nomeTextField.getText().isBlank() ||  superficie <= 0 || superficieTextField.getText().isBlank() || !(matchFound)){
            FXUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Dati non validi!", null, event);
            return;
        }
        String codiceISTAT = codiceISTATTextField.getText();
        String nome = nomeTextField.getText();
        String dataIstituzione = dataIstituzioneDataPicker.getValue().toString();
        Territorio territorio = (Territorio) FXUtil.getComboBoxItemFromString(territorioComboBox);
        Boolean fronteMare = fronteMareCheckBox.isSelected();
        Provincia provincia = (Provincia) FXUtil.getComboBoxItemFromString(provinciaComboBox);

        Comune newComune = new Comune(codiceISTAT, nome, dataIstituzione, superficie, territorio, fronteMare, provincia);
        if (comuneDao.addItem(newComune) == null) {
            FXUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Errore durante l'inserimento!", null, event);
        } else {
            System.out.println("Comune inserito correttamente!");
        }
    }
    
}
