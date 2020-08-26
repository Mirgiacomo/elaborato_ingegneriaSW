/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborato_ingegneriaSW.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author mirgi
 */
public class LoginController implements Initializable {

    @FXML
    private JFXCheckBox checkpass;
    private JFXPasswordField password;
    
    private String pwd;
    @FXML
    private JFXButton loginButton;

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
