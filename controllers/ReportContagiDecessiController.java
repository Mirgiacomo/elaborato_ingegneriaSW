package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.Provincia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ReportContagiDecessiController implements Initializable {
    @FXML
    private JFXButton searchButton;
    @FXML
    private CheckComboBox<Provincia> provinceCheckComboBox;
    @FXML
    private VBox contentBox;

    ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Set<Provincia> province = provinciaDao.getAllItems(ProvinciaDaoImpl.getCollectionName());

            for (Provincia provincia: province) {
                provinceCheckComboBox.getItems().add(provincia);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void searchAction(ActionEvent actionEvent) {
    }
}
