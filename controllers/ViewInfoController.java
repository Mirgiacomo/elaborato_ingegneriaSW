package elaborato_ingegneriaSW.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewInfoController implements Initializable {
    @FXML
    private Hyperlink toninMailLink;
    @FXML
    private Hyperlink toninLinkedinLink;
    @FXML
    private Hyperlink mirandolaMailLink;
    @FXML
    private Hyperlink mirandolaLinkedinLink;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toninMailLink.setOnAction(e -> {
            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URI("mailto:davide.tonin99@gmail.com"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        toninLinkedinLink.setOnAction(e -> {
            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/davide-tonin-139878129/?originalSubdomain=it"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        mirandolaMailLink.setOnAction(e -> {
            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URI("mailto:mirgiacomo@gmail.com"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        mirandolaMailLink.setOnAction(e -> {
            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URI("https://it.linkedin.com/in/giacomo-mirandola-87626b158"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
