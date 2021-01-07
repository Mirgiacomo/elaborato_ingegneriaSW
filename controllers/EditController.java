package elaborato_ingegneriaSW.controllers;

import javafx.event.ActionEvent;

import java.util.concurrent.ExecutionException;

public interface EditController<T> {
    void populateForm(T model);
    void saveAction(ActionEvent event) throws ExecutionException, InterruptedException;
}
