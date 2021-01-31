package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.dao.*;
import elaborato_ingegneriaSW.models.*;
import elaborato_ingegneriaSW.utils.Export;
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
import org.controlsfx.control.CheckComboBox;
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

    RegioneDaoImpl regioneDao = new RegioneDaoImpl();
    ContagioDaoImpl contagioDao = new ContagioDaoImpl();
    DecessoMalattiaContagiosaDaoImpl decessoMalattiaContagiosaDao = new DecessoMalattiaContagiosaDaoImpl();
    MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();
    DecessoDaoImpl decessoDao = new DecessoDaoImpl();

    Set<MalattiaContagiosa> malattieContagiose;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            yearSearchableComboBox.getItems().add(currentYear - 1);
            yearSearchableComboBox.getItems().add(currentYear);

            malattieContagiose = malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void searchAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        // TODO: check filtri
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
                buttonImg.setStyle("-fx-background-color: A4E06E");

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
                pieChart.setLabelLineLength(2);
                pieChart.setLabelsVisible(false);
                pieChart.setLegendSide(Side.RIGHT);
                HBox.setHgrow(pieChart, Priority.SOMETIMES);

                box.getChildren().add(table);
                box.getChildren().add(pieChart);

                Separator separator = new Separator();
                separator.setId("separatorNazione");

                contentBox.getChildren().add(title);
                contentBox.getChildren().add(button);
                contentBox.getChildren().add(box);
                contentBox.getChildren().addAll(buttonImg);
                contentBox.getChildren().add(separator);

                Set<DecessoMalattiaContagiosa> decessiMalattiaContagiosa = decessoMalattiaContagiosaDao.getFilteredItems(year);
                Set<Decesso> decessi = decessoDao.getFilteredItems(year);

                List<HashMap<String, Object>> data = new ArrayList<>();

                if (!decessiMalattiaContagiosa.isEmpty() && !decessi.isEmpty()) {
                    int counter = 0;

                    HashMap<String, Object> row = new HashMap<>();

                    row.put("causa", "MALATTIA CONTAGIOSA");
                    row.put("decessi", 0);
                    if (!decessiMalattiaContagiosa.isEmpty()) {
                        for (DecessoMalattiaContagiosa decessoMalattiaContagiosa: decessiMalattiaContagiosa) {
                            counter += decessoMalattiaContagiosa.getNumeroMorti();
                        }
                        row.put("decessi", counter);
                        data.add(row);

                    }

                    pieChart.getData().add(new PieChart.Data("MALATTIA CONTAGIOSA", counter));

                    for (CausaDecesso causaDecesso: CausaDecesso.values()) {
                        HashMap<String, Object> rowDecesso = new HashMap<>();
                        rowDecesso.put("causa", causaDecesso.getNome());

                        int contDecessi = 0;
                        for (Decesso decesso: decessi) {
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

                // action event
                EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        Set<Map<String, Object>> rows = new HashSet<>();
                        if (!tableData.isEmpty()) {
                            for (Map row: tableData) {
                                rows.add(row);
                            }

                        }
                        try {
                            Export.exportData(rows, "Decessi");
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                };
                // action event
                EventHandler<ActionEvent> eventImg = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        Set<Map<String, Object>> rows = new HashSet<>();
                        if (!tableData.isEmpty()) {
                            for (Map row: tableData) {
                                rows.add(row);
                            }
                        }
                        try {
                            Export.exportImg(pieChart, "ITALIA");
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                };
                button.setOnAction(event);
                buttonImg.setOnAction(eventImg);
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
}
