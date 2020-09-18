package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import elaborato_ingegneriaSW.models.Comune;

import elaborato_ingegneriaSW.models.Territorio;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ComuneDaoImpl extends DaoImpl<Comune> {
    private static final String collectionName = "comuni";

    public ComuneDaoImpl() {
        super();
    }

    public static String getCollectionName() {
        return collectionName;
    }

    @Override
    public Comune getItem(String itemId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(itemId);
        DocumentSnapshot document = documentReference.get().get();

        return getItem(document);
    }

    @Override
    public Comune getItem(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        Comune result = null;
        if (document.exists()) {
            result = new Comune();
            DocumentReference provinciaDocument = firestore.document(Objects.requireNonNull(document.get("provincia", String.class)));
            ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

            result.setCodiceISTAT(document.get("codiceISTAT", String.class));
            result.setNome(document.get("nome", String.class));
            result.setDataIstituzione(document.get("dataIstituzione", String.class));
            result.setSuperficie(document.get("superficie", Double.class));
            result.setTerritorio(Territorio.valueOf(document.get("territorio", String.class)));
            result.setFronteMare(document.get("fronteMare", Boolean.class));
            result.setProvincia(provinciaDao.getItem(provinciaDocument.getId()));
        }

        return result;
    }

    @Override
    public Comune addItem(Comune item) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());

        ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

        if (provinciaDao.getItem(item.getProvincia().generateId()) == null) {
            provinciaDao.addItem(item.getProvincia());
        }

        ApiFuture<WriteResult> writeResult = documentReference.set(item.getFirebaseObject());

        DocumentSnapshot documentSnapshot = documentReference.get().get();
        return getItem(documentSnapshot.getId());
    }

    @Override
    public Comune updateItem(Comune item) {
        return null;
    }

    @Override
    public boolean deleteItem(Comune item) {
        return false;
    }
}
