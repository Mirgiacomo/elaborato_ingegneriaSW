package elaborato_ingegneriaSW.dao;

import com.google.cloud.firestore.Firestore;
import elaborato_ingegneriaSW.utils.FirebaseConnection;

import java.util.List;

public abstract class DaoImpl<T> implements Dao<T> {
    protected Firestore firestore;

    protected DaoImpl() {
        this.firestore = FirebaseConnection.getConnection();
    }

    @Override
    public abstract List getAllItems();

    @Override
    public abstract T getItem(String itemId);

    @Override
    public abstract boolean addItem(T item);

    @Override
    public abstract boolean updateItem(T item);

    @Override
    public abstract boolean deleteItem(T item);
}
