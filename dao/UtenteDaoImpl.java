package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.models.Provincia;
import elaborato_ingegneriaSW.models.RuoloUtente;
import elaborato_ingegneriaSW.models.Territorio;
import elaborato_ingegneriaSW.models.Utente;

import java.util.Objects;
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

        return getItem(document);
    }

    @Override
    public Utente getItem(DocumentSnapshot document) {
        Utente result = null;
        if (document.exists()) {
            result = new Utente();
            result.setNome(document.get("nome", String.class));
            result.setCognome(document.get("cognome", String.class));
            result.setUsername(document.get("username", String.class));
            result.setPassword(document.get("password", String.class));
            result.setCf(document.get("cf", String.class));
            result.setRuolo(document.get("ruolo", RuoloUtente.class));
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
