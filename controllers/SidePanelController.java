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
    private JFXButton reportContagiComuneButton;
    @FXML
    private JFXButton reportMalattieContagioseProvinciaButton;
    @FXML
    private JFXButton reportMalattieContagioseRegioneButton;
    @FXML
    private JFXButton reportMalattieContagioseNazioneButton;
    @FXML
    private JFXButton reportDecessiProvinceButton;
    @FXML
    private JFXButton reportDecessiRegioniButton;
    @FXML
    private JFXButton reportDecessiNazioneButton;
    @FXML
    private VBox sidebar;

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
    public void reportContagiComuneAction(ActionEvent actionEvent) throws IOException {
        callback.selectView("reportContagiComune.fxml");
    }

    @FXML
    public void reportMalattieContagioseProvinciaAction(ActionEvent event) throws IOException {
        callback.selectView("reportMalattieContagioseProvincia.fxml");
    }
    @FXML
    public void reportMalattieContagioseRegioneAction(ActionEvent actionEvent) throws IOException {
        callback.selectView("reportMalattieContagioseRegione.fxml");
    }
    @FXML
    public void reportMalattieContagioseNazioneAction(ActionEvent actionEvent) throws IOException {
        callback.selectView("reportMalattieContagioseNazione.fxml");
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
    public void reportDecessiNazioneAction(ActionEvent event) throws IOException {
        callback.selectView("reportDecessiNazione.fxml");
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
        sidebar.getChildren().clear();

        // Mi prendo il ruole dell'utente loggato e carico le sezioni a lui visibili
        RuoloUtente ruoloUtente = utente.getRuolo();
        switch (ruoloUtente){
            // ADMIN ha permessi di visualizzare tutto
            case ADMIN:
                sidebar.getChildren().add(comuniButton);
                sidebar.getChildren().add(provinceButton);
                sidebar.getChildren().add(regioniButton);
                sidebar.getChildren().add(utentiButton);
                sidebar.getChildren().add(contagiComuniButton);
                sidebar.getChildren().add(decessiProvinciaButton);
                sidebar.getChildren().add(reportContagiComuneButton);
                sidebar.getChildren().add(reportMalattieContagioseProvinciaButton);
                sidebar.getChildren().add(reportMalattieContagioseRegioneButton);
                sidebar.getChildren().add(reportMalattieContagioseNazioneButton);
                sidebar.getChildren().add(reportDecessiProvinceButton);
                sidebar.getChildren().add(reportDecessiRegioniButton);
                sidebar.getChildren().add(reportDecessiNazioneButton);
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
                sidebar.getChildren().add(reportDecessiProvinceButton);
                sidebar.getChildren().add(reportDecessiRegioniButton);
                sidebar.getChildren().add(reportDecessiNazioneButton);
                break;

            // Visualizzazione report
            case RICERCATORE_ANALISTA:
                sidebar.getChildren().add(reportContagiComuneButton);
                sidebar.getChildren().add(reportMalattieContagioseProvinciaButton);
                sidebar.getChildren().add(reportMalattieContagioseRegioneButton);
                sidebar.getChildren().add(reportMalattieContagioseNazioneButton);
                sidebar.getChildren().add(reportDecessiProvinceButton);
                sidebar.getChildren().add(reportDecessiRegioniButton);
                sidebar.getChildren().add(reportDecessiNazioneButton);
                break;
            default:
                System.out.println("Errore. Riprovare ad accedere!\n");
                break;
        }
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

