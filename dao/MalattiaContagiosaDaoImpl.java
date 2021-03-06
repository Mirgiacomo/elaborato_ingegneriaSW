package elaborato_ingegneriaSW.dao;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.SetOptions;
import elaborato_ingegneriaSW.models.MalattiaContagiosa;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MalattiaContagiosaDaoImpl extends DaoImpl<MalattiaContagiosa> {
    private static final String collectionName = "malattie_contagiose";

    public MalattiaContagiosaDaoImpl() {
        super();
    }

    public static String getCollectionName() {
        return collectionName;
    }

    @Override
    public MalattiaContagiosa getItem(String itemId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(itemId);
        DocumentSnapshot document = documentReference.get().get();

        return getItem(document);
    }

    @Override
    public MalattiaContagiosa getItem(DocumentSnapshot document) {
        MalattiaContagiosa result = null;
        if (document.exists()) {
            result = new MalattiaContagiosa(document.getString("nome"));

            List<String> complications = (List<String>) document.get("complications");
            if (complications != null) {
                for(String c: complications) {
                    result.addComplication(c);
                }
            }
        }
        return result;
    }

    @Override
    public MalattiaContagiosa saveItem(MalattiaContagiosa item) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection(collectionName).document(item.generateId());
        documentReference.set(item, SetOptions.merge());

        DocumentSnapshot documentSnapshot = documentReference.get().get();
        return getItem(documentSnapshot.getId());
    }

    @Override
    public boolean deleteItem(MalattiaContagiosa item) {
        return false;
    }
}
