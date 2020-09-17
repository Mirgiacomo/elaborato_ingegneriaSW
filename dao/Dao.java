package elaborato_ingegneriaSW.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface Dao<T> {
    public Set<T> getAllItems(String collectionName) throws ExecutionException, InterruptedException;
    public T getItem(String itemId) throws ExecutionException, InterruptedException;
    public List<T> getItemsByQuery(String collectionName, HashMap<String,T> conditions, Class classType) throws ExecutionException, InterruptedException;
    public T addItem(T item) throws ExecutionException, InterruptedException;
    public T updateItem(T item);
    public boolean deleteItem(T item);
}
