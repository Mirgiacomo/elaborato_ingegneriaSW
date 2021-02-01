package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.models.*;

import java.util.*;
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
            result = new Decesso();

            DocumentReference provinciaDocument = firestore.document(Objects.requireNonNull(document.get("provincia", String.class)));
            ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

            result.setCausaDecesso(document.get("causaDecesso", CausaDecesso.class));
            result.setNumeroMorti(document.get("numeroMorti", Integer.class));
            result.setYear(document.get("year", Integer.class));
            result.setProvincia(provinciaDao.getItem(provinciaDocument.getId()));
        }

        return result;
    }

    public Set<Decesso> getFilteredItems(Provincia provincia, int year) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = firestore.collection(getCollectionName());
        Query query = collectionReference.whereEqualTo("provincia", ProvinciaDaoImpl.getCollectionName() + "/" + provincia.generateId())
                .whereEqualTo("year", year);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        Set<Decesso> result = new HashSet<>();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(getItem(document));
        }
        return result;
    }

    public Set<Decesso> getFilteredItems(Regione regione, int year) throws ExecutionException, InterruptedException {
        ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();
        Set<Provincia> province = provinciaDao.getProvinceByRegione(regione);

        List<String> filter = new ArrayList<>();
        Set<Decesso> result = new HashSet<>();

        if (!province.isEmpty()) {
            for (Provincia provincia: province) {
                filter.add(ProvinciaDaoImpl.getCollectionName() + "/" + provincia.generateId());
            }

            Query query = firestore.collection(getCollectionName()).whereEqualTo("year", year).whereIn("provincia", filter);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                result.add(getItem(document));
            }
        }

        return result;
    }

    public Set<Decesso> getFilteredItems(int year) throws ExecutionException, InterruptedException {
        Query query = firestore.collection(getCollectionName()).whereEqualTo("year", year);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        Set<Decesso> result = new HashSet<>();

        for (QueryDocumentSnapshot document : documents) {
            result.add(getItem(document));
        }

        return result;
    }

    @Override
    public Decesso saveItem(Decesso item) throws ExecutionException, InterruptedException {
        Decesso result = null;

        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());
        ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

        if (provinciaDao.getItem(item.getProvincia().generateId()) != null) {
            documentReference.set(item.getFirebaseObject());

            DocumentSnapshot documentSnapshot = documentReference.get().get();
            result = getItem(documentSnapshot.getId());
        }
        return  result;
    }

    @Override
    public boolean deleteItem(Decesso item) {
        return false;
    }
}
