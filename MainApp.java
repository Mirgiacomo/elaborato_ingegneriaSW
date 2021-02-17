package elaborato_ingegneriaSW;

import elaborato_ingegneriaSW.controllers.MainController;
import elaborato_ingegneriaSW.utils.ShowView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;

import java.util.concurrent.ExecutionException;

public class MainApp extends Application {

    public static Boolean isInitLoaded = false;

    @Override
    public void start(Stage stage) throws Exception {
        if (SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_LINUX) {
            stage.getIcons().add(new Image("elaborato_ingegneriaSW/resources/img/warning_symbol.svg.png"));
        } else if (SystemUtils.IS_OS_MAC) {
            java.net.URL iconURL = getClass().getResource("/elaborato_ingegneriaSW/resources/img/warning_symbol.svg.png");
            if (iconURL != null) {
                //com.apple.eawt.Application.getApplication().setDockIconImage(new ImageIcon(iconURL, "icon").getImage());
            }
        }

        ShowView showView = new ShowView();
        FXMLLoader loader = showView.getLoader("Main.fxml");

        Parent view = loader.load();
        MainController controller = loader.getController();
        controller.loadView();

        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        launch(args);
    }

}
