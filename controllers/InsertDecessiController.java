package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.dao.DecessoDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Decesso;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.models.Territorio;
import elaborato_ingegneriaSW.utils.AutoCompleteBox;
import elaborato_ingegneriaSW.utils.FXUtil;
import elaborato_ingegneriaSW.utils.ShowView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;


public class InsertDecessiController implements Initializable {
    @FXML
    private JFXComboBox provinciaFilterComboBox;
    @FXML
    private JFXComboBox annoFilterComboBox;
    @FXML
    private JFXButton ricercaFilterButton;
    @FXML
    private JFXButton insertDecessiButton;
    @FXML
    private JFXTextField incidenteStradaleNumeroTextField;
    @FXML
    private JFXTextField malattieTumoraliNumeroTextField;
    @FXML
    private JFXTextField malattieCardiovascolariNumeroTextField;
    @FXML
    private JFXTextField malattieContagioseNumeroTextField;

    private final ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Set<Provincia> province = provinciaDao.getAllItems(ProvinciaDaoImpl.getCollectionName());

            for (Provincia provincia: province) {
                provinciaFilterComboBox.getItems().add(provincia);
            }
            new AutoCompleteBox(provinciaFilterComboBox);

            // TODO: lasciarlo statico oppure caricare dinamicamente gli anni prendendoli dalla tabella decessi
            annoFilterComboBox.getItems().add("2019");
            annoFilterComboBox.getItems().add("2020");
            annoFilterComboBox.getItems().add("2021");
            new AutoCompleteBox(annoFilterComboBox);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showInsertDecessi(ActionEvent event) throws IOException {
        /*
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader("InsertDecessi.fxml");

        Parent view = loader.load();
        Scene scene = new Scene(view);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );

        stage.setScene(scene);
        stage.showAndWait();
        */
    }

    @FXML
    private void searchDecessiAction(ActionEvent event) throws ExecutionException, InterruptedException {
        /*
        DecessoDaoImpl decessoDao = new DecessoDaoImpl();
        Provincia prov = new Provincia();
        Set<Provincia> province = provinciaDao.getItem();

        for (Provincia provincia: province) {
            provinciaFilterComboBox.getItems().add(provincia);
        }
        Provincia provincia = provinciaDao.getItem((Provincia)provinciaFilterComboBox.getValue());
        System.out.println(provincia.getNome());
        Set<Decesso> decessi = (Set<Decesso>) decessoDao.getItem(provincia);
        */
    }

    @FXML
    private void insertDecessiAction(ActionEvent event) {

    }
}
