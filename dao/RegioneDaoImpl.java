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

        return getItem(document);
    }

    @Override
    public Regione getItem(DocumentSnapshot document) {
        Regione result = null;
        if (document.exists()) {
            String nome = document.get("nome", String.class);
            double superficie = document.get("superficie", Double.class);
            String capoluogo = document.get("capoluogo", String.class);

            result = new Regione(nome, capoluogo, superficie);
        }

        return result;
    }

    @Override
    public Regione saveItem(Regione item) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());
        ApiFuture<WriteResult> writeResult = documentReference.set(item, SetOptions.merge());

        DocumentSnapshot documentSnapshot = documentReference.get().get();
        return getItem(documentSnapshot.getId());
    }

    @Override
    public boolean deleteItem(Regione item) {
        return false;
    }
}
