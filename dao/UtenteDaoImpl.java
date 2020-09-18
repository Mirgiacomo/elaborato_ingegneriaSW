package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.models.Utente;

import java.util.concurrent.ExecutionException;

public class UtenteDaoImpl extends DaoImpl<Utente>{
    private static final String collectionName = "users";

    public UtenteDaoImpl() {
        super();
    }

    public static String getCollectionName() {
        return collectionName;
    }

    @Override
    public Utente getItem(String itemId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(itemId);
        DocumentSnapshot document = documentReference.get().get();

        Utente result = null;
        if (document.exists()) {
            result = document.toObject(Utente.class);
        }

        return result;
    }

    public Utente getUtenteByUsername(String username) throws ExecutionException, InterruptedException {
        CollectionReference users = firestore.collection(collectionName);
        Query query = users.whereEqualTo("username", username);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        Utente result = null;
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result = document.toObject(Utente.class);
        }

        return result;
    }

    @Override
    public Utente addItem(Utente item) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());
        ApiFuture<WriteResult> writeResult = documentReference.set(item, SetOptions.merge());

        DocumentSnapshot documentSnapshot = documentReference.get().get();
        return getItem(documentSnapshot.getId());
    }

    @Override
    public Utente updateItem(Utente item) {
        return null;
    }

    @Override
    public boolean deleteItem(Utente item) {
        return false;
    }
}
