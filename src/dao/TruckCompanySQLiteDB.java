package dao;

import domain.model.TruckCompany;
import domain.model.factory.TruckCompanyFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TruckCompanySQLiteDB implements TruckCompanyDao{

    @Override
    public void add(TruckCompany truckCompany) throws DaoException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement addStatement = connection.prepareStatement(
                    "INSERT INTO truck_company " +
                            "(DOT, carrier, owner_name, address, address2, " +
                            "city, state, zip, country, id)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );
            addStatement.setString( 1, truckCompany.getDOT() );
            addStatement.setString( 2, truckCompany.getCarrier() );
            addStatement.setString( 3, truckCompany.getOwnerName() );
            addStatement.setString( 4, truckCompany.getAddress() );
            addStatement.setString( 5, truckCompany.getAddress2() );
            addStatement.setString( 6, truckCompany.getCity() );
            addStatement.setString( 7, truckCompany.getState() );
            addStatement.setInt( 8, truckCompany.getZip() );
            addStatement.setString( 9, truckCompany.getCountry() );
            addStatement.setInt( 10, truckCompany.getId() );
            addStatement.execute();
            addStatement.close();
        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
    }

    @Override
    public void remove(TruckCompany truckCompany) throws DaoException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE truck_company " +
                            "SET DOT = ?, carrier = ?, owner_name = ?," +
                            "address = ?, address2 = ?, city = ?, state = ?," +
                            "zip = ?, country = ? " +
                            "WHERE id = ?"
            );
            updateStatement.setString( 1, truckCompany.getDOT() );
            updateStatement.setString( 2, truckCompany.getCarrier() );
            updateStatement.setString( 3, truckCompany.getOwnerName() );
            updateStatement.setString( 4, truckCompany.getAddress() );
            updateStatement.setString( 5, truckCompany.getAddress2() );
            updateStatement.setString( 6, truckCompany.getCity() );
            updateStatement.setString( 7, truckCompany.getState() );
            updateStatement.setInt( 8, truckCompany.getZip() );
            updateStatement.setString( 9, truckCompany.getCountry() );
            updateStatement.setInt( 10, truckCompany.getId() );
            updateStatement.execute();
            updateStatement.close();
        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
    }

    @Override
    public TruckCompany get(int id) throws DaoException {
        return getAll( id ).get( 0 );
    }

    @Override
    public List<TruckCompany> getAll() throws DaoException {
        return getAll( null );
    }

    private List<TruckCompany> getAll(Integer id) throws DaoException {
        List<TruckCompany> list = new ArrayList<>();
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement getAllStatement;
            if ( id == null ) {
                getAllStatement = connection.prepareStatement(
                        "SELECT id, DOT, carrier, owner_name, address, address2," +
                                " city, state, zip, country FROM truck_company"
                );
            }else{
                getAllStatement = connection.prepareStatement(
                        "SELECT id, DOT, carrier, owner_name, address, " +
                                "address2, city, state, zip, country " +
                                "FROM truck_company " +
                                "WHERE id = ?"
                );
                getAllStatement.setInt( 1, id );
            }
            ResultSet resultSet = getAllStatement.executeQuery();
            while ( resultSet.next() ) {
                int resultID = resultSet.getInt( "id" );
                String DOT = resultSet.getString( "DOT" );
                String carrier = resultSet.getString( "carrier" );
                String ownerName = resultSet.getString( "owner_name" );
                String address = resultSet.getString( "address" );
                String address2 = resultSet.getString( "address2" );
                String city = resultSet.getString( "city" );
                String state = resultSet.getString( "state" );
                int zip = resultSet.getInt( "zip" );
                String country = resultSet.getString( "country" );
                TruckCompany truckCompany = TruckCompanyFactory.getInstance().
                        getTruckCompany( resultID, DOT, carrier, ownerName, address,
                                address2, city, state, zip, country );
                list.add( truckCompany );
            }
        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
        return list;
    }
}
