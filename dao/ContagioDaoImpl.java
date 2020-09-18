package elaborato_ingegneriaSW.dao;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import elaborato_ingegneriaSW.models.Contagio;

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

        Contagio result = null;
        if (document.exists()) {
            result = document.toObject(Contagio.class);
        }

        return result;
    }

    @Override
    public Contagio getItem(DocumentSnapshot document) {
        return null;
    }

    @Override
    public Contagio addItem(Contagio item) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public Contagio updateItem(Contagio item) {
        return null;
    }

    @Override
    public boolean deleteItem(Contagio item) {
        return false;
    }
}
