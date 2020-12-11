package elaborato_ingegneriaSW.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import elaborato_ingegneriaSW.models.RuoloUtente;
import elaborato_ingegneriaSW.models.Utente;
import elaborato_ingegneriaSW.utils.SelectViewCallback;
import elaborato_ingegneriaSW.utils.ShowView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SidePanelController extends AbstractController implements Initializable {
    @FXML
    private JFXButton regioniButton;
    @FXML
    private JFXButton provinceButton;
    @FXML
    private JFXButton comuniButton;
    @FXML
    private JFXButton grafico1Button;
    @FXML
    private VBox vbox;
    @FXML
    private Pane footerPane;

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
    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void addButton(Utente utente) {
        vbox.getChildren().remove(provinceButton);
        vbox.getChildren().remove(comuniButton);
        vbox.getChildren().remove(regioniButton);
        vbox.getChildren().remove(grafico1Button);
        vbox.getChildren().remove(footerPane);

        RuoloUtente ruoloUtente = utente.getRuolo();
        // TODO: da finire
        switch (ruoloUtente){
            case ADMIN:
                vbox.getChildren().add(comuniButton);
                vbox.getChildren().add(provinceButton);
                vbox.getChildren().add(regioniButton);
                break;
            case RICERCATORE_ANALISTA:
                vbox.getChildren().add(grafico1Button);
                break;
            default:
                System.out.println("Errore. Riprovare ad accedere!\n");
                break;
        }
        vbox.getChildren().add(footerPane);
    }
}

