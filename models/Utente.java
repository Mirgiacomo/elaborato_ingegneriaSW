package elaborato_ingegneriaSW.models;

import java.util.HashMap;
import java.util.List;

public class Utente {
    private String username;
    private String password;
    private RuoloUtente tipoUtente;

    HashMap<String, List> permessi;
}
