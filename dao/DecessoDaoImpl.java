package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import elaborato_ingegneriaSW.models.Decesso;

import java.util.concurrent.ExecutionException;

public class DecessoDaoImpl extends DaoImpl<Decesso> {
    private static final String collectionName = "decessi";

    public DecessoDaoImpl() {
        super();
    }

    public static String getCollectionName() {
        return collectionName;
    }

    @Override
    public Decesso getItem(String itemId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(itemId);
        DocumentSnapshot document = documentReference.get().get();

        return getItem(document);
    }

    @Override
    public Decesso getItem(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        Decesso result = null;
        if (document.exists()) {
            result = document.toObject(Decesso.class);
        }

        return result;
    }

    @Override
    public Decesso addItem(Decesso item) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());
        ApiFuture<WriteResult> writeResult = documentReference.set(item, SetOptions.merge());

        DocumentSnapshot documentSnapshot = documentReference.get().get();
        return getItem(documentSnapshot.getId());
    }

    @Override
    public Decesso updateItem(Decesso item) {
        return null;
    }

    @Override
    public boolean deleteItem(Decesso item) {
        return false;
    }
}
