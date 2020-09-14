package elaborato_ingegneriaSW.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import elaborato_ingegneriaSW.models.Comune;

import java.util.List;

public class ComuneDaoImpl extends DaoImpl<Comune> {

    public ComuneDaoImpl() {
        super();
    }

    @Override
    public List getAllItems() {
        return null;
    }

    @Override
    public Comune getItem(String itemId) {
        return null;
    }

    public Comune getItemByCodiceISTAT(String codiceISTAT) {
        return null;
    }

    @Override
    public Comune addItem(Comune item) {
        return null;
    }

    @Override
    public Comune updateItem(Comune item) {
        return null;
    }

    @Override
    public boolean deleteItem(Comune item) {
        return false;
    }
}
