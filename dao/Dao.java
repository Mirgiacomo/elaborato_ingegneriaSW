package elaborato_ingegneriaSW.dao;

import com.google.cloud.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface Dao<T> {
    Set<T> getAllItems(String collectionName) throws ExecutionException, InterruptedException;
    T getItem(String itemId) throws ExecutionException, InterruptedException;
    T getItem(DocumentSnapshot document) throws ExecutionException, InterruptedException;
    T saveItem(T item) throws ExecutionException, InterruptedException;
    boolean deleteItem(T item);
}
