package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.dao.UtenteDaoImpl;
import elaborato_ingegneriaSW.models.AbstractTableModel;
import elaborato_ingegneriaSW.models.Utente;
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

public class ViewUtentiController implements Initializable, ViewController {
    private final UtenteDaoImpl utenteDao = new UtenteDaoImpl();
    private final RegioneDaoImpl regioneDao = new RegioneDaoImpl();

    @FXML
    private TableView<Utente> tableUtenti;
    @FXML
    public TableColumn<AbstractTableModel, String> actionCol;
    @FXML
    public TableColumn<Utente, String> nomeCol;
    @FXML
    public TableColumn<Utente, String> cognomeCol;
    @FXML
    public TableColumn<Utente, String> usernameCol;
    @FXML
    public TableColumn<Utente, String> passwordCol;
    @FXML
    public TableColumn<Utente, String> cfCol;
    @FXML
    public TableColumn<Utente, String> ruoloCol;
    @FXML
    public TableColumn<Utente, String> comuniAssociatiCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {
            Platform.runLater(() -> {
                try {
                    Set<Utente> utenti = utenteDao.getAllItems(UtenteDaoImpl.getCollectionName());
                    ObservableList<Utente> data = FXCollections.observableArrayList(utenti);

                    Callback<TableColumn<AbstractTableModel, String>, TableCell<AbstractTableModel, String>> cellFactory = param -> new EditButtonCell(tableUtenti, ViewUtentiController.this, "EditUtente");

                    actionCol.setCellFactory(cellFactory);
                    actionCol.prefWidthProperty().bind(tableUtenti.widthProperty().multiply(0.055));
                    actionCol.setResizable(false);
                    nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
                    cognomeCol.setCellValueFactory(new PropertyValueFactory<>("cognome"));
                    usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
                    // passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
                    cfCol.setCellValueFactory(new PropertyValueFactory<>("cf"));
                    ruoloCol.setCellValueFactory(new PropertyValueFactory<>("ruolo"));
                    comuniAssociatiCol.setCellValueFactory(new PropertyValueFactory<>("comuniAssociati"));

                    tableUtenti.setItems(data);
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

    public void showInsertUtente(ActionEvent event) throws IOException {
        showInsertView(event, "EditUtente");
    }

    public void exportUtente(ActionEvent event) throws Exception {
        Export.exportData(utenteDao.getAllItems(utenteDao.getCollectionName()));
    }
}
