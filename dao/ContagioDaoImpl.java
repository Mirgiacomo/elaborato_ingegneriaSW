package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.models.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class ContagioDaoImpl extends DaoImpl<Contagio> {
    private static final String collectionName = "contagi";

    public ContagioDaoImpl() {
        super();
    }

    public static String getCollectionName() {
        return collectionName;
    }

    @Override
    public Contagio getItem(String itemId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(itemId);
        DocumentSnapshot document = documentReference.get().get();

        return getItem(document);
    }

    @Override
    public Contagio getItem(DocumentSnapshot document) throws ExecutionException, InterruptedException, NullPointerException {
        Contagio result = null;
        if (document.exists()) {
            ComuneDaoImpl comuneDao = new ComuneDaoImpl();
            MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();

            DocumentReference comuneDocument = firestore.document(Objects.requireNonNull(document.get("comune", String.class)));
            DocumentReference malattiaContagiosaDocument = firestore.document(Objects.requireNonNull(document.get("malattiaContagiosa", String.class)));

            Comune comune = comuneDao.getItem(comuneDocument.getId());
            MalattiaContagiosa malattiaContagiosa = malattiaContagiosaDao.getItem(malattiaContagiosaDocument.getId());
            int numeroMedicoBase = document.get("numeroMedicoBase", Integer.class);
            int numeroTerapiaIntensiva = document.get("numeroTerapiaIntensiva", Integer.class);
            int week = document.get("week", Integer.class);
            int year = document.get("year", Integer.class);
            Map<String, Integer> complications = (Map<String, Integer>) document.get("complications");

            result = new Contagio(comune, numeroTerapiaIntensiva, numeroMedicoBase, malattiaContagiosa, week, year, complications);
        }

        return result;
    }

    public Set<Contagio> getFilteredItems(Comune comune, int week, int year) throws ExecutionException, InterruptedException {
        Set<Contagio> result = new HashSet<>();

        CollectionReference collectionReference = firestore.collection(getCollectionName());

        Query query = collectionReference.whereEqualTo("comune", ComuneDaoImpl.getCollectionName() + "/" + comune.generateId())
                .whereEqualTo("week", week)
                .whereEqualTo("year", year);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(getItem(document));
        }

        return result;
    }

    public Set<Contagio> getFilteredItems(Comune comune, int year) throws ExecutionException, InterruptedException {
        Set<Contagio> result = new HashSet<>();

        CollectionReference collectionReference = firestore.collection(getCollectionName());

        Query query = collectionReference.whereEqualTo("comune", ComuneDaoImpl.getCollectionName() + "/" + comune.generateId())
                .whereEqualTo("year", year);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(getItem(document));
        }

        return result;
    }

    public Set<Contagio> getFilteredItems(Provincia provincia, int year) throws ExecutionException, InterruptedException {
        ComuneDaoImpl comuneDao = new ComuneDaoImpl();
        Set<Comune> comuni = comuneDao.getComuniByProvincia(provincia);

        List<String> filter = new ArrayList<>();
        Set<Contagio> result = new HashSet<>();

        if (!comuni.isEmpty()) {
            for (Comune comune : comuni) {
                filter.add(ComuneDaoImpl.getCollectionName() + "/" + comune.generateId());
            }

            Query query = firestore.collection(getCollectionName()).whereEqualTo("year", year).whereIn("comune", filter);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                result.add(getItem(document));
            }
        }

        return result;
    }

    public Set<Contagio> getFilteredItems(Regione regione, int year) throws ExecutionException, InterruptedException {
        ComuneDaoImpl comuneDao = new ComuneDaoImpl();
        Set<Comune> comuni = comuneDao.getComuniByRegione(regione);

        List<String> filter = new ArrayList<>();
        Set<Contagio> result = new HashSet<>();

        if (!comuni.isEmpty()) {
            for (Comune comune : comuni) {
                filter.add(ComuneDaoImpl.getCollectionName() + "/" + comune.generateId());
            }

            Query query = firestore.collection(getCollectionName()).whereEqualTo("year", year).whereIn("comune", filter);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                result.add(getItem(document));
            }
        }

        return result;
    }

    public Set<Contagio> getFilteredItems(int year) throws ExecutionException, InterruptedException {
        Query query = firestore.collection(getCollectionName()).whereEqualTo("year", year);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        Set<Contagio> result = new HashSet<>();

        for (QueryDocumentSnapshot document : documents) {
            result.add(getItem(document));
        }

        return result;
    }

    @Override
    public Contagio saveItem(Contagio item) throws ExecutionException, InterruptedException {
        Contagio result = null;

        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());

        MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();
        ComuneDaoImpl comuneDao = new ComuneDaoImpl();

        if (malattiaContagiosaDao.getItem(item.getMalattiaContagiosa().generateId()) != null
                && comuneDao.getItem(item.getComune().generateId()) != null) {
            documentReference.set(item.getFirebaseObject(), SetOptions.merge());

            DocumentSnapshot documentSnapshot = documentReference.get().get();
            result = getItem(documentSnapshot.getId());
        }
        return result;
    }

    @Override
    public boolean deleteItem(Contagio item) {
        return false;
    }
}
