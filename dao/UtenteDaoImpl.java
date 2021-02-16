package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.models.*;

import java.util.*;
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
    public Utente getItem(DocumentSnapshot document) throws ExecutionException, InterruptedException {
        Utente result = null;
        if (document.exists()) {
            String nome = document.get("nome", String.class);
            String cognome = document.get("cognome", String.class);
            String username = document.get("username", String.class);
            String password = document.get("password", String.class);
            RuoloUtente ruolo = document.get("ruolo", RuoloUtente.class);
            String cf = document.get("cf", String.class);

            result = new Utente(cognome, nome, username, password, ruolo, cf);

            List<String> comuni = (List<String>) document.get("comuniAssociati");
            ComuneDaoImpl comuneDao = new ComuneDaoImpl();
            if (comuni != null) {
                for (String c : comuni) {
                    DocumentReference comuneDocument = firestore.document(Objects.requireNonNull(c));
                    result.addComune(comuneDao.getItem(comuneDocument.getId()));
                }
            }
        }
        return result;
    }

    @Override
    public Utente saveItem(Utente item) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());
        ApiFuture<WriteResult> writeResult = documentReference.set(item.getFirebaseObject(), SetOptions.merge());

        DocumentSnapshot documentSnapshot = documentReference.get().get();
        return getItem(documentSnapshot.getId());
    }

    @Override
    public boolean deleteItem(Utente item) {
        return false;
    }
}
