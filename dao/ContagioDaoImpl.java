package elaborato_ingegneriaSW.dao;

import elaborato_ingegneriaSW.models.Contagio;

import java.util.concurrent.ExecutionException;

public class ContagioDaoImpl extends DaoImpl<Contagio> {
    @Override
    public Contagio getItem(String itemId) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public Contagio addItem(Contagio item) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public Contagio updateItem(Contagio item) {
        return null;
    }

    @Override
    public boolean deleteItem(Contagio item) {
        return false;
    }
}
