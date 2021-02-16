package elaborato_ingegneriaSW.utils;

import elaborato_ingegneriaSW.models.Utente;

public class LoggedUser {
    private static Utente loggedUser = null;

    private LoggedUser() {}

    public static Utente getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(Utente utente) {
        loggedUser = utente;
    }
}
