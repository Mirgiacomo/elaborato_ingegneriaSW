package elaborato_ingegneriaSW.models;

import java.util.Set;

public class MalattiaContagiosa {
    private String nome;
    private Set<String> complications;

    /**
     *
     * @param nome          nome della malattia
     * @param complications insieme di possibili complicazioni per la malattia
     */
    public MalattiaContagiosa(String nome, Set<String> complications) {
        this.nome = nome;
        this.complications = complications;
    }

    public MalattiaContagiosa(String nome) {
        this(nome, null);
    }

    public MalattiaContagiosa() { }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<String> getComplications() {
        return complications;
    }

    public void setComplications(Set<String> complications) {
        this.complications = complications;
    }

    public void addComplication(String complication) {
        this.complications.add(complication);
    }
}
