package elaborato_ingegneriaSW.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import elaborato_ingegneriaSW.utils.SelectViewCallback;
import elaborato_ingegneriaSW.utils.ShowView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    @FXML
    public void logoutAction(ActionEvent event) throws IOException {
        loggedUser = null;

        // close window
        Stage source = (Stage)(((Node)(event.getSource())).getScene().getWindow());
        source.close();

        // open new window
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader("Main.fxml");

        Stage stage = new Stage();
        Parent view = loader.load();
        MainController controller = loader.getController();
        controller.loadView();

        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }
}
