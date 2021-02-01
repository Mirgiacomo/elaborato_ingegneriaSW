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
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
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

public class ReportContagiComuneController implements Initializable {
    @FXML
    private CheckComboBox<Comune> comuniCheckComboBox;
    @FXML
    private SearchableComboBox<Integer> yearSearchableComboBox;
    @FXML
    private JFXButton searchButton;
    @FXML
    private VBox contentBox;

    ComuneDaoImpl comuneDao = new ComuneDaoImpl();
    ContagioDaoImpl contagioDao = new ContagioDaoImpl();
    MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();

    Set<MalattiaContagiosa> malattieContagiose;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Set<Comune> comuni = comuneDao.getAllItems(ComuneDaoImpl.getCollectionName());

            for (Comune comune: comuni) {
                comuniCheckComboBox.getItems().add(comune);
            }

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            yearSearchableComboBox.getItems().add(currentYear - 1);
            yearSearchableComboBox.getItems().add(currentYear);

            malattieContagiose = malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void searchAction(ActionEvent event) throws ExecutionException, InterruptedException {
        if (comuniCheckComboBox.getCheckModel().isEmpty() || yearSearchableComboBox.getSelectionModel().isEmpty()) {
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE CARICAMENTO", "Selezionare uno o più comuni e un anno!", null, event);
            return;
        }

        ObservableList<Comune> comuni = comuniCheckComboBox.getCheckModel().getCheckedItems();
        int year = yearSearchableComboBox.getSelectionModel().getSelectedItem();

        Set<MalattiaContagiosa> malattieContagiose;
        try {
            malattieContagiose = malattiaContagiosaDao.getAllItems(MalattiaContagiosaDaoImpl.getCollectionName());
        } catch (ExecutionException | InterruptedException e) {
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE", "Errore durante il caricamento!", null, event);
            return;
        }

        if (comuni != null && !comuni.isEmpty()) {
            contentBox.getChildren().clear();

            for (Comune comune : comuni) {
                Task<Void> task = new Task<>() {

                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                HBox box = new HBox();
                                VBox.setVgrow(box, Priority.ALWAYS);

                                box.setId(comune.getNome() + "Box");
                                box.getChildren().clear();

                                Text title = new Text();
                                title.setFont(Font.font("Open Sans Semibold", FontWeight.SEMI_BOLD, 25));
                                title.setId("title" + comune.getNome());
                                title.setText(comune.getNome());

                                JFXButton button = new JFXButton();
                                button.setText("EXPORT");
                                button.setTextFill(Color.WHITE);
                                button.setStyle("-fx-background-color: #eda324");

                                TableView<Map> table = new TableView<>();
                                HBox.setHgrow(table, Priority.SOMETIMES);

                                TableColumn<Map, String> malattiaContagiosaCol = new TableColumn<>();
                                malattiaContagiosaCol.setText("malattia");
                                malattiaContagiosaCol.setId("malattiaContagiosaCol" + comune.getNome());

                                TableColumn<Map, Integer> contagiCol = new TableColumn<>();
                                contagiCol.setText("contagi");
                                contagiCol.setId("contagiCol" + comune.getNome());

                                table.getColumns().clear();
                                table.getColumns().add(malattiaContagiosaCol);
                                table.getColumns().add(contagiCol);

                                final CategoryAxis xAxis = new CategoryAxis();
                                final NumberAxis yAxis = new NumberAxis();
                                BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
                                chart.setId("chart" + comune.getNome());
                                HBox.setHgrow(chart, Priority.SOMETIMES);

                                box.getChildren().add(table);
                                box.getChildren().add(chart);

                                Separator separator = new Separator();
                                separator.setId("separator" + comune.getNome());

                                contentBox.getChildren().add(title);
                                contentBox.getChildren().add(button);
                                contentBox.getChildren().add(box);
                                contentBox.getChildren().add(separator);

                                Set<Contagio> contagi = contagioDao.getFilteredItems(comune, year);

                                List<HashMap<String, Object>> data = new ArrayList<>();

                                if (!malattieContagiose.isEmpty()) {
                                    for (MalattiaContagiosa malattiaContagiosa: malattieContagiose) {
                                        HashMap<String, Object> row = new HashMap<>();
                                        row.put("malattia", malattiaContagiosa.getNome());

                                        int contContagi = 0;
                                        if (!contagi.isEmpty()) {
                                            for (Contagio contagio: contagi) {
                                                if (contagio.getMalattiaContagiosa().equals(malattiaContagiosa)) {
                                                    contContagi += (contagio.getNumeroMedicoBase() + contagio.getNumeroTerapiaIntensiva());
                                                }
                                            }
                                        }
                                        row.put("contagi", contContagi);
                                        data.add(row);
                                    }
                                }

                                ObservableList<Map> tableData = FXCollections.observableArrayList(data);

                                malattiaContagiosaCol.setCellValueFactory(new MapValueFactory<>("malattia"));
                                contagiCol.setCellValueFactory(new MapValueFactory<>("contagi"));

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
                                            Export.exportData(rows, "ContagiComune");
                                        } catch (Exception exception) {
                                            exception.printStackTrace();
                                        }
                                    }
                                };
                                button.setOnAction(event);

                                XYChart.Series<String, Number> seriesContagi = new XYChart.Series<>();

                                seriesContagi.setName("Contagi");

                                for (Map malattia: tableData) {
                                    seriesContagi.getData().add(new XYChart.Data<>((String) malattia.get("malattia"), (Number) malattia.get("contagi")));
                                }
                                chart.getData().clear();
                                chart.getData().addAll(seriesContagi);

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
