package elaborato_ingegneriaSW.models;

public class Decesso {
    private Provincia provincia;
    private int year;
    private int numeroMorti;

    private CausaDecesso causaDecesso;

    /**
     *
     * @param provincia
     * @param year
     * @param numeroMorti
     * @param causaDecesso
     */
    public Decesso(Provincia provincia, int year, int numeroMorti, CausaDecesso causaDecesso) {
        this.provincia = provincia;
        this.year = year;
        this.numeroMorti = numeroMorti;
        this.causaDecesso = causaDecesso;
    }

    public Decesso() { }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumeroMorti() {
        return numeroMorti;
    }

    public void setNumeroMorti(int numeroMorti) {
        this.numeroMorti = numeroMorti;
    }

    public CausaDecesso getCausaDecesso() {
        return causaDecesso;
    }

    public void setCausaDecesso(CausaDecesso causaDecesso) {
        this.causaDecesso = causaDecesso;
    }
}
