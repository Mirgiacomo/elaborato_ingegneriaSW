package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.sun.xml.bind.v2.model.core.RegistryInfo;
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
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ReportDecessiRegioneController implements Initializable {
    @FXML
    private CheckComboBox<Regione> regioniCheckComboBox;
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
            Set<Regione> regioni = regioneDao.getAllItems(RegioneDaoImpl.getCollectionName());

            for (Regione regione : regioni) {
                regioniCheckComboBox.getItems().add(regione);
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

        ObservableList<Regione> regioni = regioniCheckComboBox.getCheckModel().getCheckedItems();
        if(yearSearchableComboBox.getSelectionModel().isEmpty()){
            FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE FILTRO!", "Anno inserito nel filtro non valido!", null, actionEvent);
            return;
        }
        int year = yearSearchableComboBox.getSelectionModel().getSelectedItem();

        if (regioni != null && !regioni.isEmpty()) {
            contentBox.getChildren().clear();

            for (Regione regione : regioni) {
                Task<Void> task = new Task<>() {

                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                HBox box = new HBox();
                                VBox.setVgrow(box, Priority.ALWAYS);

                                box.setId(regione.getNome() + "Box");
                                box.getChildren().clear();

                                Text title = new Text();
                                title.setFont(Font.font("Open Sans Semibold", FontWeight.SEMI_BOLD, 25));
                                title.setId("title" + regione.getNome());
                                title.setText(regione.getNome());

                                JFXButton button = new JFXButton();
                                button.setText("EXPORT");
                                button.setTextFill(Color.WHITE);
                                button.setStyle("-fx-background-color: #eda324");

                                JFXButton buttonImg = new JFXButton();
                                buttonImg.setText("EXPORT GRAFICO");
                                buttonImg.setTextFill(Color.WHITE);
                                buttonImg.setStyle("-fx-background-color: black; -fx-font-size: 10px;");

                                TableView<Map> table = new TableView<>();
                                HBox.setHgrow(table, Priority.SOMETIMES);

                                TableColumn<Map, String> causaCol = new TableColumn<>();
                                causaCol.setText("causa");
                                causaCol.setId("causaCol" + regione.getNome());

                                TableColumn<Map, Integer> decessiCol = new TableColumn<>();
                                decessiCol.setText("decessi");
                                decessiCol.setId("decessiCol" + regione.getNome());

                                table.getColumns().clear();
                                table.getColumns().add(causaCol);
                                table.getColumns().add(decessiCol);

                                PieChart pieChart = new PieChart();
                                pieChart.setId("pie" + regione.getNome());
                                pieChart.setLabelLineLength(2);
                                pieChart.setLabelsVisible(false);
                                pieChart.setLegendSide(Side.RIGHT);
                                HBox.setHgrow(pieChart, Priority.SOMETIMES);

                                box.getChildren().add(table);
                                box.getChildren().add(pieChart);

                                Separator separator = new Separator();
                                separator.setId("separator" + regione.getNome());

                                contentBox.getChildren().add(title);
                                contentBox.getChildren().add(button);
                                contentBox.getChildren().add(box);
                                contentBox.getChildren().addAll(buttonImg);
                                contentBox.getChildren().add(separator);

                                Set<DecessoMalattiaContagiosa> decessiMalattiaContagiosa = decessoMalattiaContagiosaDao.getFilteredItems(regione, year);
                                Set<Decesso> decessi = decessoDao.getFilteredItems(regione, year);

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

                                    }
                                    data.add(row);

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
                                            Export.exportImg(pieChart, regione.getNome());
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
    }
}
