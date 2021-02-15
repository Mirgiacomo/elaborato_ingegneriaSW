package elaborato_ingegneriaSW.models;

import elaborato_ingegneriaSW.dao.RegioneDaoImpl;

import java.util.HashMap;
import java.util.Objects;

public class Provincia implements Comparable<Provincia> {
    private String nome;
    private double superficie;
    private Regione regione;

    /**
     * @param nome nome della provincia
     * @param superficie superficie della provincia in chilometri quadrati
     * @param regione regione a cui appartiene la provincia
     */
    public Provincia(String nome, double superficie, Regione regione) {
        this.nome = nome;
        this.superficie = superficie;
        this.regione = regione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public Regione getRegione() {
        return regione;
    }

    public void setRegione(Regione regione) {
        this.regione = regione;
    }

    /**
     * Ritorna l'id univoco per il record nel database
     * @return nome_regione.id
     */
    public String generateId() {
        return (nome + "_" + regione.generateId()).toLowerCase();
    }

    public HashMap<String, Object> getFirebaseObject() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("nome", nome);
        result.put("superficie", superficie);
        result.put("regione", RegioneDaoImpl.getCollectionName() + "/" + regione.generateId());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provincia provincia = (Provincia) o;
        return Objects.equals(nome, provincia.nome) &&
                Objects.equals(regione, provincia.regione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, regione);
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int compareTo(Provincia other) {
        return nome.compareTo(other.getNome());
    }
}
