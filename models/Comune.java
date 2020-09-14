package elaborato_ingegneriaSW.models;

public class Comune {
    private String codiceISTAT;
    private String nome;
    private String dataIstituzione;
    private double superficie;
    private String territorio;
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
    public Comune(String codiceISTAT, String nome, String dataIstituzione, double superficie, String territorio, boolean fronteMare, Provincia provincia) {
        this.codiceISTAT = codiceISTAT;
        this.nome = nome;
        this.dataIstituzione = dataIstituzione;
        this.superficie = superficie;
        this.territorio = territorio;
        this.fronteMare = fronteMare;
        this.provincia = provincia;
    }

    public Comune() {
    }

    public String getCodice_ISTAT() {
        return codiceISTAT;
    }

    public void setCodice_ISTAT(String codiceISTAT) {
        this.codiceISTAT = codiceISTAT;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData_istituzione() {
        return dataIstituzione;
    }

    public void setData_istituzione(String dataIstituzione) {
        this.dataIstituzione = dataIstituzione;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public String getTerritorio() {
        return territorio;
    }

    public void setTerritorio(String territorio) {
        this.territorio = territorio;
    }

    public boolean isFronte_mare() {
        return fronteMare;
    }

    public void setFronte_mare(boolean fronteMare) {
        this.fronteMare = fronteMare;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}
