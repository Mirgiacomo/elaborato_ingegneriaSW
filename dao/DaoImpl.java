package elaborato_ingegneriaSW.dao;

import java.util.List;

public abstract class DaoImpl<T> implements Dao<T> {
    @Override
    public List getAllItems() {
        return null;
    }

    @Override
    public abstract T getItem(String itemId);

    @Override
    public abstract boolean addItem(Object item);

    @Override
    public boolean updateItem(Object item) {
        return false;
    }

    @Override
    public boolean deleteItem(Object item) {
        return false;
    }

    @Override
    public boolean updateSource() {
        return false;
    }
}
