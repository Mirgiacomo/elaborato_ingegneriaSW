package elaborato_ingegneriaSW.controllers;


import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.utils.SelectViewCallback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidePanelPersonaleMonitoraggioController implements Initializable {

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
    public void initialize(URL url, ResourceBundle rb) {

    }
    public void setCallback(SelectViewCallback callback) {
        this.callback = callback;
    }

    @FXML
    private void changeColor(ActionEvent event) throws IOException {
        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());
        switch (btn.getText()) {
            case "Color 1":
                callback.selectView("#00FF00");
                break;
            case "Color 2":
                callback.selectView("#0000FF");
                break;
            case "Color 3":
                callback.selectView("#FF0000");
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}
