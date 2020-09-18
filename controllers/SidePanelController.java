package elaborato_ingegneriaSW.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import elaborato_ingegneriaSW.utils.SelectViewCallback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public abstract class SidePanelController extends AbstractController implements Initializable {
    private SelectViewCallback callback;

    public void setCallback(SelectViewCallback callback) {
        this.callback = callback;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    @FXML
    public void viewRegioniAction(ActionEvent event) throws IOException {
        callback.selectView("viewRegioni.fxml");
    }

    @FXML
    public void viewProvinceAction(ActionEvent event) throws IOException {
        callback.selectView("viewProvince.fxml");
    }

    @FXML
    public void viewComuniAction(ActionEvent event) throws IOException {
        callback.selectView("viewComuni.fxml");
    }
}
