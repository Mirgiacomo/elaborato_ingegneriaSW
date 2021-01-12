package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class DecessoMalattiaContagiosaDaoImpl extends DaoImpl<DecessoMalattiaContagiosa> {
    private static final String collectionName = "decessi_malattie_contagiose";

    public DecessoMalattiaContagiosaDaoImpl() {
        super();
    }

    public static String getCollectionName() {
        return collectionName;
    }

    @Override
    public DecessoMalattiaContagiosa getItem(String itemId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(itemId);
        DocumentSnapshot document = documentReference.get().get();

        return getItem(document);
    }

    @Override
    public DecessoMalattiaContagiosa getItem(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        DecessoMalattiaContagiosa result = null;

        if (document.exists()) {
            DocumentReference provinciaDocument = firestore.document(Objects.requireNonNull(document.get("provincia", String.class)));
            ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();
            DocumentReference malattiaContagiosaDocument = firestore.document(Objects.requireNonNull(document.get("malattiaContagiosa", String.class)));
            MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();

            result = new DecessoMalattiaContagiosa();
            result.setCausaDecesso(document.get("causaDecesso", CausaDecesso.class));
            result.setNumeroMorti(document.get("numeroMorti", Integer.class));
            result.setYear(document.get("year", Integer.class));
            result.setProvincia(provinciaDao.getItem(provinciaDocument.getId()));
            result.setMalattiaContagiosa(malattiaContagiosaDao.getItem(malattiaContagiosaDocument.getId()));
        }

        return result;
    }

    public List<DecessoMalattiaContagiosa> getFilteredItems(Provincia provincia, int year) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = firestore.collection(getCollectionName());
        List<DecessoMalattiaContagiosa> result;

        Query query = collectionReference.whereEqualTo("provincia", ProvinciaDaoImpl.getCollectionName() + "/" + provincia.generateId())
                .whereEqualTo("year", year);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        result = new ArrayList<>();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(getItem(document));
        }

        return result;
    }

    @Override
    public DecessoMalattiaContagiosa addItem(DecessoMalattiaContagiosa item) throws ExecutionException, InterruptedException {
        DecessoMalattiaContagiosa result = null;

        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());
        ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();
        MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();

        if (provinciaDao.getItem(item.getProvincia().generateId()) != null
                && malattiaContagiosaDao.getItem(item.getMalattiaContagiosa().generateId()) != null) {
            documentReference.set(item.getFirebaseObject());

            DocumentSnapshot documentSnapshot = documentReference.get().get();
            result = getItem(documentSnapshot.getId());
        }
        return  result;
    }

    @Override
    public DecessoMalattiaContagiosa updateItem(DecessoMalattiaContagiosa item) {
        return null;
    }

    @Override
    public boolean deleteItem(DecessoMalattiaContagiosa item) {
        return false;
    }
}
