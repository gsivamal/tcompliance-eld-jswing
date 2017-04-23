package domain.model.factory;

import dao.DaoException;
import domain.mediator.Instances;
import domain.model.CompanySettings;

public class CompanySettingsFactory extends Factory<CompanySettings> {

    private static final CompanySettingsFactory instance = new CompanySettingsFactory();

    private CompanySettingsFactory() {
    }

    public static CompanySettingsFactory getInstance() {
        return instance;
    }

    public CompanySettings getCompanySettings(String name,
                                  String value, String description) {
        try {
            incrementCount();
            return getCompanySettings( getCount(), name, value, description );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public CompanySettings getCompanySettings(int id, String name,
                               String value, String description) {
        CompanySettings item = new CompanySettings( id,
                name, value, description );
        cachedObjects.put( id, item );
        return item;
    }

    public CompanySettings getCompanySettings(int id) throws DaoException{
        CompanySettings item = cachedObjects.get( id );
        if ( item == null ) {
            item = Instances.getCompanySettingsSQLiteDB().get( id );
            cachedObjects.put( id, item );
        }
        return item;
    }

}
