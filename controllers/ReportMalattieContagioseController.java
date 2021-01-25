package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.dao.ContagioDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.Contagio;
import elaborato_ingegneriaSW.models.Provincia;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ReportMalattieContagioseController implements Initializable {
    @FXML
    private CheckComboBox<Provincia> provinceCheckComboBox;
    @FXML
    public SearchableComboBox<Integer> yearSearchableComboBox;
    @FXML
    private JFXButton searchButton;
    @FXML
    private VBox contentBox;

    ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();
    ContagioDaoImpl contagioDao = new ContagioDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Set<Provincia> province = provinciaDao.getAllItems(ProvinciaDaoImpl.getCollectionName());

            for (Provincia provincia : province) {
                provinceCheckComboBox.getItems().add(provincia);
            }

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            yearSearchableComboBox.getItems().add(currentYear - 1);
            yearSearchableComboBox.getItems().add(currentYear);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void searchAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        ObservableList<Provincia> province = provinceCheckComboBox.getCheckModel().getCheckedItems();
        int year = yearSearchableComboBox.getSelectionModel().getSelectedItem();

        if (province != null && !province.isEmpty()) {
            for (Provincia provincia : province) {
                Set<Contagio> contagi = contagioDao.getFilteredItems(provincia, year);
                // TODO: caricamento decessi
                // TODO: creazione interfaccia grafica con una tabella e un grafico per ogni provincia
            }
        }
    }
}
