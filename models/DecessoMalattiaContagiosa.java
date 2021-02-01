package elaborato_ingegneriaSW.models;

import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.MalattiaContagiosaDaoImpl;
import elaborato_ingegneriaSW.dao.ProvinciaDaoImpl;

import java.util.HashMap;
import java.util.Objects;

public class DecessoMalattiaContagiosa extends Decesso {
    private MalattiaContagiosa malattiaContagiosa;

    /**
     *
     * @param provincia
     * @param year
     * @param numeroMorti
     * @param malattiaContagiosa
     */
    public DecessoMalattiaContagiosa(Provincia provincia, int year, int numeroMorti, MalattiaContagiosa malattiaContagiosa) {
        super(provincia, year, numeroMorti, CausaDecesso.MALATTIA_CONTAGIOSA);
        this.malattiaContagiosa = malattiaContagiosa;
    }

    public DecessoMalattiaContagiosa() {
    }

    public MalattiaContagiosa getMalattiaContagiosa() {
        return malattiaContagiosa;
    }

    public void setMalattiaContagiosa(MalattiaContagiosa malattiaContagiosa) {
        this.malattiaContagiosa = malattiaContagiosa;
    }

    @Override
    public String generateId() {
        return getYear() + "_" + getProvincia().getNome().toLowerCase() + "_" + malattiaContagiosa.generateId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DecessoMalattiaContagiosa that = (DecessoMalattiaContagiosa) o;
        return Objects.equals(malattiaContagiosa, that.malattiaContagiosa) &&
                getYear() == that.getYear() &&
                getProvincia().equals(that.getProvincia());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), malattiaContagiosa);
    }

    public HashMap<String, Object> getFirebaseObject() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("provincia", ProvinciaDaoImpl.getCollectionName() + "/" + getProvincia().generateId());
        result.put("numeroMorti", getNumeroMorti());
        result.put("year", getYear());
        result.put("causaDecesso", getCausaDecesso());
        result.put("malattiaContagiosa", MalattiaContagiosaDaoImpl.getCollectionName() + "/" + malattiaContagiosa.generateId());

        return result;
    }
}
