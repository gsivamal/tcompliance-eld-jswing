package domain.model.factory;

import dao.DaoException;
import domain.mediator.Instances;
import domain.model.EldDevice;

public class EldDeviceFactory extends Factory<EldDevice>{

    private static EldDeviceFactory instance = new EldDeviceFactory();

    public static EldDeviceFactory getInstance() {
        return instance;
    }

    private EldDeviceFactory() {
    }


    public EldDevice getEldDevice(String manufacturer, String version) {
        try {
            incrementCount();
            return getEldDevice( getCount(), manufacturer, version );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public EldDevice getEldDevice(int id, String manufacturer, String version) {
        EldDevice item = new EldDevice( id, manufacturer, version );
        cachedObjects.put( id, item );
        return item;
    }

    public EldDevice getEldDevice(int id) throws DaoException{
        EldDevice item = cachedObjects.get( id );
        if ( item == null ) {
            item = Instances.getEldDeviceSQLiteDB().get( id );
            cachedObjects.put( id, item );
        }
        return item;
    }
}
