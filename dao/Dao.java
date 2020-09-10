package elaborato_ingegneriaSW.dao;

import java.util.List;

public interface Dao<T> {
    public List<T> getAllItems();
    public T getItem(String itemId);
    public boolean addItem(T item);
    public boolean updateItem(T item);
    public boolean deleteItem(T item);
}
