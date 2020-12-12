package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXTextField;
import elaborato_ingegneriaSW.MainApp;
import elaborato_ingegneriaSW.dao.UtenteDaoImpl;
import elaborato_ingegneriaSW.models.Utente;
import elaborato_ingegneriaSW.utils.FXUtil;
import elaborato_ingegneriaSW.utils.ShowView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    public AnchorPane loginRoot;
    @FXML
    private JFXCheckBox showPassword;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXTextField passwordField_hidden;
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXButton loginButton;

    private final UtenteDaoImpl userDao = new UtenteDaoImpl();

    public LoginController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       showPassword(null);
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
        String username = usernameTextField.getText().toLowerCase();
        String password = passwordField.getText();

        Utente user = userDao.getItem(username);

        if (user == null || password.equals("")) {
            FXUtil.Alert(Alert.AlertType.ERROR, "LOGIN FALLITO", "Dati non validi!", null, event);
        } else if (user.getPassword().equals(password)) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.hide();

            ShowView showView = new ShowView();
            FXMLLoader loader = showView.getLoader("Main.fxml");

            // NOTA: fare prima loader.load(), e poi loader.getController(), altrimenti controller = null
            Parent view = loader.load();
            Scene scene = new Scene(view);
            stage.setTitle("Centro malattie infettive");
            stage.setScene(scene);

            MainController controller = loader.getController();
            controller.setLoggedUser(user);
            controller.loadView();

            stage.show();


            // Per nascondere la finestra di login sotto
            //((Node)(event.getSource())).getScene().getWindow().hide();
        } else {
            FXUtil.Alert(Alert.AlertType.ERROR, "LOGIN FALLITO", "Dati non validi!", null, event);
        }
    }

    @FXML
    private void showPassword(ActionEvent event) {
        if (showPassword.isSelected()) {
            passwordField_hidden.setText(passwordField.getText());
            passwordField_hidden.setVisible(true);
            passwordField.setVisible(false);
        } else {
            passwordField.setText(passwordField_hidden.getText());
            passwordField.setVisible(true);
            passwordField_hidden.setVisible(false);
        }
    }
}
