package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.utils.ShowView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ViewRegioniController implements Initializable {
    @FXML
    public TableColumn<Regione, String> nomeCol;
    @FXML
    public TableColumn<Regione, String> capoluogoCol;
    @FXML
    public TableColumn<Regione, Double> superficieCol;
    @FXML
    private TableView<Regione> tableRegioni;

    private final RegioneDaoImpl regioneDao = new RegioneDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Set<Regione> regioni = regioneDao.getAllItems(RegioneDaoImpl.getCollectionName());
            ObservableList<Regione> data = FXCollections.observableArrayList(regioni);

            nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
            superficieCol.setCellValueFactory(new PropertyValueFactory<>("superficie"));
            capoluogoCol.setCellValueFactory(new PropertyValueFactory<>("capoluogo"));

            tableRegioni.setItems(data);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showInsertRegione(ActionEvent event) throws IOException {
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader("InsertRegione.fxml");

        Parent view = loader.load();
        Scene scene = new Scene(view);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }
}
