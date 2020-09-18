package elaborato_ingegneriaSW.utils;

import javafx.fxml.FXMLLoader;

public class ShowView {

    public ShowView() { }

    public FXMLLoader getLoader(String viewName) {
        return new FXMLLoader(getClass().getResource("/elaborato_ingegneriaSW/views/"+viewName));
    }
}
