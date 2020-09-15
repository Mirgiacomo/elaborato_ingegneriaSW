/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborato_ingegneriaSW.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author mirgi
 */
public class InsertRegioneController implements Initializable {
    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField comuneCapoluogoTextField;
    @FXML
    private JFXTextField superficieTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void insertRegioneAction(ActionEvent event) throws ExecutionException, InterruptedException {
        System.out.println(nomeTextField.getText());
    }
    
}
