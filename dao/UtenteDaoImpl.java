package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import elaborato_ingegneriaSW.models.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
            result = new Utente();
            result.setNome(document.get("nome", String.class));
            result.setCognome(document.get("cognome", String.class));
            result.setUsername(document.get("username", String.class));
            result.setPassword(document.get("password", String.class));
            result.setCf(document.get("cf", String.class));
            result.setRuolo(document.get("ruolo", RuoloUtente.class));
            result.setRuolo(document.get("ruolo", RuoloUtente.class));
            result.setComuniAssociati(null);
//            List<String> complications = (List<String>)document.get("comuniAssociati");
//            if (complications != null) {
//                for(String c: complications) {
//                    ArrayList provola = (Objects.requireNonNull(document.get("comuniAssociati", ArrayList.class)));
//                    System.out.println(provola.size());
//                    ComuneDaoImpl comuneDao = new ComuneDaoImpl();
//                    System.out.println(comuneDocument.getId());
//                    System.out.println(comuneDao.getItem(comuneDocument.getId()).getNome());

//                result.setRegione(regioneDao.getItem(regioneDocument.getId()));
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
