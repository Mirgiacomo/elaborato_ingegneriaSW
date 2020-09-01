package elaborato_ingegneriaSW.models;

public class Comune {
    private String codice;
    private String nome;
    private String data_istituzione;
    private double superficie;
    private String territorio; // TODO: definire ENUM Territorio con i tre tipi di territorio
    private boolean mare;
    private Provincia provincia;

    /**
     * @param codice codice ISTAT univoco
     * @param nome nome del comune
     * @param data_istituzione data di istituzione
     * @param superficie superficie in chilometri quadrati
     * @param territorio tipo di territorio
     * @param mare indicazione del fatto che il comune si affacci sul mare
     * @param provincia provincia a cui appartiene il comune
     */
    public Comune(String codice, String nome, String data_istituzione, double superficie, String territorio, boolean mare, Provincia provincia) {
        this.codice = codice;
        this.nome = nome;
        this.data_istituzione = data_istituzione;
        this.superficie = superficie;
        this.territorio = territorio;
        this.mare = mare;
        this.provincia = provincia;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData_istituzione() {
        return data_istituzione;
    }

    public void setData_istituzione(String data_istituzione) {
        this.data_istituzione = data_istituzione;
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

    public boolean isMare() {
        return mare;
    }

    public void setMare(boolean mare) {
        this.mare = mare;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}
