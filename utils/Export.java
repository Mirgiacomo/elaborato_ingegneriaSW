package elaborato_ingegneriaSW.utils;

import elaborato_ingegneriaSW.models.Comune;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

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
                FileChooser.ExtensionFilter xlsExtFilter = new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls");
                fileChooser.getExtensionFilters().add(txtExtFilter);
                fileChooser.getExtensionFilters().add(csvExtFilter);
                fileChooser.getExtensionFilters().add(xlsExtFilter);

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
                                if(iter.next() instanceof Regione){
                                    String intestazione = "Nome\t\t\t\t\tCapoluogo\t\t\t\t\tSuperficie\n";
                                    writer.write(intestazione);
                                    for (Object regione : items) {
                                        Regione item = (Regione)regione;
                                        String text = item.getNome() + "\t\t\t\t\t" + item.getCapoluogo() + "\t\t\t\t\t" + item.getSuperficie() + "\n";
                                        writer.write(text);
                                    }
                                } else if(iter.next() instanceof Provincia){
                                    String intestazione = "Nome\t\t\t\t\tRegione\t\t\t\t\tSuperficie\n";
                                    writer.write(intestazione);
                                    for (Object provincia : items) {
                                        Provincia item = (Provincia) provincia;
                                        String text = item.getNome() + "\t\t\t\t\t" + item.getRegione() + "\t\t\t\t\t" + item.getSuperficie() + "\n";
                                        writer.write(text);
                                    }
                                } else if(iter.next() instanceof Comune){
                                    String intestazione = "CodiceISTAT\t\t\t\t\tNome\t\t\t\t\tProvincia\t\t\t\t\tSuperficie\t\t\t\t\tFronteMare\t\t\t\t\tTerritorio\t\t\t\t\tDataIstituzione;\n";
                                    writer.write(intestazione);
                                    for (Object comune : items) {
                                        Comune item = (Comune) comune;
                                        String text = item.getCodiceISTAT() + "\t\t\t\t\t" + item.getNome() + "\t\t\t\t\t" + item.getProvincia() + "\t\t\t\t\t" +
                                                item.getSuperficie() + "\t\t\t\t\t" +   item.isFronteMare() + "\t\t\t\t\t" + item.getTerritorio() + "\t\t\t\t\t" +
                                                item.getDataIstituzione()+ "\n";
                                        writer.write(text);
                                    }
                                }
                                break;
                            case "csv":
                                if(iter.next() instanceof Regione){
                                    String intestazione = "Nome;Capoluogo;Superficie;\n";
                                    writer.write(intestazione);
                                    for (Object regione : items) {
                                        Regione item = (Regione)regione;
                                        String text = item.getNome() + ";" + item.getCapoluogo() + ";" + item.getSuperficie() + ";\n";
                                        writer.write(text);
                                    }
                                } else if(iter.next() instanceof Provincia){
                                    String intestazione = "Nome;Regione;Superficie;\n";
                                    writer.write(intestazione);
                                    for (Object provincia : items) {
                                        Provincia item = (Provincia) provincia;
                                        String text = item.getNome() + ";" + item.getRegione() + ";" + item.getSuperficie() + ";\n";
                                        writer.write(text);
                                    }
                                } else if(iter.next() instanceof Comune){
                                    String intestazione = "CodiceISTAT;Nome;Provincia;Superficie;FronteMare;Territorio;DataIstituzione;\n";
                                    writer.write(intestazione);
                                    for (Object comune : items) {
                                        Comune item = (Comune) comune;
                                        String text = item.getCodiceISTAT() + ";" + item.getNome() + ";" + item.getProvincia() + ";" +
                                                item.getSuperficie() + item.isFronteMare() + ";" + item.getTerritorio() + ";" +
                                                item.getDataIstituzione()+ ";\n";
                                        writer.write(text);
                                    }
                                }
                                break;
                            case "xls":
                                //TODO: salvataggio XLS
                                Workbook workbook = new HSSFWorkbook();
                                if(iter.next() instanceof Regione){
                                    Sheet sheet = workbook.createSheet("Regione");
                                    String[] intestazione = {"Nome", "Capoluogo", "Superficie"};

                                    // Creo una riga
                                    Row headerRow = sheet.createRow(0);

                                    // Creo le celle
                                    for(int i = 0; i < intestazione.length; i++) {
                                        Cell cell = headerRow.createCell(i);
                                        cell.setCellValue(intestazione[i]);
                                    }
                                    int rowNum = 1;
                                    for(Object regione: items) {
                                        Regione item = (Regione) regione;
                                        Row row = sheet.createRow(rowNum++);

                                        row.createCell(0).setCellValue(item.getNome());
                                        row.createCell(1).setCellValue(item.getCapoluogo());
                                        row.createCell(2).setCellValue(item.getSuperficie());
                                    }

                                    // Resize all columns to fit the content size
                                    for(int i = 0; i < intestazione.length; i++) {
                                        sheet.autoSizeColumn(i);
                                    }
                                } else if(iter.next() instanceof Provincia){
                                    Sheet sheet = workbook.createSheet("Provincia");
                                    String[] intestazione = {"Nome", "Regione", "Superficie"};

                                    // Creo una riga
                                    Row headerRow = sheet.createRow(0);

                                    // Creo le celle
                                    for(int i = 0; i < intestazione.length; i++) {
                                        Cell cell = headerRow.createCell(i);
                                        cell.setCellValue(intestazione[i]);
                                    }
                                    int rowNum = 1;
                                    for(Object provincia: items) {
                                        Provincia item = (Provincia) provincia;
                                        Row row = sheet.createRow(rowNum++);

                                        row.createCell(0).setCellValue(item.getNome());
                                        row.createCell(1).setCellValue(item.getRegione().getNome());
                                        row.createCell(2).setCellValue(item.getSuperficie());
                                    }

                                    // Resize all columns to fit the content size
                                    for(int i = 0; i < intestazione.length; i++) {
                                        sheet.autoSizeColumn(i);
                                    }
                                } else if(iter.next() instanceof Comune){
                                    Sheet sheet = workbook.createSheet("Comune");
                                    String[] intestazione = {"ISTAT", "Nome", "Provincia", "Superficie", "FrontaMare", "Territorio", "DataIstituzione"};

                                    // Creo una riga
                                    Row headerRow = sheet.createRow(0);

                                    // Creo le celle
                                    for(int i = 0; i < intestazione.length; i++) {
                                        Cell cell = headerRow.createCell(i);
                                        cell.setCellValue(intestazione[i]);
                                    }
                                    int rowNum = 1;
                                    for(Object comune: items) {
                                        Comune item = (Comune) comune;
                                        Row row = sheet.createRow(rowNum++);

                                        row.createCell(0).setCellValue(item.getCodiceISTAT());
                                        row.createCell(1).setCellValue(item.getNome());
                                        row.createCell(2).setCellValue(item.getProvincia().getNome());
                                        row.createCell(3).setCellValue(item.getSuperficie());
                                        row.createCell(4).setCellValue(item.isFronteMare());
                                        row.createCell(5).setCellValue(item.getTerritorio().toString());
                                        row.createCell(6).setCellValue(item.getDataIstituzione());
                                    }

                                    // Resize all columns to fit the content size
                                    for(int i = 0; i < intestazione.length; i++) {
                                        sheet.autoSizeColumn(i);
                                    }
                                }
                                /**

                                 */
                                // Scrivo l'output sul file
                                FileOutputStream fileOut = new FileOutputStream(file.getAbsoluteFile());
                                workbook.write(fileOut);
                                fileOut.close();
                                workbook.close();
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
