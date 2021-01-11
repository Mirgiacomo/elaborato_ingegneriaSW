package elaborato_ingegneriaSW.models;

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
}
