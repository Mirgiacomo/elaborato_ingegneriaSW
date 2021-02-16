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

}
