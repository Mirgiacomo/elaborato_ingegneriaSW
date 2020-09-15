package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.models.Utente;
import elaborato_ingegneriaSW.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mirgi
 */
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
        // TODO
    }

    @FXML
    private void insertProvinciaAction(ActionEvent event) throws ExecutionException, InterruptedException {
        String nomeProvincia = nomeTextField.getText();
        Double superficieProvincia = Double.parseDouble(superficieTextField.getText());

        /*RegioneDaoImpl regioneDao = new RegioneDaoImpl();
        List<Regione> regioni = regioneDao.getAllItems(RegioneDaoImpl.getCollectionName(), Regione.class);

        for (Regione regione: regioni) {
            //TODO: popolare combobox
        }*/
        RegioneDaoImpl regioneDao = new RegioneDaoImpl();
        //TODO: poplare combobox e associare il giusto nome
        HashMap filter = new HashMap();
        filter.put("nome", "Veneto");
        Regione regioneCollegata = (Regione)(regioneDao.getItemsByQuery(RegioneDaoImpl.getCollectionName(), filter, Regione.class).get(0));
        System.out.println("ok 3");

        if (nomeProvincia == null || superficieProvincia == null || regioneCollegata == null) {
            //AlertUtil.Alert(Alert.AlertType.ERROR, "INSERT FALLITO", "Dati non validi!", null);
            System.err.println("insert fallito! dati non validi");
        }
        System.out.println("ok 4");

        Provincia newProvincia = new Provincia(nomeProvincia, superficieProvincia, regioneCollegata);
        System.out.println("ok 5");
        if (provinciaDao.addItem(newProvincia) == null) {
            System.err.println("insert fallito");
        } else {
            System.out.println("insert provincia " + newProvincia.getNome() + " ok!");
        }
    }

}
