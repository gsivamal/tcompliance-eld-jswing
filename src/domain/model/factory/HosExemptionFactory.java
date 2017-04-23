package domain.model.factory;

import dao.DaoException;
import domain.mediator.Instances;
import domain.model.HosExemption;

public class HosExemptionFactory extends Factory<HosExemption>{
    private static HosExemptionFactory instance = new HosExemptionFactory();

    public static HosExemptionFactory getInstance() {
        return instance;
    }

    private HosExemptionFactory() {
    }


    public HosExemption getHosExemption(String exemption, boolean status) {
        try {
            incrementCount();
            return getHosExemption( getCount(), exemption, status );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public HosExemption getHosExemption(int id, String exemption, boolean status) {
        HosExemption item = new HosExemption( id, exemption, status );
        cachedObjects.put( id, item );
        return item;
    }

    public HosExemption getHosExemption(int id) throws DaoException{
        HosExemption item = cachedObjects.get( id );
        if ( item == null ) {
            item = Instances.getHosExemptionSQLiteDB().get( id );
            cachedObjects.put( id, item );
        }
        return item;
    }
}
