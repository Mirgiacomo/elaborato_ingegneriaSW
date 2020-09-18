package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class InsertRegioneController implements Initializable {
    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField capoluogoTextField;
    @FXML
    private JFXTextField superficieTextField;

    private final RegioneDaoImpl regioneDao = new RegioneDaoImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void insertRegioneAction(ActionEvent event) throws ExecutionException, InterruptedException {
        Double superficie = null;
        try {
            superficie = Double.parseDouble(superficieTextField.getText());
        } catch (NumberFormatException e) {
            AlertUtil.Alert(Alert.AlertType.ERROR, "SUPERFICIE ERRATA", "Errore durante l'inserimento della superficie! Prova con il punto al posto della virgola", null, event);
            return;
        }
        if(nomeTextField.getText().isBlank() || capoluogoTextField.getText().isBlank() || superficieTextField.getText().isBlank() || superficie <= 0){
            AlertUtil.Alert(Alert.AlertType.ERROR, "ERRORE NEI DATI!", "Dati non validi!", null, event);
            return;
        }
        String nome = nomeTextField.getText();
        String capoluogo = capoluogoTextField.getText();


        Regione newRegione = new Regione(nome, capoluogo, superficie);
        if (regioneDao.addItem(newRegione) == null) {
            AlertUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Errore durante l'inserimento!", null, event);
        } else {
            System.out.println("Regione inserita correttamente.");
        }
    }
    
}
