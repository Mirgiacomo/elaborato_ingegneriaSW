package elaborato_ingegneriaSW.models;

import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.MalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;

import java.util.HashMap;
import java.util.Objects;

public class Decesso implements Comparable<Decesso>{
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

    /**
     * Ritorna l'id univoco per il record nel database
     * @return year + provincia name
     */
    public String generateId() {
        return year + "_" + provincia.getNome().toLowerCase() + "_" + causaDecesso.name().toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Decesso decesso = (Decesso) o;
        return year == decesso.year &&
                provincia.equals(decesso.provincia) &&
                causaDecesso == decesso.causaDecesso;
    }

    @Override
    public int hashCode() {
        return Objects.hash(provincia.getNome() + year);
    }

    @Override
    public String toString() {
        return provincia.getNome() + " " + year;
    }

    @Override
    public int compareTo(Decesso other) {
        if(year == other.getYear()){
            return provincia.getNome().compareTo(other.getProvincia().getNome());
        }
        return 0;
    }

    public HashMap<String, Object> getFirebaseObject() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("provincia", ProvinciaDaoImpl.getCollectionName() + "/" + provincia.generateId());
        result.put("numeroMorti", numeroMorti);
        result.put("year", year);
        result.put("causaDecesso", causaDecesso);

        return result;
    }
}
