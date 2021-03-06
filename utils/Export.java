package elaborato_ingegneriaSW.utils;

import elaborato_ingegneriaSW.models.Comune;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;
import elaborato_ingegneriaSW.models.Utente;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Export {

    public static void exportData(Set<?> items, String report) throws Exception {
        if (!items.isEmpty()) {
            Writer writer = null;
            Iterator iter = items.iterator();
            // Salvo l'oggetto all'inizio in modo tale da andare a confrontare la classe di appartenenza.
            Object next = iter.next();
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

                // In base all'estensione salvo i dati
                if (file != null) {
                    String fileName = file.getName();
                    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
                    try {
                        writer = new BufferedWriter(new FileWriter(file));

                        switch (fileExtension) {
                            case "txt":
                                if (next instanceof Regione) {
                                    String formatStr = "%-16s %-16s %-16s\n";
                                    String intestazione = String.format(formatStr, "Nome", "Capoluogo", "Superficie");
                                    writer.write(intestazione);
                                    for (Object regione : items) {
                                        Regione item = (Regione) regione;
                                        writer.write(String.format(formatStr, item.getNome(), item.getCapoluogo(), item.getSuperficie()));
                                    }
                                } else if (next instanceof Provincia) {
                                    String formatStr = "%-16s %-16s %-16s\n";
                                    String intestazione = String.format(formatStr, "Nome", "Regione", "Superficie");
                                    writer.write(intestazione);
                                    for (Object provincia : items) {
                                        Provincia item = (Provincia) provincia;
                                        writer.write(String.format(formatStr, item.getNome(), item.getRegione(), item.getSuperficie()));
                                    }
                                } else if (next instanceof Comune) {
                                    String formatStr = "%-16s %-16s %-16s %-16s %-16s %-16s %-16s\n";
                                    String intestazione = String.format(formatStr, "CodiceISTAT", "Nome", "Provincia", "Superficie", "FronteMare", "Territorio", "DataIstituzione");
                                    writer.write(intestazione);
                                    for (Object comune : items) {
                                        Comune item = (Comune) comune;
                                        writer.write(String.format(formatStr, item.getCodiceISTAT(), item.getNome(), item.getProvincia(), item.getSuperficie(),
                                                item.isFronteMare(), item.getTerritorio(), item.getDataIstituzione()));
                                    }
                                } else if (next instanceof Utente) {
                                    String formatStr = "%-16s %-16s %-16s %-16s %-16s %-16s\n";
                                    String intestazione = String.format(formatStr, "Nome", "Cognome", "Username", "CodiceFiscale", "Ruolo", "ComuniAssciati");
                                    writer.write(intestazione);
                                    for (Object utente : items) {
                                        Utente item = (Utente) utente;
                                        String text = item.getNome() + "\t\t\t\t\t" + item.getCognome() + "\t\t\t\t\t" + item.getUsername() + "\t\t\t\t\t" +
                                                item.getCf() + "\t\t\t\t\t" + item.getRuolo() + "\t\t\t\t\t" +
                                                item.getComuniAssociati() + "\n";
                                        writer.write(String.format(formatStr, item.getNome(), item.getCognome(), item.getUsername(), item.getCf(), item.getRuolo(), item.getComuniAssociati()));
                                    }
                                } else if (next != null && report != null && report.equals("MalattieContagiose")) {
                                    String formatStr = "%-16s %-16s %-16s\n";
                                    writer.write(String.format(formatStr, "Malattia", "Contagi", "Decessi"));
                                    for (Object obj : items) {
                                        Map<String, Object> item = (Map<String, Object>) obj;
                                        writer.write(String.format(formatStr, item.get("malattia"), item.get("contagi"), item.get("decessi")));
                                    }
                                } else if (next != null && report != null && report.equals("ContagiComune")) {
                                    String formatStr = "%-16s %-16s\n";
                                    writer.write(String.format(formatStr, "Malattia", "Contagi"));
                                    for (Object obj : items) {
                                        Map<String, Object> item = (Map<String, Object>) obj;
                                        writer.write(String.format(formatStr, item.get("malattia"), item.get("contagi")));
                                    }
                                } else if (next != null && report != null && report.equals("Decessi")) {
                                    String formatStr = "%-16s %-16s\n";
                                    writer.write(String.format(formatStr, "Causa", "Decessi"));
                                    for (Object obj : items) {
                                        Map<String, Object> item = (Map<String, Object>) obj;
                                        writer.write(String.format(formatStr, item.get("causa"), item.get("decessi"), item.get("decessi")));
                                    }
                                }
                                break;
                            case "csv":
                                if (next instanceof Regione) {
                                    String intestazione = "Nome;Capoluogo;Superficie;\n";
                                    writer.write(intestazione);
                                    for (Object regione : items) {
                                        Regione item = (Regione) regione;
                                        String text = item.getNome() + ";" + item.getCapoluogo() + ";" + item.getSuperficie() + ";\n";
                                        writer.write(text);
                                    }
                                } else if (next instanceof Provincia) {
                                    String intestazione = "Nome;Regione;Superficie;\n";
                                    writer.write(intestazione);
                                    for (Object provincia : items) {
                                        Provincia item = (Provincia) provincia;
                                        String text = item.getNome() + ";" + item.getRegione() + ";" + item.getSuperficie() + ";\n";
                                        writer.write(text);
                                    }
                                } else if (next instanceof Comune) {
                                    String intestazione = "CodiceISTAT;Nome;Provincia;Superficie;FronteMare;Territorio;DataIstituzione;\n";
                                    writer.write(intestazione);
                                    for (Object comune : items) {
                                        Comune item = (Comune) comune;
                                        String text = item.getCodiceISTAT() + ";" + item.getNome() + ";" + item.getProvincia() + ";" +
                                                item.getSuperficie() + item.isFronteMare() + ";" + item.getTerritorio().toString() + ";" +
                                                item.getDataIstituzione() + ";\n";
                                        writer.write(text);
                                    }
                                } else if (next instanceof Utente) {
                                    String intestazione = "Nome;Cognome;Username,CodiceFiscale,Ruolo;\n";
                                    writer.write(intestazione);
                                    for (Object utente : items) {
                                        Utente item = (Utente) utente;
                                        String text = item.getNome() + ";" + item.getComuniAssociati() + ";" +
                                                item.getUsername() + ";" + item.getCf() + ";" + item.getRuolo().toString() + ";\n";
                                        writer.write(text);
                                    }
                                } else if (next != null && report != null && report.equals("MalattieContagiose")) {
                                    String intestazione = "Malattia;Contagi;Decessi\n";
                                    writer.write(intestazione);
                                    for (Object obj : items) {
                                        Map<String, Object> item = (Map<String, Object>) obj;
                                        String text = item.get("malattia") + ";" + item.get("contagi") + ";" + item.get("decessi") + ";\n";
                                        writer.write(text);
                                    }
                                } else if (next != null && report != null && report.equals("ContagiComune")) {
                                    String intestazione = "Malattia;Contagi\n";
                                    writer.write(intestazione);
                                    for (Object obj : items) {
                                        Map<String, Object> item = (Map<String, Object>) obj;
                                        String text = item.get("malattia") + ";" + item.get("contagi") + ";\n";
                                        writer.write(text);
                                    }
                                } else if (next != null && report != null && report.equals("Decessi")) {
                                    String intestazione = "Causa;Decessi\n";
                                    writer.write(intestazione);
                                    for (Object obj : items) {
                                        Map<String, Object> item = (Map<String, Object>) obj;
                                        String text = item.get("causa") + ";" + item.get("decessi") + ";\n";
                                        writer.write(text);
                                    }
                                }
                                break;
                            case "xls":
                                Workbook workbook = new HSSFWorkbook();
                                if (next instanceof Regione) {
                                    Sheet sheet = workbook.createSheet("Regione");
                                    String[] intestazione = {"Nome", "Capoluogo", "Superficie"};

                                    // Creo una riga
                                    Row headerRow = sheet.createRow(0);

                                    // Creo le celle
                                    for (int i = 0; i < intestazione.length; i++) {
                                        Cell cell = headerRow.createCell(i);
                                        cell.setCellValue(intestazione[i]);
                                    }
                                    int rowNum = 1;
                                    for (Object regione : items) {
                                        Regione item = (Regione) regione;
                                        Row row = sheet.createRow(rowNum++);

                                        row.createCell(0).setCellValue(item.getNome());
                                        row.createCell(1).setCellValue(item.getCapoluogo());
                                        row.createCell(2).setCellValue(item.getSuperficie());
                                    }

                                    // Resize all columns to fit the content size
                                    for (int i = 0; i < intestazione.length; i++) {
                                        sheet.autoSizeColumn(i);
                                    }
                                } else if (next instanceof Provincia) {
                                    Sheet sheet = workbook.createSheet("Provincia");
                                    String[] intestazione = {"Nome", "Regione", "Superficie"};

                                    // Creo una riga
                                    Row headerRow = sheet.createRow(0);

                                    // Creo le celle
                                    for (int i = 0; i < intestazione.length; i++) {
                                        Cell cell = headerRow.createCell(i);
                                        cell.setCellValue(intestazione[i]);
                                    }
                                    int rowNum = 1;
                                    for (Object provincia : items) {
                                        Provincia item = (Provincia) provincia;
                                        Row row = sheet.createRow(rowNum++);

                                        row.createCell(0).setCellValue(item.getNome());
                                        row.createCell(1).setCellValue(item.getRegione().getNome());
                                        row.createCell(2).setCellValue(item.getSuperficie());
                                    }

                                    // Resize all columns to fit the content size
                                    for (int i = 0; i < intestazione.length; i++) {
                                        sheet.autoSizeColumn(i);
                                    }
                                } else if (next instanceof Comune) {
                                    Sheet sheet = workbook.createSheet("Comune");
                                    String[] intestazione = {"ISTAT", "Nome", "Provincia", "Superficie", "FrontaMare", "Territorio", "DataIstituzione"};

                                    // Creo una riga
                                    Row headerRow = sheet.createRow(0);

                                    // Creo le celle
                                    for (int i = 0; i < intestazione.length; i++) {
                                        Cell cell = headerRow.createCell(i);
                                        cell.setCellValue(intestazione[i]);
                                    }
                                    int rowNum = 1;
                                    for (Object comune : items) {
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
                                    for (int i = 0; i < intestazione.length; i++) {
                                        sheet.autoSizeColumn(i);
                                    }
                                } else if (next instanceof Utente) {
                                    Sheet sheet = workbook.createSheet("Utente");
                                    String[] intestazione = {"Nome", "Cognome", "Username", "Codice Fiscale", "Ruolo"};

                                    // Creo una riga
                                    Row headerRow = sheet.createRow(0);

                                    // Creo le celle
                                    for (int i = 0; i < intestazione.length; i++) {
                                        Cell cell = headerRow.createCell(i);
                                        cell.setCellValue(intestazione[i]);
                                    }
                                    int rowNum = 1;
                                    for (Object utente : items) {
                                        Utente item = (Utente) utente;
                                        Row row = sheet.createRow(rowNum++);

                                        row.createCell(0).setCellValue(item.getNome());
                                        row.createCell(1).setCellValue(item.getCognome());
                                        row.createCell(2).setCellValue(item.getUsername());
                                        row.createCell(3).setCellValue(item.getCf());
                                        row.createCell(4).setCellValue(item.getRuolo().toString());
                                    }

                                    // Resize all columns to fit the content size
                                    for (int i = 0; i < intestazione.length; i++) {
                                        sheet.autoSizeColumn(i);
                                    }
                                } else if (next != null && report != null && report.equals("MalattieContagiose")) {
                                    Sheet sheet = workbook.createSheet("MalattieContagiose");
                                    String[] intestazione = {"Malattia", "Contagi", "Decessi"};

                                    // Creo una riga
                                    Row headerRow = sheet.createRow(0);

                                    // Creo le celle
                                    for (int i = 0; i < intestazione.length; i++) {
                                        Cell cell = headerRow.createCell(i);
                                        cell.setCellValue(intestazione[i]);
                                    }
                                    int rowNum = 1;
                                    for (Object obj : items) {
                                        Map<String, Object> item = (Map<String, Object>) obj;
                                        Row row = sheet.createRow(rowNum++);

                                        row.createCell(0).setCellValue((String) item.get("malattia"));
                                        row.createCell(1).setCellValue((Integer) item.get("contagi"));
                                        row.createCell(2).setCellValue((Integer) item.get("decessi"));
                                    }
                                } else if (next != null && report != null && report.equals("ContagiComune")) {
                                    Sheet sheet = workbook.createSheet("ContagiComune");
                                    String[] intestazione = {"Malattia", "Contagi"};

                                    // Creo una riga
                                    Row headerRow = sheet.createRow(0);

                                    // Creo le celle
                                    for (int i = 0; i < intestazione.length; i++) {
                                        Cell cell = headerRow.createCell(i);
                                        cell.setCellValue(intestazione[i]);
                                    }
                                    int rowNum = 1;
                                    for (Object obj : items) {
                                        Map<String, Object> item = (Map<String, Object>) obj;
                                        Row row = sheet.createRow(rowNum++);

                                        row.createCell(0).setCellValue((String) item.get("malattia"));
                                        row.createCell(1).setCellValue((Integer) item.get("contagi"));
                                    }
                                } else if (next != null && report != null && report.equals("Decessi")) {
                                    Sheet sheet = workbook.createSheet("Decessi");
                                    String[] intestazione = {"Causa", "Decessi"};

                                    // Creo una riga
                                    Row headerRow = sheet.createRow(0);

                                    // Creo le celle
                                    for (int i = 0; i < intestazione.length; i++) {
                                        Cell cell = headerRow.createCell(i);
                                        cell.setCellValue(intestazione[i]);
                                    }
                                    int rowNum = 1;
                                    for (Object obj : items) {
                                        Map<String, Object> item = (Map<String, Object>) obj;
                                        Row row = sheet.createRow(rowNum++);

                                        row.createCell(0).setCellValue((String) item.get("causa"));
                                        row.createCell(1).setCellValue((Integer) item.get("decessi"));
                                    }
                                }
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

    public static void exportImg(PieChart pieChart, String titolo) {
        try {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter PNGExtFilter = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
            fileChooser.getExtensionFilters().add(PNGExtFilter);

            // Mostro la finestra di salvataggio
            Window primaryStage = null;
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                pieChart.setTitle(titolo);
                WritableImage image = pieChart.snapshot(new SnapshotParameters(), null);
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
