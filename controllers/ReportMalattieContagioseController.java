package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.Comune;
import elaborato_ingegneriaSW.models.Provincia;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ReportMalattieContagioseController implements Initializable {
    @FXML
    private JFXButton searchButton;
    @FXML
    private CheckComboBox<Provincia> provinceCheckComboBox;
    @FXML
    private VBox contentBox;

    ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();
    ComuneDaoImpl comuneDao = new ComuneDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Set<Provincia> province = provinciaDao.getAllItems(ProvinciaDaoImpl.getCollectionName());

            for (Provincia provincia : province) {
                provinceCheckComboBox.getItems().add(provincia);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void searchAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        ObservableList<Provincia> province = provinceCheckComboBox.getCheckModel().getCheckedItems();
        if (province != null && !province.isEmpty()) {
            for (Provincia provincia : province) {
                System.out.println("Provincia: " + provincia);
                Set<Comune> comuni = comuneDao.getComuniByProvincia(provincia);
                System.out.println(comuni);
                System.out.println("---------------------");
            }
        }
    }
}
