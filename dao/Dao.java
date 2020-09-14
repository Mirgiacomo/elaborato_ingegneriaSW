package elaborato_ingegneriaSW.dao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface Dao<T> {
    public List<T> getAllItems();
    public T getItem(String itemId) throws ExecutionException, InterruptedException;
    public boolean addItem(T item);
    public boolean updateItem(T item);
    public boolean deleteItem(T item);
}
