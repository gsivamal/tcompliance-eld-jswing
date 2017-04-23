package dao;

import domain.model.CompanySettings;
import domain.model.factory.CompanySettingsFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanySettingsSQLiteDB implements CompanySettingsDao{

    @Override
    public void add(CompanySettings companySettings) throws DaoException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement addStatement = connection.prepareStatement(
                    "INSERT INTO company_settings " +
                            "(name, value, description, id) VALUES" +
                            "(?, ?, ?, ?)"
            );
            addStatement.setInt( 4, companySettings.getId() );
            addStatement.setString( 1, companySettings.getName() );
            addStatement.setString( 2, companySettings.getValue() );
            addStatement.setString( 3, companySettings.getDescription() );
            addStatement.execute();
            addStatement.close();
        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
    }

    @Override
    public void update(CompanySettings companySettings) throws DaoException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE company_settings SET " +
                            "name = ?, value = ?, description = ? " +
                            "WHERE id = ?"
            );
            updateStatement.setInt( 4, companySettings.getId() );
            updateStatement.setString( 1, companySettings.getName() );
            updateStatement.setString( 2, companySettings.getValue() );
            updateStatement.setString( 3, companySettings.getDescription() );
            updateStatement.execute();
            updateStatement.close();

        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
    }

    @Override
    public void remove(CompanySettings companySettings) throws DaoException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement removeStatement = connection.prepareStatement(
                    "DELETE FROM company_settings " +
                            "WHERE id = ?"
            );
            removeStatement.setInt( 1, companySettings.getId() );
            removeStatement.execute();
            removeStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CompanySettings get(int id) throws DaoException {
        return getCompanySettings( id ).get( 0 );
    }

    @Override
    public List<CompanySettings> getAll() throws DaoException {
        return getCompanySettings( -1 );
    }

    private List<CompanySettings> getCompanySettings(int id) {
        List<CompanySettings> list = new ArrayList<>();
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement getAllStatement;
            if ( id == -1 ) {
                getAllStatement = connection.prepareStatement(
                        "SELECT id, name, value, description " +
                                "FROM company_settings;"
                );
            }else{
                getAllStatement = connection.prepareStatement(
                        "SELECT id, name, value, description " +
                                "FROM company_settings " +
                                "WHERE id = ?;"
                );
                getAllStatement.setInt( 1, id );
            }
            ResultSet resultSet = getAllStatement.executeQuery();
            while ( resultSet.next() ) {
                int resultID = resultSet.getInt( "id" );
                String name = resultSet.getString( "name" );
                String value = resultSet.getString( "value" );
                String description = resultSet.getString( "description" );
                CompanySettings companySettings = CompanySettingsFactory.getInstance().
                        getCompanySettings( resultID, name, value, description );
                list.add( companySettings );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
