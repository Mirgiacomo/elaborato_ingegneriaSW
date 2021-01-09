package elaborato_ingegneriaSW.models;

import elaborato_ingegneriaSW.dao.ComuneDaoImpl;
import elaborato_ingegneriaSW.dao.RegioneDaoImpl;
import elaborato_ingegneriaSW.dao.UtenteDaoImpl;

import java.util.*;

public class Utente extends AbstractTableModel implements Comparable<Utente>{
    private String cognome;
    private String nome;
    private String username;
    private String password;
    private RuoloUtente ruolo;
    private String cf;
    private Set<Comune> comuniAssociati;

    // TODO: aggiungere nel costruttore anche i comuni
    public Utente(String cognome, String nome, String username, String password, RuoloUtente ruolo, String cf, Set<Comune> comuniAssociati) {
        this.cognome = cognome;
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
        this.cf = cf;
        this.comuniAssociati = new HashSet<>();
        if (comuniAssociati != null) {
            for (Comune c: comuniAssociati) {
                this.addComune(c);
            }
        }
    }

    public Utente() {
    }

    /**
     * Ritorna l'id univoco per il record nel database
     * @return username.id
     */
    public String generateId() {
        return (username).toLowerCase();
    }

    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public RuoloUtente getRuolo() {
        return ruolo;
    }
    public void setRuolo(RuoloUtente ruolo) {
        this.ruolo = ruolo;
    }

    public String getCf() {
        return cf;
    }
    public void setCf(String cf) {
        this.cf = cf;
    }

    public void addComune(Comune c) {
        this.comuniAssociati.add(c);
    }

    public Set<Comune> getComuniAssociati() {
        return comuniAssociati;
    }

    public void setComuniAssociati(Set<Comune> comuniAssociati) {
        this.comuniAssociati = comuniAssociati;
    }

   public HashMap<String, Object> getFirebaseObject() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("nome", nome);
        result.put("cognome", cognome);
        result.put("username", username);
        result.put("password", password);
        result.put("cf", cf);
        result.put("ruolo", ruolo);

        // firebase non supporta le collection, vuole le liste
        List<String> comuni = new ArrayList<>();
        if (!comuniAssociati.isEmpty()) {
            for (Comune comune: comuniAssociati) {
                comuni.add(ComuneDaoImpl.getCollectionName() + "/" + comune.generateId());
            }
        }
        result.put("comuniAssociati", comuni);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return Objects.equals(cf, utente.cf);
    }

    @Override
    public int compareTo(Utente other) {
        return username.compareTo(other.getUsername());
    }
}


