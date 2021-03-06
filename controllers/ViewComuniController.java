package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.models.Comune;
import elaborato_ingegneriaSW.utils.EditButtonCell;
import elaborato_ingegneriaSW.utils.Export;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

public class ViewComuniController extends ViewController<Comune> implements Initializable {
    private final ComuneDaoImpl comuneDao = new ComuneDaoImpl();

    @FXML
    private TableView<Comune> tableComuni;
    @FXML
    public TableColumn<Comune, String> actionCol;
    @FXML
    public TableColumn<Comune, String> codiceISTATCol;
    @FXML
    public TableColumn<Comune, String> nomeCol;
    @FXML
    public TableColumn<Comune, String> dataIstituzioneCol;
    @FXML
    public TableColumn<Comune, Double> superficieCol;
    @FXML
    public TableColumn<Comune, String> territorioCol;
    @FXML
    public TableColumn<Comune, String> fronteMareCol;
    @FXML
    public TableColumn<Comune, String> provinciaCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {
            Platform.runLater(() -> {
                try {
                    Set<Comune> comuni = comuneDao.getAllItems(ComuneDaoImpl.getCollectionName());
                    setTableData(comuni);

                    Callback<TableColumn<Comune, String>, TableCell<Comune, String>> cellFactory = param -> new EditButtonCell<>(tableComuni, ViewComuniController.this, "EditComune.fxml");

                    actionCol.setCellFactory(cellFactory);
                    actionCol.prefWidthProperty().bind(tableComuni.widthProperty().multiply(0.075));
                    actionCol.setResizable(false);
                    codiceISTATCol.setCellValueFactory(new PropertyValueFactory<>("codiceISTAT"));
                    nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
                    dataIstituzioneCol.setCellValueFactory(new PropertyValueFactory<>("dataIstituzione"));
                    superficieCol.setCellValueFactory(new PropertyValueFactory<>("superficie"));
                    territorioCol.setCellValueFactory(new PropertyValueFactory<>("territorio"));
                    fronteMareCol.setCellValueFactory(new PropertyValueFactory<>("fronteMare"));
                    provinciaCol.setCellValueFactory(new PropertyValueFactory<>("provincia"));

                    tableComuni.setItems(tableData);

                    tableData.addListener((ListChangeListener<Comune>) change -> tableComuni.refresh());
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
    void setTableData(Set<Comune> data) {
        this.tableData = FXCollections.observableArrayList(data);
    }

    public void showInsertComune(ActionEvent event) throws IOException {
        showInsertView(event, "EditComune.fxml");
    }

    public void exportComune(ActionEvent event) throws Exception {
        Export.exportData(comuneDao.getAllItems(ComuneDaoImpl.getCollectionName()), null);
    }
}
