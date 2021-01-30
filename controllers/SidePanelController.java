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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SidePanelController implements Initializable {
    @FXML
    private JFXButton regioniButton;
    @FXML
    private JFXButton provinceButton;
    @FXML
    private JFXButton comuniButton;
    @FXML
    private JFXButton contagiComuniButton;
    @FXML
    private JFXButton decessiProvinciaButton;
    @FXML
    private JFXButton utentiButton;
    @FXML
    private JFXButton reportMalattieContagioseButton;
    @FXML
    private JFXButton reportDecessiProvinceButton;
    @FXML
    private JFXButton reportDecessiRegioniButton;
    @FXML
    private VBox sidebar;
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
    public void editDecessiProvinceAction(ActionEvent event) throws IOException {
        callback.selectView("editDecessiProvince.fxml");
    }

    @FXML
    public void editContagiComuniAction(ActionEvent event) throws IOException {
        callback.selectView("editContagiComuni.fxml");
    }

    @FXML
    public void viewUtentiAction(ActionEvent event) throws IOException {
        callback.selectView("viewUtenti.fxml");
    }

    @FXML
    public void reportMalattieContagioseAction(ActionEvent event) throws IOException {
        callback.selectView("reportMalattieContagiose.fxml");
    }

    @FXML
    public void reportDecessiProvinceAction(ActionEvent event) throws IOException {
        callback.selectView("reportDecessiProvince.fxml");
    }

    @FXML
    public void reportDecessiRegioniAction(ActionEvent event) throws IOException {
        callback.selectView("reportDecessiRegioni.fxml");
    }


    @FXML
    public void logoutAction(ActionEvent event) throws IOException {
        // close window
        Stage source = (Stage)(((Node)(event.getSource())).getScene().getWindow());
        source.close();

        // open new window
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader("Main.fxml");

        Stage stage = new Stage();
        Parent view = loader.load();
        MainController controller = loader.getController();
        controller.setLoggedUser(null);
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
    public void createSidePanel(Utente utente) {
        // All'inizio tolgo tutti i bottoni in modo tale da inserirli nella sequenza giusta
        sidebar.getChildren().remove(provinceButton);
        sidebar.getChildren().remove(comuniButton);
        sidebar.getChildren().remove(regioniButton);
        sidebar.getChildren().remove(contagiComuniButton);
        sidebar.getChildren().remove(decessiProvinciaButton);
        sidebar.getChildren().remove(utentiButton);
        sidebar.getChildren().remove(footerPane);
        sidebar.getChildren().remove(reportMalattieContagioseButton);
        sidebar.getChildren().remove(reportDecessiProvinceButton);
        sidebar.getChildren().remove(reportDecessiRegioniButton);

        // Mi prendo il ruole dell'utente loggato e carico le sezioni a lui visibili
        RuoloUtente ruoloUtente = utente.getRuolo();
        switch (ruoloUtente){
            // ADMIN ha permessi di visualizzare tutto
            case ADMIN:
                sidebar.getChildren().add(provinceButton);
                sidebar.getChildren().add(comuniButton);
                sidebar.getChildren().add(regioniButton);
                sidebar.getChildren().add(contagiComuniButton);
                sidebar.getChildren().add(decessiProvinciaButton);
                sidebar.getChildren().add(utentiButton);
                sidebar.getChildren().add(reportDecessiProvinceButton);
                sidebar.getChildren().add(reportDecessiRegioniButton);
                break;

            // Il personale dell’ente incaricato del monitoraggio può inserire  nuove regioni, province e comuni
            case PERSONALE_MONITORAGGIO:
                sidebar.getChildren().add(comuniButton);
                sidebar.getChildren().add(provinceButton);
                sidebar.getChildren().add(regioniButton);
                break;

            // Ogni persona assunta a contratto ha l’autorizzazione ad inserire i dati di un numero predefinito di comuni.
            // Il personale dell’ente inserisce, per ogni persona a contratto, i comuni di cui è responsabile.
            case PERSONALE_CONTAGI:
                sidebar.getChildren().add(contagiComuniButton);
                break;

            // L'apposito personale dell’ente registra annualmente per ogni provincia il numero di decessi
            case PERSONALE_DECESSI:
                // TODO: valutare se aggiungere un altro bottone per la visualizzazione di tali dati sotto forma di report
                sidebar.getChildren().add(decessiProvinciaButton);
                break;

            // Visualizzazione report
            case RICERCATORE_ANALISTA:
                sidebar.getChildren().add(reportMalattieContagioseButton);
                break;
            default:
                System.out.println("Errore. Riprovare ad accedere!\n");
                break;
        }
        sidebar.getChildren().add(footerPane);
    }

    public void infoAction(ActionEvent event) throws IOException {
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader("ViewInfo.fxml");

        Parent view = loader.load();
        Scene scene = new Scene(view);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );

        stage.setScene(scene);
        stage.showAndWait();
    }
}

