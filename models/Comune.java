package elaborato_ingegneriaSW.models;

import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

public class Comune implements Comparable<Comune> {
    private String codiceISTAT;
    private String nome;
    private LocalDate dataIstituzione;
    private double superficie;
    private Territorio territorio;
    private boolean fronteMare;
    private Provincia provincia;

    /**
     * @param codiceISTAT     codice ISTAT univoco
     * @param nome            nome del comune
     * @param dataIstituzione data di istituzione
     * @param superficie      superficie in chilometri quadrati
     * @param territorio      tipo di territorio
     * @param fronteMare      indicazione del fatto che il comune si affacci sul mare
     * @param provincia       provincia a cui appartiene il comune
     */
    public Comune(String codiceISTAT, String nome, LocalDate dataIstituzione, double superficie, Territorio territorio, boolean fronteMare, Provincia provincia) {
        this.codiceISTAT = codiceISTAT;
        this.nome = nome;
        this.dataIstituzione = dataIstituzione;
        this.superficie = superficie;
        this.territorio = territorio;
        this.fronteMare = fronteMare;
        this.provincia = provincia;
    }

    public String getCodiceISTAT() {
        return codiceISTAT;
    }

    public void setCodiceISTAT(String codiceISTAT) {
        this.codiceISTAT = codiceISTAT;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataIstituzione() {
        return dataIstituzione;
    }

    public void setDataIstituzione(LocalDate dataIstituzione) {
        this.dataIstituzione = dataIstituzione;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public Territorio getTerritorio() {
        return territorio;
    }

    public void setTerritorio(Territorio territorio) {
        this.territorio = territorio;
    }

    public boolean isFronteMare() {
        return fronteMare;
    }

    public void setFronteMare(boolean fronteMare) {
        this.fronteMare = fronteMare;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    /**
     * Ritorna l'id univoco per il record nel database
     * @return nome_regione.id
     */
    public String generateId() {
        return (codiceISTAT).toLowerCase();
    }

    public HashMap<String, Object> getFirebaseObject() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("codiceISTAT", codiceISTAT);
        result.put("nome", nome);
        result.put("dataIstituzione", dataIstituzione.toString());
        result.put("superficie", superficie);
        result.put("territorio", territorio);
        result.put("fronteMare", fronteMare);
        result.put("provincia", ProvinciaDaoImpl.getCollectionName() + "/" + provincia.generateId());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comune comune = (Comune) o;
        return Objects.equals(codiceISTAT, comune.codiceISTAT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceISTAT);
    }

    @Override
    public int compareTo(Comune other) {
        return codiceISTAT.compareTo(other.getCodiceISTAT());
    }

    @Override
    public String toString() {
        return nome;
    }
}
