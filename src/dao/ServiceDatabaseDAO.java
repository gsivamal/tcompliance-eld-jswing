package dao;

import model.*;
import model.factory.ServiceFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDatabaseDAO implements ServiceDao {

    @Override
    public void add(Service item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement addStatement = connection.prepareStatement( "INSERT INTO service" +
                    "(service_id, vehicle_id, driver_id, co_driver_id, rule1, rule2, rule3, rule4) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)" );
            addStatement.setInt( 1, item.getServiceID() );
            addStatement.setInt( 2, item.getVehicleID() );
            addStatement.setInt( 3, Integer.parseInt( item.getDriver().getID() ) );
            addStatement.setInt( 4, item.getCoDriver() == null ? 0 : Integer.parseInt( item.getCoDriver().getID() ) );
            addStatement.setString( 5, item.getRules().get( 0 ).getRuleValue() );
            addStatement.setString( 6, item.getRules().get( 1 ).getRuleValue() );
            addStatement.setString( 7, item.getRules().get( 2 ).getRuleValue() );
            addStatement.setString( 8, item.getRules().get( 3 ).getRuleValue() );
            addStatement.executeUpdate();
            StatusDatabaseDAO statusDao = new StatusDatabaseDAO();
            for (Status status : item.getStatusHistory()) {
                statusDao.add( item, status );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Service item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement( "UPDATE service SET " +
                    "vehicle_id = ?, driver_id = ?, co_driver_id = ?, rule1 = ?, rule2 = ?, rule3 = ?, rule4 = ? " +
                    "WHERE service_id = ?" );
            updateStatement.setInt( 1, item.getVehicleID() );
            updateStatement.setInt( 2, Integer.parseInt( item.getDriver().getID() ) );
            updateStatement.setInt( 3, Integer.parseInt( item.getCoDriver().getID() ) );
            updateStatement.setString( 4, item.getRules().get( 0 ).getRuleValue() );
            updateStatement.setString( 5, item.getRules().get( 1 ).getRuleValue() );
            updateStatement.setString( 6, item.getRules().get( 2 ).getRuleValue() );
            updateStatement.setString( 7, item.getRules().get( 3 ).getRuleValue() );
            updateStatement.setInt( 8, item.getServiceID() );
            updateStatement.executeUpdate();
            StatusDatabaseDAO statusDao = new StatusDatabaseDAO();
            for (Status status : item.getStatusHistory()) {
                statusDao.update( item, status );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(Service item) {

    }

    @Override
    public Service get(int id) {
        return null;
    }

    @Override
    public ArrayList<Service> getAll() {
        ArrayList<Service> serviceList = new ArrayList<>();
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement retrieveAllStatement = connection.prepareStatement( "SELECT * FROM service" );
            ResultSet resultSet = retrieveAllStatement.executeQuery();
            while ( resultSet.next() ) {
                int serviceID = resultSet.getInt( "service_id" );
                int vehicleID = resultSet.getInt( "vehicle_id" );
                DriverDatabaseDAO driverDatabaseDAO = new DriverDatabaseDAO();
                User driver = driverDatabaseDAO.get( resultSet.getInt( "driver_id" ) );
                User coDriver = driverDatabaseDAO.get( resultSet.getInt( "co_driver_id" ) );
                Rule rule1 = new Rule( resultSet.getString( "rule1" ) );
                Rule rule2 = new Rule( resultSet.getString( "rule2" ) );
                Rule rule3 = new Rule( resultSet.getString( "rule3" ) );
                Rule rule4 = new Rule( resultSet.getString( "rule4" ) );
                FuelDatabaseDAO fuelDao = new FuelDatabaseDAO();
                LoadDatabaseDAO loadDao = new LoadDatabaseDAO();
                StatusDatabaseDAO statusDao = new StatusDatabaseDAO();
                Fuel fuel = fuelDao.get( serviceID );
                Load load = loadDao.get( serviceID );
                StatusHistory statuses = statusDao.getAllService( serviceID );
                Service service = ServiceFactory.getInstance().createService( serviceID, driver, coDriver, fuel, load, statuses );
                serviceList.add( service );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return serviceList;
    }

    public int getCount(){
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getCountStatement = connection.prepareStatement( "SELECT COUNT(*) FROM service" );
            ResultSet resultSet = getCountStatement.executeQuery();
            if ( resultSet.next() ) {
                return resultSet.getInt( 1 );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
