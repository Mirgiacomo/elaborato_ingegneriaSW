package elaborato_ingegneriaSW.models;

public class Regione {
    private String id;
    private String nome;
    private String capoluogo;
    private double superficie;

    /**
     * @param id identificatore della regione
     * @param nome nome regione
     * @param capoluogo capoluogo regione
     * @param superficie superficie della regione in chilometri quadrati
     */
    public Regione(String id, String nome, String capoluogo, double superficie) {
        this.id = id;
        this.nome = nome;
        this.capoluogo = capoluogo;
        this.superficie = superficie;
    }

    public Regione() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCapoluogo() {
        return capoluogo;
    }

    public void setCapoluogo(String capoluogo) {
        this.capoluogo = capoluogo;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }
}