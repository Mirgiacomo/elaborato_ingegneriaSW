package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
            DocumentReference provinciaDocument = firestore.document(Objects.requireNonNull(document.get("provincia", String.class)));
            ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();

            if (document.get("causaDecesso", CausaDecesso.class).equals(CausaDecesso.MALATTIA_CONTAGIOSA)) {
                DocumentReference malattiaContagiosaDocument = firestore.document(Objects.requireNonNull(document.get("malattiaContagiosa", String.class)));
                MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();

                DecessoMalattiaContagiosa decesso = new DecessoMalattiaContagiosa();
                decesso.setCausaDecesso(document.get("causaDecesso", CausaDecesso.class));
                decesso.setNumeroMorti(document.get("numeroMorti", Integer.class));
                decesso.setYear(document.get("year", Integer.class));
                decesso.setProvincia(provinciaDao.getItem(provinciaDocument.getId()));
                decesso.setMalattiaContagiosa(malattiaContagiosaDao.getItem(malattiaContagiosaDocument.getId()));
            } else {
                result = document.toObject(Decesso.class);
            }
        }

        return result;
    }

    public List<Decesso> getFilteredItems(Provincia provincia, int year) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = firestore.collection(getCollectionName());
        List<Decesso> result;

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
