package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.utils.ShowView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public interface ViewController<T> {
    default void showInsertView(ActionEvent event, String viewName) throws IOException {
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader(viewName);

        Parent view = loader.load();
        Scene scene = new Scene(view);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());

        stage.setScene(scene);
        stage.showAndWait();
    }
    default void showUpdateView(ActionEvent event, String viewName, T model) throws IOException {
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader(viewName);

        Parent view = loader.load();
        Scene scene = new Scene(view);

        EditController<T> controller = loader.getController();
        controller.populateForm(model);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());

        stage.setScene(scene);
        stage.showAndWait();
    }
}
