package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.models.*;

import java.util.*;
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
            result.setTerritorio(document.get("territorio", Territorio.class));
            result.setFronteMare(document.get("fronteMare", Boolean.class));
            result.setProvincia(provinciaDao.getItem(provinciaDocument.getId()));
        }

        return result;
    }

    public Set<Comune> getComuniByProvincia(Provincia provincia) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = firestore.collection(collectionName).
                whereEqualTo("provincia", ProvinciaDaoImpl.getCollectionName() + "/" + provincia.generateId()).get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        Set<Comune> result = new HashSet<>();

        for (QueryDocumentSnapshot document : documents) {
            result.add(getItem(document));
        }

        return result;
    }

    public Set<Comune> getComuniByRegione(Regione regione) throws ExecutionException, InterruptedException {
        ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();
        Set<Provincia> province = provinciaDao.getProvinceByRegione(regione);

        Set<Comune> comuni;
        Set<Comune> result = new HashSet<>();

        if (!province.isEmpty()) {
            for (Provincia provincia: province) {
                comuni = getComuniByProvincia(provincia);
                if (!comuni.isEmpty()) {
                    result.addAll(comuni);
                }
            }
        }
        return result;
    }

    @Override
    public Comune saveItem(Comune item) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());

        ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

        if (provinciaDao.getItem(item.getProvincia().generateId()) == null) {
            provinciaDao.saveItem(item.getProvincia());
        }

        ApiFuture<WriteResult> writeResult = documentReference.set(item.getFirebaseObject(), SetOptions.merge());

        DocumentSnapshot documentSnapshot = documentReference.get().get();
        return getItem(documentSnapshot.getId());
    }

    @Override
    public boolean deleteItem(Comune item) {
        return false;
    }
}
