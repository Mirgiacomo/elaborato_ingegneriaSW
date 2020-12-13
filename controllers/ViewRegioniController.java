package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.utils.ShowView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ViewRegioniController implements Initializable {
    private final RegioneDaoImpl regioneDao = new RegioneDaoImpl();
    @FXML
    public TableColumn<Regione, String> nomeCol;
    @FXML
    public TableColumn<Regione, String> capoluogoCol;
    @FXML
    public TableColumn<Regione, Double> superficieCol;
    @FXML
    private TableView<Regione> tableRegioni;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    try {
                        Set<Regione> regioni = regioneDao.getAllItems(RegioneDaoImpl.getCollectionName());
                        ObservableList<Regione> data = FXCollections.observableArrayList(regioni);

                        TableColumn actionCol = new TableColumn("ACTION");

                        Callback<TableColumn<Regione, String>, TableCell<Regione, String>> cellFactory
                                = //
                                new Callback<TableColumn<Regione, String>, TableCell<Regione, String>>() {
                                    @Override
                                    public TableCell call(final TableColumn<Regione, String> param) {
                                        final TableCell<Regione, String> cell = new TableCell<Regione, String>() {

                                            JFXButton btn = new JFXButton("Modifica");

                                            @Override
                                            public void updateItem(String item, boolean empty) {

                                                // Setto il CSS al bottone della tabella
                                                btn.setId("button-table");
                                                btn.setTextFill(Paint.valueOf("white"));
                                                super.updateItem(item, empty);
                                                if (empty) {
                                                    setGraphic(null);
                                                    setText(null);
                                                } else {
                                                    btn.setOnAction(event -> {

                                                        // Apro la pagina di insert ma in modalità edit
                                                        ShowView showView = new ShowView();
                                                        FXMLLoader loader = showView.getLoader("InsertRegione.fxml");

                                                        Parent view = null;
                                                        try {
                                                            view = loader.load();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        Scene scene = new Scene(view);

                                                        Stage stage = new Stage();
                                                        stage.initModality(Modality.WINDOW_MODAL);
                                                        stage.initOwner(
                                                                ((Node)event.getSource()).getScene().getWindow() );

                                                        stage.setScene(scene);
                                                        stage.showAndWait();
                                                    });
                                                    setGraphic(btn);
                                                    setText(null);
                                                }
                                            }
                                        };
                                        return cell;
                                    }
                                };
                        actionCol.setCellFactory(cellFactory);
                        tableRegioni.getColumns().addAll(actionCol);

                        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
                        superficieCol.setCellValueFactory(new PropertyValueFactory<>("superficie"));
                        capoluogoCol.setCellValueFactory(new PropertyValueFactory<>("capoluogo"));

                        tableRegioni.setItems(data);
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

    public void showInsertRegione(ActionEvent event) throws IOException {
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader("InsertRegione.fxml");

        Parent view = loader.load();
        Scene scene = new Scene(view);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());

        stage.setScene(scene);
        stage.showAndWait();
    }
}
