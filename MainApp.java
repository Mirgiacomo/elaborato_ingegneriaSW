package elaborato_ingegneriaSW;

import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.models.Comune;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.models.Territorio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.ExecutionException;

public class MainApp extends Application {

    public static Boolean isSplashLoaded = false;

   @Override
    public void start(Stage stage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("views/Login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("views/Main.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();
        RegioneDaoImpl regioneDao = new RegioneDaoImpl();
        ComuneDaoImpl comuneDao = new ComuneDaoImpl();

        Regione regione = regioneDao.addItem(new Regione("Veneto", "Venezia", 18345));

        provinciaDao.addItem(new Provincia("Venezia", 414.16, regione));
        Provincia provincia = provinciaDao.addItem(new Provincia("Verona", 206.6, regione));

        comuneDao.addItem(new Comune("023017", "Caldiero", "01/01/1900", 10.37, Territorio.PIANURA, false, provincia));*/

        launch(args);
    }

}
