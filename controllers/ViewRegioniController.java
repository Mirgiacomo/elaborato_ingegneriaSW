package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.AbstractTableModel;
import elaborato_ingegneriaSW.models.Regione;
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

public class ViewRegioniController implements Initializable, ViewController {
    private final RegioneDaoImpl regioneDao = new RegioneDaoImpl();

    @FXML
    private TableView<Regione> tableRegioni;
    @FXML
    public TableColumn<AbstractTableModel, String> actionCol;
    @FXML
    public TableColumn<Regione, String> nomeCol;
    @FXML
    public TableColumn<Regione, String> capoluogoCol;
    @FXML
    public TableColumn<Regione, Double> superficieCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {
            Platform.runLater(() -> {
                try {
                    Set<Regione> regioni = regioneDao.getAllItems(RegioneDaoImpl.getCollectionName());
                    ObservableList<Regione> data = FXCollections.observableArrayList(regioni);
                    Callback<TableColumn<AbstractTableModel, String>, TableCell<AbstractTableModel, String>> cellFactory = param -> new EditButtonCell(tableRegioni, ViewRegioniController.this, "EditRegione");

                    actionCol.setCellFactory(cellFactory);
                    actionCol.prefWidthProperty().bind(tableRegioni.widthProperty().multiply(0.055));
                    actionCol.setResizable(false);
                    nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
                    superficieCol.setCellValueFactory(new PropertyValueFactory<>("superficie"));
                    capoluogoCol.setCellValueFactory(new PropertyValueFactory<>("capoluogo"));

                    tableRegioni.setItems(data);


                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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

    public void showInsertRegione(ActionEvent event) throws IOException {
        showInsertView(event, "EditRegione");
    }

    public void exportRegione(ActionEvent event) throws Exception {
        Export.exportData(regioneDao.getAllItems(regioneDao.getCollectionName()));
    }
}
