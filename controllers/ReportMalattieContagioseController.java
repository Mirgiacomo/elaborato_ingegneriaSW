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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

public class ReportMalattieContagioseController implements Initializable {
    @FXML
    private CheckComboBox<Provincia> provinceCheckComboBox;
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
            Set<Provincia> province = provinciaDao.getAllItems(ProvinciaDaoImpl.getCollectionName());

            for (Provincia provincia : province) {
                provinceCheckComboBox.getItems().add(provincia);
            }

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
        ObservableList<Provincia> province = provinceCheckComboBox.getCheckModel().getCheckedItems();
        int year = yearSearchableComboBox.getSelectionModel().getSelectedItem();

        Set<MalattiaContagiosa> malattieContagiose = malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());

        if (province != null && !province.isEmpty()) {
            contentBox.getChildren().clear();

            for (Provincia provincia : province) {
                Task<Void> task = new Task<>() {

                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                HBox box = new HBox();
                                VBox.setVgrow(box, Priority.ALWAYS);

                                box.setId(provincia.getNome() + "Box");
                                box.getChildren().clear();

                                Text title = new Text();
                                title.setFont(Font.font("Open Sans Semibold", FontWeight.SEMI_BOLD, 25));
                                title.setId("title" + provincia.getNome());
                                title.setText(provincia.getNome());

                                JFXButton button = new JFXButton();
                                button.setText("EXPORT");
                                button.setTextFill(Color.WHITE);
                                button.setStyle("-fx-background-color: #eda324");

                                TableView<Map> table = new TableView<>();
                                HBox.setHgrow(table, Priority.SOMETIMES);

                                TableColumn<Map, String> malattiaContagiosaCol = new TableColumn<>();
                                malattiaContagiosaCol.setText("malattia");
                                malattiaContagiosaCol.setId("malattiaContagiosaCol" + provincia.getNome());

                                TableColumn<Map, Integer> contagiCol = new TableColumn<>();
                                contagiCol.setText("contagi");
                                contagiCol.setId("contagiCol" + provincia.getNome());

                                TableColumn<Map, Integer> decessiCol = new TableColumn<>();
                                decessiCol.setText("decessi");
                                decessiCol.setId("decessiCol" + provincia.getNome());

                                table.getColumns().clear();
                                table.getColumns().add(malattiaContagiosaCol);
                                table.getColumns().add(contagiCol);
                                table.getColumns().add(decessiCol);

                                final CategoryAxis xAxis = new CategoryAxis();
                                final NumberAxis yAxis = new NumberAxis();
                                LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
                                chart.setId("chart" + provincia.getNome());
                                HBox.setHgrow(chart, Priority.SOMETIMES);

                                box.getChildren().add(table);
                                box.getChildren().add(chart);

                                Separator separator = new Separator();
                                separator.setId("separator" + provincia.getNome());

                                contentBox.getChildren().add(title);
                                contentBox.getChildren().add(button);
                                contentBox.getChildren().add(box);
                                contentBox.getChildren().add(separator);

                                Set<Contagio> contagi = contagioDao.getFilteredItems(provincia, year);
                                Set<DecessoMalattiaContagiosa> decessi = decessoMalattiaContagiosaDao.getFilteredItems(provincia, year);

                                List<HashMap<String, Object>> data = new ArrayList<>();

                                if (!malattieContagiose.isEmpty()) {
                                    for (MalattiaContagiosa malattiaContagiosa: malattieContagiose) {
                                        HashMap<String, Object> row = new HashMap<>();
                                        row.put("malattia", malattiaContagiosa.getNome());
                                        row.put("contagi", 0);
                                        row.put("decessi", 0);

                                        if (!contagi.isEmpty()) {
                                            for (Contagio contagio: contagi) {
                                                if (contagio.getMalattiaContagiosa().equals(malattiaContagiosa)) {
                                                    row.put("contagi", (contagio.getNumeroMedicoBase() + contagio.getNumeroTerapiaIntensiva()));
                                                }
                                            }
                                        }

                                        if (!decessi.isEmpty()) {
                                            for (DecessoMalattiaContagiosa decesso: decessi) {
                                                if (decesso.getMalattiaContagiosa().equals(malattiaContagiosa)) {
                                                    row.put("decessi", decesso.getNumeroMorti());
                                                }
                                            }
                                        }
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
    }
}
