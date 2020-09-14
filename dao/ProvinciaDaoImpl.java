package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import elaborato_ingegneriaSW.models.Provincia;

import java.util.List;

public class ProvinciaDaoImpl extends DaoImpl<Provincia> {

    public ProvinciaDaoImpl() {
        super();
    }

    @Override
    public Provincia getItem(String itemId) {
        return null;
    }

    @Override
    public Provincia addItem(Provincia item) {
        DocumentReference documentReference = firestore.collection("province").document();

        RegioneDaoImpl regioneDao = new RegioneDaoImpl();

        ApiFuture<WriteResult> writeResult = documentReference.set(item);

        return null;
    }

    @Override
    public Provincia updateItem(Provincia item) {
        return null;
    }

    @Override
    public boolean deleteItem(Provincia item) {
        return false;
    }
}
