package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.MainApp;
import elaborato_ingegneriaSW.models.RuoloUtente;
import elaborato_ingegneriaSW.models.Utente;
import elaborato_ingegneriaSW.utils.LoggedUser;
import elaborato_ingegneriaSW.utils.SelectViewCallback;
import com.jfoenix.controls.JFXDrawer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import elaborato_ingegneriaSW.utils.ShowView;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MainController implements Initializable, SelectViewCallback {
    private final ShowView showView = new ShowView();
    //private Utente loggedUser = new Utente("mirandola", "giacomo", "mirgiacomo", "", RuoloUtente.ADMIN , "", null);

    @FXML
    private JFXDrawer drawer;

    // @FXML
    // private JFXHamburger hamburger;

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private Text nomeCognomeText;
    @FXML
    private Text ruoloText;

    //private HamburgerBackArrowBasicTransition transition;

    public MainController() { }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void loadView()
    {
        if (LoggedUser.getLoggedUser() == null) {
            showLogin();
        } else {
            /*transition = new HamburgerBackArrowBasicTransition(hamburger);
            transition.setRate(1);*/
            try {
                // TODO: togliere la seguente riga, solo per debug
                //LoggedUser.setLoggedUser(loggedUser);
                FXMLLoader loader = showView.getLoader("SidePanel.fxml");
                VBox box = loader.load();

                SidePanelController controller = loader.getController();
                controller.createSidePanel(LoggedUser.getLoggedUser());
                controller.setCallback(this);

                drawer.setSidePane(box);
                drawer.open();

                Utente loggedUser = LoggedUser.getLoggedUser();
                nomeCognomeText.setText(loggedUser.getNome() + " " + loggedUser.getCognome());
                ruoloText.setText(loggedUser.getRuolo().toString());

            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

            /*hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                transition.setRate(transition.getRate() * -1);
                transition.play();

                if (drawer.isOpened()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            });*/
        }
    }

    private void showLogin()
    {
        try {
            AnchorPane loginPane = FXMLLoader.load(getClass().getResource(("/elaborato_ingegneriaSW/views/Login.fxml")));

            if (!MainApp.isInitLoaded) {
                MainApp.isInitLoaded = true;

                ShowView showView = new ShowView();
                Pane loadingPane = showView.getLoader("Loading.fxml").load();

                root.getChildren().clear();
                root.getChildren().add(loadingPane);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), loadingPane);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.setCycleCount(1);

                FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), loadingPane);
                fadeOut.setFromValue(1);
                fadeOut.setToValue(0);
                fadeOut.setCycleCount(1);

                fadeIn.play();

                fadeIn.setOnFinished((e) -> {
                    fadeOut.play();
                });

                fadeOut.setOnFinished((e) -> {
                    root.getChildren().clear();
                    root.getChildren().add(loginPane);
                });
            } else {
                root.getChildren().clear();
                root.getChildren().add(loginPane);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void selectView(String view) throws IOException
    {
        FXMLLoader loader = showView.getLoader(view);
        Parent content = loader.load();

        contentPane.getChildren().setAll(content);
    }

    public void setLoggedUser(Utente user)
    {
        LoggedUser.setLoggedUser(user);
    }
}
