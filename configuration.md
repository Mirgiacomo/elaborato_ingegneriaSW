# CONFIGURAZIONE INTELLIJ IDEA

In ``run configuration`` aggiungere queste righe in VM OPTION:

    --module-path
    /Users/davide/Desktop/universita/javafx-sdk-14.0.2.1/lib
    --add-modules
    javafx.controls,javafx.fxml
    --add-exports
    javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED
    --add-exports
    javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED
    --add-exports
    javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED
    --add-exports
    javafx.base/com.sun.javafx.binding=ALL-UNNAMED
    --add-exports
    javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED
    --add-exports
    javafx.base/com.sun.javafx.event=ALL-UNNAMED
    --add-opens=java.base/java.lang.reflect=ALL-UNNAMED
    -Dorg.slf4j.simpleLogger.defaultLogLevel=info
    
Aggiungere la libreria slf4j-simple