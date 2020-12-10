package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.utils.ShowView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ViewProvinceController implements Initializable {
    @FXML
    public TableColumn<Provincia, String> nomeCol;
    @FXML
    public TableColumn<Provincia, Double> superficieCol;
    @FXML
    public TableColumn<Provincia, String> regioneCol;
    @FXML
    private TableView<Provincia> tableProvince;

    private final ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Set<Provincia> province = provinciaDao.getAllItems(ProvinciaDaoImpl.getCollectionName());
            ObservableList<Provincia> data = FXCollections.observableArrayList(province);

            nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
            superficieCol.setCellValueFactory(new PropertyValueFactory<>("superficie"));
            regioneCol.setCellValueFactory(new PropertyValueFactory<>("regione"));

            tableProvince.setItems(data);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showInsertProvincia(ActionEvent event) throws IOException {
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader("InsertProvincia.fxml");

        Parent view = loader.load();
        Scene scene = new Scene(view);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );

        stage.setScene(scene);
        stage.showAndWait();
    }
}
