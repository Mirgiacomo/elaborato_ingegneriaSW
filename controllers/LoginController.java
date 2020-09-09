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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

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
    private void loginAction(ActionEvent event) {
//        try {
//            AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/elaborato_ingegneriaSW/views/InsertComune.fxml")));
//            root.getChildren().setAll(parentContent);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
