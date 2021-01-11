package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.utils.ShowView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public abstract class ViewController<T> {
    ObservableList<T> tableData;

    abstract void setTableData(Set<T> data);

    public void showInsertView(ActionEvent event, String viewName) throws IOException {
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader(viewName);

        Parent view = loader.load();
        Scene scene = new Scene(view);

        EditController<T> controller = loader.getController();
        controller.setModel(null);
        controller.setTableData(tableData);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());

        stage.setScene(scene);
        stage.showAndWait();
    }
    public void showUpdateView(ActionEvent event, String viewName, T model) throws IOException {
        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader(viewName);

        Parent view = loader.load();
        Scene scene = new Scene(view);

        EditController<T> controller = loader.getController();
        controller.setModel(model);
        controller.setTableData(tableData);
        controller.populateForm();

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());

        stage.setScene(scene);
        stage.showAndWait();
    }
}
