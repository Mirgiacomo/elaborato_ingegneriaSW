package elaborato_ingegneriaSW.utils;

import elaborato_ingegneriaSW.controllers.AbstractViewController;
import elaborato_ingegneriaSW.models.AbstractTableModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;

import java.io.IOException;


public class EditButtonCell extends TableCell<AbstractTableModel, String> {
    final Button editButton = new Button("✎");

    public EditButtonCell(final TableView table, final AbstractViewController controller, final String editViewName) {
        editButton.setTextFill(Paint.valueOf("white"));
        editButton.setOnAction(event -> {
            // Evento: premo sul bottone
            try {
                AbstractTableModel model = (AbstractTableModel) table.getSelectionModel().getSelectedItem(); // prende la riga selezionata
                if (!table.getSelectionModel().isEmpty()) {
                    controller.showUpdateView(event, editViewName, model);
                } else {
                    FXUtil.Alert(Alert.AlertType.WARNING, "ATTENZIONE!", "Selezionare la riga da modificare, prima di premere il bottone!", null, event);
                }
            } catch (IOException e) {
                e.printStackTrace();
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
