package elaborato_ingegneriaSW.utils;

import elaborato_ingegneriaSW.models.Comune;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

public class Export {

    public static void exportData(Set<Object> items) throws Exception {
        if(!items.isEmpty()) {
            Writer writer = null;
            Iterator iter = items.iterator();
            try {
                FileChooser fileChooser = new FileChooser();

                //Imposto l'estensione
                FileChooser.ExtensionFilter txtExtFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                FileChooser.ExtensionFilter csvExtFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
                FileChooser.ExtensionFilter xlsxExtFilter = new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
                fileChooser.getExtensionFilters().add(txtExtFilter);
                fileChooser.getExtensionFilters().add(csvExtFilter);
                fileChooser.getExtensionFilters().add(xlsxExtFilter);

                // Mostro la finestra di salvataggio
                Window primaryStage = null;
                File file = fileChooser.showSaveDialog(primaryStage);
                String fileName = file.getName();

                // In base all'estensione salvo i dati
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
                if (file != null) {
                    try {
                        writer = new BufferedWriter(new FileWriter(file));

                        switch (fileExtension) {
                            case "txt":
                                //TODO: salvataggio TXT
                                break;
                            case "csv":
                                //TODO: salvataggio CSV
                                if(iter.next() instanceof Regione){
                                    for (Object regione : items) {
                                        Regione item = (Regione)regione;
                                        String text = item.getNome() + ";" + item.getCapoluogo() + ";" + item.getSuperficie() + ";\n";
                                        writer.write(text);
                                    }
                                } else if(iter.next() instanceof Provincia){
                                    for (Object provincia : items) {
                                        Provincia item = (Provincia) provincia;
                                        String text = item.getNome() + ";" + item.getRegione() + ";" + item.getSuperficie() + ";\n";
                                        writer.write(text);
                                    }
                                } else if(iter.next() instanceof Comune){
                                    for (Object comune : items) {
                                        Comune item = (Comune) comune;
                                        String text = item.getCodiceISTAT() + ";" + item.getNome() + ";" + item.getProvincia() + ";" +
                                                item.getSuperficie() + item.isFronteMare() + ";" + item.getTerritorio() + ";" +
                                                item.getDataIstituzione()+ ";\n";
                                        writer.write(text);
                                    }
                                }
                                break;
                            case "xlsx":
                                //TODO: salvataggio XLSX
                                break;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        writer.flush();
                        writer.close();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
