package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import elaborato_ingegneriaSW.models.Regione;

import java.util.concurrent.ExecutionException;


public class RegioneDaoImpl extends DaoImpl<Regione> {
    private static final String collectionName = "regioni";

    public RegioneDaoImpl() {
        super();
    }

    public static String getCollectionName() {
        return collectionName;
    }

    @Override
    public Regione getItem(String itemId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(itemId);
        DocumentSnapshot document = documentReference.get().get();

        Regione result = null;
        if (document.exists()) {
            result = document.toObject(Regione.class);
        }

        return result;
    }

    @Override
    public Regione addItem(Regione item) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());
        ApiFuture<WriteResult> writeResult = documentReference.set(item, SetOptions.merge());

        DocumentSnapshot documentSnapshot = documentReference.get().get();
        return getItem(documentSnapshot.getId());
    }

    @Override
    public Regione updateItem(Regione item) {
        return null;
    }

    @Override
    public boolean deleteItem(Regione item) {
        return false;
    }
}
