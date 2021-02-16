package elaborato_ingegneriaSW.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.util.concurrent.ExecutionException;

public abstract class EditController<T> {
    protected ObservableList<T> tableData;
    protected T model;

    abstract void setModel(T model);
    abstract void setTableData(ObservableList<T> tableData);
    abstract void populateForm();
    abstract void saveAction(ActionEvent event);
}
