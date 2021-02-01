package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.dao.*;
import elaborato_ingegneriaSW.models.*;
import elaborato_ingegneriaSW.utils.Export;
import elaborato_ingegneriaSW.utils.FXUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ReportDecessiNazioneController implements Initializable {
    @FXML
    private SearchableComboBox<Integer> yearSearchableComboBox;
    @FXML
    private JFXButton searchButton;
    @FXML
    private VBox contentBox;

    DecessoMalattiaContagiosaDaoImpl decessoMalattiaContagiosaDao = new DecessoMalattiaContagiosaDaoImpl();
    DecessoDaoImpl decessoDao = new DecessoDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearSearchableComboBox.getItems().add(currentYear - 1);
        yearSearchableComboBox.getItems().add(currentYear);
    }

    public void searchAction(ActionEvent actionEvent) {
        if (yearSearchableComboBox.getSelectionModel().isEmpty()) {
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE FILTRO!", "Anno inserito nel filtro non valido!", null, actionEvent);
            return;
        }
        int year = yearSearchableComboBox.getSelectionModel().getSelectedItem();
        contentBox.getChildren().clear();

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    try {
                        HBox box = new HBox();
                        VBox.setVgrow(box, Priority.ALWAYS);

                        box.setId("Box");
                        box.getChildren().clear();

                        Text title = new Text();
                        title.setFont(Font.font("Open Sans Semibold", FontWeight.SEMI_BOLD, 25));
                        title.setId("title");
                        title.setText("ITALIA");

                        JFXButton button = new JFXButton();
                        button.setText("EXPORT");
                        button.setTextFill(Color.WHITE);
                        button.setStyle("-fx-background-color: #eda324");

                        JFXButton buttonImg = new JFXButton();
                        buttonImg.setText("EXPORT GRAFICO");
                        buttonImg.setTextFill(Color.WHITE);
                        buttonImg.setStyle("-fx-background-color: black; " +
                                "-fx-font-size: 10px;" +
                                "-fx-border-insets: 5px;" +
                                "-fx-background-insets: 5px;");

                        TableView<Map> table = new TableView<>();
                        HBox.setHgrow(table, Priority.SOMETIMES);

                        TableColumn<Map, String> causaCol = new TableColumn<>();
                        causaCol.setText("causa");
                        causaCol.setId("causaCol");

                        TableColumn<Map, Integer> decessiCol = new TableColumn<>();
                        decessiCol.setText("decessi");
                        decessiCol.setId("decessiCol");

                        table.getColumns().clear();
                        table.getColumns().add(causaCol);
                        table.getColumns().add(decessiCol);

                        PieChart pieChart = new PieChart();
                        pieChart.setId("pieNazione");
                        pieChart.setLabelLineLength(10);
                        pieChart.setLegendSide(Side.RIGHT);
                        HBox.setHgrow(pieChart, Priority.SOMETIMES);

                        box.getChildren().add(table);
                        box.getChildren().add(pieChart);

                        Separator separator = new Separator();
                        separator.setId("separatorNazione");

                        contentBox.getChildren().add(title);
                        contentBox.getChildren().add(button);
                        contentBox.getChildren().add(box);

                        Set<DecessoMalattiaContagiosa> decessiMalattiaContagiosa = decessoMalattiaContagiosaDao.getFilteredItems(year);
                        Set<Decesso> decessi = decessoDao.getFilteredItems(year);

                        List<HashMap<String, Object>> data = new ArrayList<>();

                        if (!decessiMalattiaContagiosa.isEmpty()) {
                            int counter = 0;

                            HashMap<String, Object> row = new HashMap<>();

                            row.put("causa", "MALATTIA CONTAGIOSA");
                            row.put("decessi", 0);

                            for (DecessoMalattiaContagiosa decessoMalattiaContagiosa : decessiMalattiaContagiosa) {
                                counter += decessoMalattiaContagiosa.getNumeroMorti();
                            }
                            row.put("decessi", counter);
                            data.add(row);

                            pieChart.getData().add(new PieChart.Data("MALATTIA CONTAGIOSA", counter));
                        }

                        if (!decessi.isEmpty()) {
                            for (CausaDecesso causaDecesso : CausaDecesso.values()) {
                                HashMap<String, Object> rowDecesso = new HashMap<>();
                                rowDecesso.put("causa", causaDecesso.getNome());

                                int contDecessi = 0;
                                for (Decesso decesso : decessi) {
                                    if (decesso.getCausaDecesso().equals(causaDecesso)) {
                                        contDecessi += decesso.getNumeroMorti();
                                    }
                                }

                                rowDecesso.put("decessi", contDecessi);

                                pieChart.getData().add(new PieChart.Data(causaDecesso.getNome(), contDecessi));

                                data.add(rowDecesso);
                            }
                        }

                        ObservableList<Map> tableData = FXCollections.observableArrayList(data);

                        causaCol.setCellValueFactory(new MapValueFactory<>("causa"));
                        decessiCol.setCellValueFactory(new MapValueFactory<>("decessi"));

                        table.setItems(tableData);

                        // Lo inserisco dopo perchè non voglio che venga mostrato se non c'è il grafico
                        if (!pieChart.getData().isEmpty()) {
                            contentBox.getChildren().addAll(buttonImg);
                        }
                        contentBox.getChildren().add(separator);

                        // action event
                        EventHandler<ActionEvent> eventHandler = e -> {
                            Set<Map<String, Object>> rows = new HashSet<>();
                            if (!tableData.isEmpty()) {
                                for (Map row : tableData) {
                                    rows.add(row);
                                }

                            }
                            try {
                                Export.exportData(rows, "Decessi");
                            } catch (Exception exception) {
                                FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE", "Errore durante l'export!", null, e);
                                // DEBUG
                                // exception.printStackTrace();
                            }
                        };
                        // action event
                        EventHandler<ActionEvent> eventImg = e -> {
                            Set<Map<String, Object>> rows = new HashSet<>();
                            if (!tableData.isEmpty()) {
                                for (Map row : tableData) {
                                    rows.add(row);
                                }
                            }
                            try {
                                Export.exportImg(pieChart, "ITALIA");
                            } catch (Exception exception) {
                                FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE", "Errore durante l'export!", null, e);
                                // DEBUG
                                // exception.printStackTrace();
                            }
                        };
                        button.setOnAction(eventHandler);
                        buttonImg.setOnAction(eventImg);
                    } catch (ExecutionException | InterruptedException e) {
                        FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE", "Errore durante il caricamento!", null, actionEvent);
                        // DEBUG
                        // e.printStackTrace();
                    }
                });
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
}
