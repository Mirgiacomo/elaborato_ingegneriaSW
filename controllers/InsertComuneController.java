package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Comune;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.models.Territorio;
import elaborato_ingegneriaSW.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class InsertComuneController implements Initializable {
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
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Territorio territorio: Territorio.values()) {
            territorioComboBox.getItems().add(territorio);
        }
    }

    @FXML
    private void insertComuneAction(ActionEvent event) throws ExecutionException, InterruptedException {
        String codiceISTAT = codiceISTATTextField.getText();
        String nome = nomeTextField.getText();
        String dataIstituzione = dataIstituzioneDataPicker.getValue().toString();
        Double superficie = Double.parseDouble(superficieTextField.getText());
        Territorio territorio = (Territorio) territorioComboBox.getValue();
        Boolean fronteMare = fronteMareCheckBox.isSelected();
        Provincia provincia = (Provincia) provinciaComboBox.getValue();

        if (codiceISTAT == null || nome == null || dataIstituzione == null || superficie == null || territorio == null || fronteMare == null || provincia == null) {
            AlertUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Dati non validi!", null, event);
        }

        Comune newComune = new Comune(codiceISTAT, nome, dataIstituzione, superficie, territorio, fronteMare, provincia);
        if (comuneDao.addItem(newComune) == null) {
            AlertUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Errore durante l'inserimento!", null, event);
        } else {
            System.out.println("ok");
        }
    }
    
}
