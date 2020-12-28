package elaborato_ingegneriaSW.models;

import java.util.HashSet;
import java.util.Set;

public class MalattiaContagiosa implements Comparable<MalattiaContagiosa> {
    private String nome;
    private Set<String> complications;

    /**
     *
     * @param nome          nome della malattia
     * @param complications insieme di possibili complicazioni per la malattia
     */
    public MalattiaContagiosa(String nome, Set<String> complications) {
        this.nome = nome;
        this.complications = new HashSet<>();
        if (complications != null) {
            for (String c: complications) {
                this.addComplication(c);
            }
        }
    }

    public MalattiaContagiosa(String nome) {
        this(nome, null);
    }

    public MalattiaContagiosa() {
        this(null, null);
    }

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

    @Override
    public int compareTo(MalattiaContagiosa other) {
        return nome.compareTo(other.getNome());
    }

    @Override
    public String toString() {
        String s = nome;

        if (!complications.isEmpty()) {
            s += ": {";
            for (String c: complications) {
                s += c + ", ";
            }
            s = s.substring(0, s.length()-2);
            s += "}";
        }
        return s;
    }
}
