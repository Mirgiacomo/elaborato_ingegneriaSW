package elaborato_ingegneriaSW.controllers;


import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.utils.SelectViewCallback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SidePanelPersonaleContagiController extends SidePanelController implements Initializable {

    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;
    @FXML
    private JFXButton exit;

    private SelectViewCallback callback;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}
