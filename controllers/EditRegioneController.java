package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.utils.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class EditRegioneController implements Initializable, EditController<Regione> {
    private final RegioneDaoImpl regioneDao = new RegioneDaoImpl();
    @FXML
    public JFXButton saveButton;
    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField capoluogoTextField;
    @FXML
    private JFXTextField superficieTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    @Override
    public void saveAction(ActionEvent event) throws ExecutionException, InterruptedException {
        Double superficie = null;
        try {
            superficie = Double.parseDouble(superficieTextField.getText());
        } catch (NumberFormatException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "SUPERFICIE ERRATA", "Errore durante l'inserimento della superficie! Prova con il punto al posto della virgola", null, event);
            return;
        }
        if (nomeTextField.getText().isBlank() || capoluogoTextField.getText().isBlank() || superficieTextField.getText().isBlank() || superficie <= 0) {
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE NEI DATI!", "Dati non validi!", null, event);
            return;
        }
        String nome = nomeTextField.getText();
        String capoluogo = capoluogoTextField.getText();

        Regione newRegione = new Regione(nome, capoluogo, superficie);

        if (regioneDao.addItem(newRegione) == null) {
            FXUtil.Alert(Alert.AlertType.ERROR, "SALVATAGGIO FALLITO", "Errore durante l'inserimento!", null, event);
        } else {
            System.out.println("Regione salvata correttamente.");

            // Chiudo la pagina di insert dopo l'avvenuto inserimento
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void populateForm(Regione model) {
        nomeTextField.setText(model.getNome());
        capoluogoTextField.setText(model.getCapoluogo());
        superficieTextField.setText(String.valueOf(model.getSuperficie()));
    }
}
