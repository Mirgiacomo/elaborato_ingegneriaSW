package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ReportDecessiController implements Initializable {
    @FXML
    private JFXComboBox<Integer> yearFilterComboBox;
//    @FXML
//    private VBox contentBox;
//    @FXML
//    private TableView<Object> tableDecessiProvincia;
//    @FXML
//    private TableView<Object> tableDecessiRegione;
//    @FXML
//    private TableView<Object> tableDecessiNazione;
//    @FXML
//    public TableColumn<Object, String> provinciaCol;
//    @FXML
//    public TableColumn<Object, String> causaCol;
//    @FXML
//    public TableColumn<Object, String> qtaCol;
        @FXML
        private VBox contentBox;

    DecessoDaoImpl decessoDao = new DecessoDaoImpl();
    DecessoMalattiaContagiosaDaoImpl decessoMalattiaContagiosaDao = new DecessoMalattiaContagiosaDaoImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        yearFilterComboBox.getItems().add(2019);
        yearFilterComboBox.getItems().add(2020);
        yearFilterComboBox.getItems().add(2021);
    }

    @FXML
    public void loadDecessiAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        int year = yearFilterComboBox.getSelectionModel().getSelectedItem();

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    try {
                        HBox box = new HBox();
                        VBox.setVgrow(box, Priority.ALWAYS);

                        box.setId("ProvinciaBox");
                        box.getChildren().clear();

//                            Text title = new Text();
//                            title.setFont(Font.font("Open Sans Semibold", FontWeight.SEMI_BOLD, 25));
//                            title.setId("title" + provincia.getNome());
//                            title.setText(provincia.getNome());

//                            JFXButton button = new JFXButton();
//                            button.setText("EXPORT");
//                            button.setTextFill(Color.WHITE);
//                            button.setStyle("-fx-background-color: #eda324");

                        TableView<Map> tableProv = new TableView<>();
                        HBox.setHgrow(tableProv, Priority.SOMETIMES);
//
                        TableColumn<Map, String> provinciaCol = new TableColumn<>();
                        provinciaCol.setText("malattia");
                        provinciaCol.setId("provinciaCol");
//
                        TableColumn<Map, String> causaCol = new TableColumn<>();
                        causaCol.setText("causa");
                        causaCol.setId("causaCol");

                        TableColumn<Map, Integer> qtaCol = new TableColumn<>();
                        qtaCol.setText("qta");
                        qtaCol.setId("qtaCol");

                        tableProv.getColumns().clear();
                        tableProv.getColumns().add(provinciaCol);
                        tableProv.getColumns().add(causaCol);
                        tableProv.getColumns().add(qtaCol);

//                            final CategoryAxis xAxis = new CategoryAxis();
//                            final NumberAxis yAxis = new NumberAxis();
//                            LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
//                            chart.setId("chart" + provincia.getNome());
//                            HBox.setHgrow(chart, Priority.SOMETIMES);

                         // contentBox.getChildren().add(tableDecessiProvincia);
//                            box.getChildren().add(chart);

//                            Separator separator = new Separator();
//                            separator.setId("separator" + provincia.getNome());

//                            contentBox.getChildren().add(title);
//                            contentBox.getChildren().add(button);
                            contentBox.getChildren().add(box);
//                            contentBox.getChildren().add(separator);

                        Set<DecessoMalattiaContagiosa> decessiMalattiaContagiosa = decessoMalattiaContagiosaDao.getFilteredItems(year);

                        List<HashMap<String, Object>> data = new ArrayList<>();

                        if (!decessiMalattiaContagiosa.isEmpty()) {
                            for (DecessoMalattiaContagiosa decessoMalattiaContagiosa: decessiMalattiaContagiosa) {
                                HashMap<String, Object> row = new HashMap<>();
                                row.put("Provincia", decessoMalattiaContagiosa.getProvincia());
                                row.put("malattia", decessoMalattiaContagiosa.getMalattiaContagiosa().getNome());
                                row.put("decessi", decessoMalattiaContagiosa.getNumeroMorti());

                                data.add(row);
                            }
                        }

                        ObservableList<Map> tableData = FXCollections.observableArrayList(data);

                        provinciaCol.setCellValueFactory(new MapValueFactory<>("provincia"));
                        causaCol.setCellValueFactory(new MapValueFactory<>("causa"));
                        qtaCol.setCellValueFactory(new MapValueFactory<>("qta"));

                        tableProv.setItems(tableData);

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
                                    System.out.println("das");
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        };
//                        button.setOnAction(event);

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
