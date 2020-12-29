package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.utils.FXUtil;
import elaborato_ingegneriaSW.utils.AutoCompleteBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditProvinciaController implements Initializable, EditView<Provincia> {

    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField superficieTextField;
    @FXML
    private JFXComboBox regioneComboBox;
    @FXML
    public JFXButton insertProvinciaButton;


    private final RegioneDaoImpl regioneDao = new RegioneDaoImpl();
    private final ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Set<Regione> regioni = regioneDao.getAllItems(RegioneDaoImpl.getCollectionName());

            for (Regione regione: regioni) {
                regioneComboBox.getItems().add(regione);
            }
            new AutoCompleteBox(regioneComboBox);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertProvinciaAction(ActionEvent event) throws ExecutionException, InterruptedException {
        Double superficie = null;
        try {
            superficie = Double.parseDouble(superficieTextField.getText());
        } catch (NumberFormatException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "SUPERFICIE ERRATA", "Errore durante l'inserimento della superficie! Prova con il punto al posto della virgola", null, event);
            return;
        }
        if(nomeTextField.getText().isBlank() || superficieTextField.getText().isBlank() || superficie <= 0){
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE NEI DATI!", "Dati non validi!", null, event);
            return;
        }

        String nome = nomeTextField.getText();
        Regione regione = (Regione) FXUtil.getComboBoxItemFromString(regioneComboBox);
      
        Provincia newProvincia = new Provincia(nome, superficie, regione);
        if (provinciaDao.addItem(newProvincia) == null) {
            FXUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Errore durante l'inserimento!", null, event);
        } else {
            System.out.println("Provincia inserita correttamente!");

            // Chiudo la pagina di insert dopo l'avvenuto inserimento
            Stage stage = (Stage) insertProvinciaButton.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void populateForm(Provincia model) {
        
    }
}
