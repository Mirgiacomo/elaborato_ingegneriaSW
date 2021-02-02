package elaborato_ingegneriaSW.utils;

import elaborato_ingegneriaSW.controllers.ViewController;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;

import java.io.IOException;


public class EditButtonCell<T> extends TableCell<T, String> {
    final Button editButton = new Button("✎");

    public EditButtonCell(final TableView<T> table, final ViewController<T> controller, final String editViewName) {
        editButton.setTextFill(Paint.valueOf("white"));
        editButton.setOnAction(event -> {
            // Evento: premo sul bottone
            try {
                T model = table.getSelectionModel().getSelectedItem(); // prende la riga selezionata
                if (!table.getSelectionModel().isEmpty()) {
                    controller.showUpdateView(event, editViewName, model);
                } else {
                    FXUtil.Alert(Alert.AlertType.WARNING, "ATTENZIONE!", "Selezionare la riga da modificare, prima di premere il bottone!", null, event);
                }
            } catch (IOException e) {
                FXUtil.Alert(Alert.AlertType.ERROR, "ERRORE!", "Errore durante l'esecuzione!", null, event);
            }
        });
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(editButton);
        }
        setText(null);
    }
}
