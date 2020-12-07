package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.utils.ShowView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidePanelRicercatoreAnalistaController extends SidePanelController implements Initializable {

    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;
    @FXML
    private JFXButton exit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}
