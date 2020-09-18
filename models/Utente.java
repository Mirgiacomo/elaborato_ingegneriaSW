package elaborato_ingegneriaSW.models;

import java.util.HashMap;
import java.util.List;

public class Utente {
    private String cognome;
    private String nome;
    private String username;
    private String password;
    private RuoloUtente ruolo;
    private String cf;

    // TODO: aggiungere nel costruttore anche i comuni
    public Utente(String cognome, String nome, String username, String password, RuoloUtente ruolo, String cf) {
        this.cognome = cognome;
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
        this.cf = cf;
//        this.comuniAssociati = comuniAssociati;
    }

    /**
     * Ritorna l'id univoco per il record nel database
     * @return username.id
     */
    public String generateId() {
        return (username).toLowerCase();
    }

    HashMap<String, List> comuniAssociati;

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

    public HashMap<String, List> getComuniAssociati() {
        return comuniAssociati;
    }

    public void setComuniAssociati(HashMap<String, List> comuniAssociati) {
        this.comuniAssociati = comuniAssociati;
    }
}


