package elaborato_ingegneriaSW.models;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Contagio {
    private Comune comune;
    private int numeroTerapiaIntensiva;
    private int numeroMedicoBase;
    private MalattiaContagiosa malattiaContagiosa;

    // identifica univocamente la settimana in base all'anno
    private int week;
    private int year;

    /**
     *
     * @param comune
     * @param numeroTerapiaIntensiva numero di pazienti in terapia intensiva
     * @param numeroMedicoBase       numero di pazienti in cura presso il medico di base
     * @param malattiaContagiosa
     * @param week                   numero della settimana nell'anno scelto
     * @param year
     */
    public Contagio(Comune comune, int numeroTerapiaIntensiva, int numeroMedicoBase, MalattiaContagiosa malattiaContagiosa, int week, int year) {
        this.comune = comune;
        this.numeroTerapiaIntensiva = numeroTerapiaIntensiva;
        this.numeroMedicoBase = numeroMedicoBase;
        this.malattiaContagiosa = malattiaContagiosa;
        this.week = week;
        this.year = year;
    }

    /**
     *
     * @param comune
     * @param numeroTerapiaIntensiva numero di pazienti in terapia intensiva
     * @param numeroMedicoBase       numero di pazienti in cura presso il medico di base
     * @param malattiaContagiosa
     * @param date                   data di riferimento per la settimana
     */
    public Contagio(Comune comune, int numeroTerapiaIntensiva, int numeroMedicoBase, MalattiaContagiosa malattiaContagiosa, LocalDate date) {
        this.comune = comune;
        this.numeroTerapiaIntensiva = numeroTerapiaIntensiva;
        this.numeroMedicoBase = numeroMedicoBase;
        this.malattiaContagiosa = malattiaContagiosa;

        this.week = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        this.year = date.getYear();
    }

    public Contagio() { }

    public Comune getComune() {
        return comune;
    }

    public void setComune(Comune comune) {
        this.comune = comune;
    }

    public int getNumeroTerapiaIntensiva() {
        return numeroTerapiaIntensiva;
    }

    public void setNumeroTerapiaIntensiva(int numeroTerapiaIntensiva) {
        this.numeroTerapiaIntensiva = numeroTerapiaIntensiva;
    }

    public int getNumeroMedicoBase() {
        return numeroMedicoBase;
    }

    public void setNumeroMedicoBase(int numeroMedicoBase) {
        this.numeroMedicoBase = numeroMedicoBase;
    }

    public MalattiaContagiosa getMalattiaContagiosa() {
        return malattiaContagiosa;
    }

    public void setMalattiaContagiosa(MalattiaContagiosa malattiaContagiosa) {
        this.malattiaContagiosa = malattiaContagiosa;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
