package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.utils.FXUtil;
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
        String nome = nomeTextField.getText();
        String capoluogo = capoluogoTextField.getText();
        Double superficie = Double.parseDouble(superficieTextField.getText());

        if (nome == null || capoluogo == null || superficie == null) {
            FXUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Dati non validi!", null, event);
        }

        Regione newRegione = new Regione(nome, capoluogo, superficie);
        if (regioneDao.addItem(newRegione) == null) {
            FXUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Errore durante l'inserimento!", null, event);
        } else {
            System.out.println("ok");
        }
    }
    
}
