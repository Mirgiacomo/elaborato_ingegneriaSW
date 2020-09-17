package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class InsertProvinciaController implements Initializable {

    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField superficieTextField;
    @FXML
    private JFXComboBox regioneComboBox;

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
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertProvinciaAction(ActionEvent event) throws ExecutionException, InterruptedException {
        String nome = nomeTextField.getText();
        Double superficie = Double.parseDouble(superficieTextField.getText());
        Regione regione = (Regione) regioneComboBox.getValue();

        if (nome == null || superficie == null || regione == null) {
            AlertUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Dati non validi!", null, event);
        }

        Provincia newProvincia = new Provincia(nome, superficie, regione);
        if (provinciaDao.addItem(newProvincia) == null) {
            AlertUtil.Alert(Alert.AlertType.ERROR, "INSERIMENTO FALLITO", "Errore durante l'inserimento!", null, event);
        } else {
            System.out.println("ok");
        }
    }

}
