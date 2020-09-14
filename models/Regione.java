package elaborato_ingegneriaSW.models;

import java.util.Objects;

public class Regione {
    private String nome;
    private String capoluogo;
    private double superficie;

    /**
     * @param nome nome regione
     * @param capoluogo capoluogo regione
     * @param superficie superficie della regione in chilometri quadrati
     */
    public Regione(String nome, String capoluogo, double superficie) {
        this.nome = nome;
        this.capoluogo = capoluogo;
        this.superficie = superficie;
    }

    public Regione() { }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Regione regione = (Regione) o;
        return Objects.equals(nome, regione.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}