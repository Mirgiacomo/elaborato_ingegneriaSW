package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Regione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    public void showInsertRegione(ActionEvent event) {

    }
}
