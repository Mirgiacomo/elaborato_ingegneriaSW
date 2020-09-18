package elaborato_ingegneriaSW.dao;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import elaborato_ingegneriaSW.models.Contagio;

import java.util.Objects;
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
        Contagio result = new Contagio();
        if (document.exists()) {
            ComuneDaoImpl comuneDao = new ComuneDaoImpl();
            MalattiaContagiosaDaoImpl malattiaContagiosaDao = new MalattiaContagiosaDaoImpl();

            DocumentReference comuneDocument = firestore.document(Objects.requireNonNull(document.get("comune", String.class)));
            DocumentReference malattiaContagiosaDocument = firestore.document(Objects.requireNonNull(document.get("malattiaContagiosa", String.class)));

            result.setComune(comuneDao.getItem(comuneDocument.getId()));
            result.setMalattiaContagiosa(malattiaContagiosaDao.getItem(malattiaContagiosaDocument.getId()));
            result.setNumeroMedicoBase(document.get("numeroMedicoBase", Integer.class));
            result.setNumeroTerapiaIntensiva(document.get("numeroTerapiaIntensiva", Integer.class));
            result.setWeek(document.get("week", Integer.class));
            result.setYear(document.get("year", Integer.class));
        }

        return result;
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
