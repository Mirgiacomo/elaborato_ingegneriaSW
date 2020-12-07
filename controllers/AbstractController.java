package elaborato_ingegneriaSW.controllers;

import elaborato_ingegneriaSW.models.Utente;
import elaborato_ingegneriaSW.utils.ShowView;

public abstract class AbstractController {
    protected ShowView showView = new ShowView();
    protected Utente loggedUser = null;

    public void setLoggedUser(Utente user) {
        this.loggedUser = user;
    }

    public Utente getLoggedUser() {
        return loggedUser;
    }
}
