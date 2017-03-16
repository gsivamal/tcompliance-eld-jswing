package dao;

import model.FuelCard;
import model.factory.FuelCardFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuelCardDatabaseDAO implements FuelCardDao{

    private static final FuelCardDatabaseDAO instance = new FuelCardDatabaseDAO();
    private FuelCardDatabaseDAO(){}
    public static FuelCardDatabaseDAO getInstance(){
        return instance;
    }

    @Override
    public void add(FuelCard item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement addStatement = connection.prepareStatement(
                    "INSERT INTO fuel_card (fuel_card_number, fuel_card_issued_by, fuel_card_id) " +
                            "VALUES (?, ?, ?)"
            );
            addStatement.setInt( 1, item.getNumber() );
            addStatement.setString( 2, item.getIssuedBy() );
            addStatement.setInt( 3, item.getId() );
            addStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void update(FuelCard item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE fuel_card SET " +
                            "fuel_card_number = ?, fuel_card_issued_by = ?" +
                            "WHERE fuel_card_id = ?"
            );
            updateStatement.setInt( 1, item.getNumber() );
            updateStatement.setString( 2, item.getIssuedBy() );
            updateStatement.setInt( 3, item.getId() );
            updateStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(FuelCard item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement removeStatement = connection.prepareStatement(
                    "DELETE FROM fuel_card WHERE fuel_card_id = ?"
            );
            removeStatement.setInt( 1, item.getId() );
            removeStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public FuelCard get(int id) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getStatement = connection.prepareStatement(
                    "SELECT " +
                            "fuel_card_number, fuel_card_issued_by " +
                            "FROM fuel_card " +
                            "WHERE fuel_card_id = ?;");
            ResultSet resultSet = getStatement.executeQuery();
            if ( resultSet.next() ) {
                int fuelCardNumber = resultSet.getInt( "fuel_card_number" );
                String issuedBy = resultSet.getString( "fuel_card_issued_by" );
                return FuelCardFactory.getInstance().getFuelCard( id, fuelCardNumber, issuedBy );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<FuelCard> getAll() {
        throw new UnsupportedOperationException();
    }
}
