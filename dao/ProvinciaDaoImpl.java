package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ProvinciaDaoImpl extends DaoImpl<Provincia> {
    private static final String collectionName = "province";

    public ProvinciaDaoImpl() {
        super();
    }

    public static String getCollectionName() {
        return collectionName;
    }

    @Override
    public Provincia getItem(String itemId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(itemId);
        DocumentSnapshot document = documentReference.get().get();

        return getItem(document);
    }

    @Override
    public Provincia getItem(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        Provincia result = new Provincia();
        if (document.exists()) {
            DocumentReference regioneDocument = firestore.document(Objects.requireNonNull(document.get("regione", String.class)));
            RegioneDaoImpl regioneDao = new RegioneDaoImpl();

            result.setNome(document.get("nome", String.class));
            result.setSuperficie(document.get("superficie", Double.class));
            result.setRegione(regioneDao.getItem(regioneDocument.getId()));
        }

        return result;
    }

    @Override
    public Provincia addItem(Provincia item) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());

        RegioneDaoImpl regioneDao = new RegioneDaoImpl();

        if (regioneDao.getItem(item.getRegione().generateId()) == null) {
            regioneDao.addItem(item.getRegione());
        }

        ApiFuture<WriteResult> writeResult = documentReference.set(item.getFirebaseObject());

        DocumentSnapshot documentSnapshot = documentReference.get().get();
        return getItem(documentSnapshot.getId());
    }

    @Override
    public Provincia updateItem(Provincia item) {
        return null;
    }

    @Override
    public boolean deleteItem(Provincia item) {
        return false;
    }
}
