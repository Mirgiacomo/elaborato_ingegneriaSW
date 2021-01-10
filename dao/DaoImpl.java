package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.utils.FirebaseConnection;

import java.util.*;
import java.util.concurrent.ExecutionException;

public abstract class DaoImpl<T> implements Dao<T> {
    protected Firestore firestore;

    protected DaoImpl() {
        this.firestore = FirebaseConnection.getConnection();
    }

    @Override
    public Set<T> getAllItems(String collectionName) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = firestore.collection(collectionName).get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        TreeSet<T> result = new TreeSet();

        for (QueryDocumentSnapshot document : documents) {
            result.add(getItem(document));
        }

        return result;
    }

    /**
     * Crea una query di ricerca sul database
     * @param collectionName: nome collezione su database
     * @param conditions: chiave = nome del campo, valore = valore da filtrare
     * @param classType: classe degli oggetti richiesti
     * @return result
     *
     * TODO: provare a sistemare perchè non funziona
     */
    @Override
    public List<T> getItemsByQuery(String collectionName, HashMap<String,T> conditions, Class classType) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = firestore.collection(collectionName);
        Query query = null;
        List<T> result = null;

        for (String key: conditions.keySet()) {
            query = collectionReference.whereEqualTo(key, conditions.get(key));
        }

        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        result = new ArrayList<>();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(getItem(document));
        }

        return result;
    }
}
