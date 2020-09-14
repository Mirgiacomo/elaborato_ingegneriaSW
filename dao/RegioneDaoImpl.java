package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import elaborato_ingegneriaSW.models.Regione;

import java.util.List;

public class RegioneDaoImpl extends DaoImpl<Regione> {

    public RegioneDaoImpl() {
        super();
    }

    @Override
    public Regione getItem(String itemId) {
        return null;
    }

    @Override
    public Regione addItem(Regione item) {
        DocumentReference documentReference = firestore.collection("regioni").document();
        ApiFuture<WriteResult> writeResult = documentReference.set(item);

        return null;
    }

    @Override
    public Regione updateItem(Regione item) {
        return null;
    }

    @Override
    public boolean deleteItem(Regione item) {
        return false;
    }
}
