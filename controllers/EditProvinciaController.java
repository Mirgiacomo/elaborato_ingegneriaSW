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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class EditProvinciaController extends EditController<Provincia> implements Initializable {

    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField superficieTextField;
    @FXML
    private JFXComboBox<Regione> regioneComboBox;
    @FXML
    public JFXButton saveButton;


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
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveAction(ActionEvent event) throws ExecutionException, InterruptedException {
        double superficie = -1.0;
        try {
            superficie = Double.parseDouble(superficieTextField.getText());
        } catch (NumberFormatException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "SUPERFICIE ERRATA", "Errore durante l'inserimento della superficie! Prova con il punto al posto della virgola", null, event);
            return;
        }
        if(nomeTextField.getText().isBlank() || superficieTextField.getText().isBlank() || superficie <= 0.0){
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE NEI DATI!", "Dati non validi!", null, event);
            return;
        }

        String nome = nomeTextField.getText();
        Regione regione = (Regione) regioneComboBox.getSelectionModel().getSelectedItem();

        Provincia provincia = new Provincia(nome, superficie, regione);
        System.out.println(provincia);
        if (provinciaDao.addItem(provincia) == null) {
            FXUtil.Alert(Alert.AlertType.ERROR, "SALVATAGGIO FALLITO", "Errore durante il salvataggio! Controlla i dati inseriti", null, event);
        } else {
            tableData.remove(model);
            tableData.add(provincia);
            //System.out.println("Provincia inserita correttamente!");

            // Chiudo la pagina di insert dopo l'avvenuto inserimento
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    void setModel(Provincia model) {
        this.model = model;
    }

    @Override
    void setTableData(ObservableList<Provincia> tableData) {
        this.tableData = tableData;
    }

    @Override
    public void populateForm() {
        nomeTextField.setText(model.getNome());
        superficieTextField.setText(String.valueOf(model.getSuperficie()));
        regioneComboBox.getSelectionModel().select(model.getRegione());
    }
}
