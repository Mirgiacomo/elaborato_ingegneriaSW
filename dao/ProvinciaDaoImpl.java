package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.models.Comune;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.Regione;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
        Provincia result = null;
        if (document.exists()) {
            result = new Provincia();
            DocumentReference regioneDocument = firestore.document(Objects.requireNonNull(document.get("regione", String.class)));
            RegioneDaoImpl regioneDao = new RegioneDaoImpl();

            result.setNome(document.get("nome", String.class));
            result.setSuperficie(document.get("superficie", Double.class));
            result.setRegione(regioneDao.getItem(regioneDocument.getId()));
        }

        return result;
    }

    public Set<Provincia> getProvinceByRegione(Regione regione) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = firestore.collection(collectionName).
                whereEqualTo("regione", RegioneDaoImpl.getCollectionName() + "/" + regione.generateId()).get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        Set<Provincia> result = new HashSet<>();

        for (QueryDocumentSnapshot document : documents) {
            result.add(getItem(document));
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

    public Set<Provincia> getProvinceByRegione(Regione regione) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = firestore.collection(collectionName).
                whereEqualTo("regione", RegioneDaoImpl.getCollectionName() + "/" + regione.generateId()).get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        Set<Provincia> result = new HashSet<>();

        for (QueryDocumentSnapshot document : documents) {
            result.add(getItem(document));
        }

        return result;
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
