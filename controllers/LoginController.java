/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.applet.Main;

/**
 * FXML Controller class
 *
 * @author mirgi
 */
public class LoginController implements Initializable {

    @FXML
    private JFXCheckBox checkpass;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXButton loginButton;
    @FXML
    private AnchorPane root;

    private String pwd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void showpassword(ActionEvent event) {
    }
    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        if(username.getText().equalsIgnoreCase("mirgiacomo") && password.getText().equalsIgnoreCase("mirgiacomo")){
            System.out.println("LOGIN");
            Stage stage = new Stage();
            VBox box = new VBox();
            stage.setTitle("My New Stage Title");

            Parent root = FXMLLoader.load(getClass().getResource("/elaborato_ingegneriaSW/views/Main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Per nascondere la finestra di login sotto
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } else {
            System.out.println("LOGOUT");
        }
    }

    /**
    // TODO: Da implementare
    @FXML
    private void showpassword(ActionEvent event) {
        checkpass.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (checkpass.isSelected()) {
                pwd = password.getText();
                password.clear();
                password.setPromptText(pwd);
            } else {
                pwd = password.getText();
                password.setText(pwd);
                password.setVisible(true);
            }

        });
    } */
}
