package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.utils.EditButtonCell;
import elaborato_ingegneriaSW.utils.Export;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ViewProvinceController extends ViewController<Provincia> implements Initializable {
    private final ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

    @FXML
    private TableView<Provincia> tableProvince;
    @FXML
    public TableColumn<Provincia, String> actionCol;
    @FXML
    public TableColumn<Provincia, String> nomeCol;
    @FXML
    public TableColumn<Provincia, Double> superficieCol;
    @FXML
    public TableColumn<Provincia, String> regioneCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {
            Platform.runLater(() -> {
                try {
                    Set<Provincia> province = provinciaDao.getAllItems(ProvinciaDaoImpl.getCollectionName());
                    setTableData(province);

                    Callback<TableColumn<Provincia, String>, TableCell<Provincia, String>> cellFactory = param -> new EditButtonCell<>(tableProvince, ViewProvinceController.this, "EditProvincia.fxml");

                    actionCol.setCellFactory(cellFactory);
                    actionCol.prefWidthProperty().bind(tableProvince.widthProperty().multiply(0.055));
                    actionCol.setResizable(false);
                    nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
                    superficieCol.setCellValueFactory(new PropertyValueFactory<>("superficie"));
                    regioneCol.setCellValueFactory(new PropertyValueFactory<>("regione"));

                    tableProvince.setItems(tableData);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });

            return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    @Override
    void setTableData(Set<Provincia> data) {
        this.tableData = FXCollections.observableArrayList(data);
    }

    public void showInsertProvincia(ActionEvent event) throws IOException {
        showInsertView(event, "EditProvincia.fxml");
    }

    public void exportProvincia(ActionEvent event) throws Exception {
        Export.exportData(provinciaDao.getAllItems(ProvinciaDaoImpl.getCollectionName()));
    }
}
