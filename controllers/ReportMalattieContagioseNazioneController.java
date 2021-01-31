package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.dao.ContagioDaoImpl;
import elaborato_ingegneriaSW.dao.DecessoMalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.dao.MalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.models.Contagio;
import elaborato_ingegneriaSW.models.DecessoMalattiaContagiosa;
import elaborato_ingegneriaSW.models.MalattiaContagiosa;
import elaborato_ingegneriaSW.models.Provincia;
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
import javafx.scene.chart.*;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class ReportMalattieContagioseNazioneController implements Initializable {
    @FXML
    private SearchableComboBox<Integer> yearSearchableComboBox;
    @FXML
    private JFXButton searchButton;
    @FXML
    private VBox contentBox;

    ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();
    ContagioDaoImpl contagioDao = new ContagioDaoImpl();
    DecessoMalattiaContagiosaDaoImpl decessoMalattiaContagiosaDao = new DecessoMalattiaContagiosaDaoImpl();
    MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();

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

        Set<MalattiaContagiosa> malattieContagiose = malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    try {
                        contentBox.getChildren().clear();

                        HBox piebox = new HBox();
                        VBox.setVgrow(piebox, Priority.ALWAYS);

                        piebox.setId("PieChartBox");
                        piebox.getChildren().clear();

                        HBox box = new HBox();
                        VBox.setVgrow(box, Priority.ALWAYS);

                        box.setId("Box");
                        box.getChildren().clear();

                        Text title = new Text();
                        title.setFont(Font.font("Open Sans Semibold", FontWeight.SEMI_BOLD, 25));
                        title.setText("ITALIA");

                        JFXButton button = new JFXButton();
                        button.setText("EXPORT");
                        button.setTextFill(Color.WHITE);
                        button.setStyle("-fx-background-color: #eda324");

                        TableView<Map> table = new TableView<>();
                        HBox.setHgrow(table, Priority.SOMETIMES);

                        TableColumn<Map, String> malattiaContagiosaCol = new TableColumn<>();
                        malattiaContagiosaCol.setText("malattia");
                        malattiaContagiosaCol.setId("malattiaContagiosaCol");

                        TableColumn<Map, Integer> contagiCol = new TableColumn<>();
                        contagiCol.setText("contagi");
                        contagiCol.setId("contagiCol");

                        TableColumn<Map, Integer> decessiCol = new TableColumn<>();
                        decessiCol.setText("decessi");
                        decessiCol.setId("decessiCol");

                        table.getColumns().clear();
                        table.getColumns().add(malattiaContagiosaCol);
                        table.getColumns().add(contagiCol);
                        table.getColumns().add(decessiCol);

                        final CategoryAxis xAxis = new CategoryAxis();
                        final NumberAxis yAxis = new NumberAxis();
                        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
                        chart.setId("chartLine");
                        HBox.setHgrow(chart, Priority.SOMETIMES);

                        box.getChildren().add(table);
                        box.getChildren().add(chart);

                        Separator separator = new Separator();
                        separator.setId("separator");

                        contentBox.getChildren().add(title);
                        contentBox.getChildren().add(button);
                        contentBox.getChildren().add(piebox);
                        contentBox.getChildren().add(box);
                        contentBox.getChildren().add(separator);

                        Set<Contagio> contagi = contagioDao.getFilteredItems(year);
                        Set<DecessoMalattiaContagiosa> decessi = decessoMalattiaContagiosaDao.getFilteredItems(year);

                        List<HashMap<String, Object>> data = new ArrayList<>();

                        PieChart pieChartContagi = new PieChart();
                        pieChartContagi.setTitle("CONTAGI");
                        pieChartContagi.setId("pieContagi");
                        pieChartContagi.setLabelLineLength(2);
                        pieChartContagi.setLegendSide(Side.BOTTOM);
                        HBox.setHgrow(pieChartContagi, Priority.SOMETIMES);

                        PieChart pieChartDecessi = new PieChart();
                        pieChartDecessi.setTitle("DECESSI");
                        pieChartDecessi.setId("pieDecessi");
                        pieChartDecessi.setLabelLineLength(2);
                        pieChartDecessi.setLegendSide(Side.BOTTOM);
                        HBox.setHgrow(pieChartDecessi, Priority.SOMETIMES);

                        piebox.getChildren().add(pieChartContagi);
                        piebox.getChildren().add(pieChartDecessi);

                        if (!malattieContagiose.isEmpty()) {
                            for (MalattiaContagiosa malattiaContagiosa: malattieContagiose) {
                                HashMap<String, Object> row = new HashMap<>();
                                row.put("malattia", malattiaContagiosa.getNome());
                                int contContagi = 0;
                                int contDecessi = 0;

                                if (!contagi.isEmpty()) {
                                    for (Contagio contagio: contagi) {
                                        if (contagio.getMalattiaContagiosa().equals(malattiaContagiosa)) {
                                            contContagi += (contagio.getNumeroMedicoBase() + contagio.getNumeroTerapiaIntensiva());
                                        }
                                    }
                                }
                                pieChartContagi.getData().add(new PieChart.Data(malattiaContagiosa.getNome(), contContagi));

                                if (!decessi.isEmpty()) {
                                    for (DecessoMalattiaContagiosa decesso: decessi) {
                                        if (decesso.getMalattiaContagiosa().equals(malattiaContagiosa)) {
                                            contDecessi += decesso.getNumeroMorti();
                                        }
                                    }
                                }
                                pieChartDecessi.getData().add(new PieChart.Data(malattiaContagiosa.getNome(), contDecessi));

                                row.put("contagi", contContagi);
                                row.put("decessi", contDecessi);
                                data.add(row);
                            }
                        }

                        ObservableList<Map> tableData = FXCollections.observableArrayList(data);

                        malattiaContagiosaCol.setCellValueFactory(new MapValueFactory<>("malattia"));
                        contagiCol.setCellValueFactory(new MapValueFactory<>("contagi"));
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
                                    Export.exportData(rows, "MalattieContagiose");
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        };
                        button.setOnAction(event);

                        XYChart.Series<String, Number> seriesContagi = new XYChart.Series<>();
                        XYChart.Series<String, Number> seriesDecessi = new XYChart.Series<>();

                        seriesContagi.setName("Contagi");
                        seriesDecessi.setName("Decessi");

                        for (Map malattia: tableData) {
                            seriesContagi.getData().add(new XYChart.Data<>((String) malattia.get("malattia"), (Number) malattia.get("contagi")));
                            seriesDecessi.getData().add(new XYChart.Data<>((String) malattia.get("malattia"), (Number) malattia.get("decessi")));
                        }
                        chart.getData().clear();
                        chart.getData().addAll(seriesContagi, seriesDecessi);
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
