package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import elaborato_ingegneriaSW.dao.Dao;
import elaborato_ingegneriaSW.dao.DaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.*;

public class ReportDecessiController implements Initializable {
    @FXML
    private JFXComboBox<String> suddivisioneComboBox;
    @FXML
    private JFXComboBox<Integer> yearFilterComboBox;
    @FXML
    private JFXButton loadDecessiButton;
    @FXML
    private TableView<Object> tableDecessi;
    @FXML
    public TableColumn<Object, String> nomeCol;
    @FXML
    public TableColumn<Object, String> causaCol;
    @FXML
    public TableColumn<Object, String> qtaCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        suddivisioneComboBox.getItems().add("PROVINCIA");
        suddivisioneComboBox.getItems().add("REGIONE");
        suddivisioneComboBox.getItems().add("NAZIONE");
        yearFilterComboBox.getItems().add(2019);
        yearFilterComboBox.getItems().add(2020);
        yearFilterComboBox.getItems().add(2021);
    }

    @FXML
    public void loadDecessiAction(ActionEvent actionEvent) {
    }
}
