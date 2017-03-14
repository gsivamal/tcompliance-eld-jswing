package model;

import dao.DriverDatabaseDAO;
import dao.ServiceDatabaseDAO;
import dao.StatusDatabaseDAO;
import model.factory.ServiceFactory;
import model.factory.StatusFactory;
import model.factory.UserFactory;

import java.util.ArrayList;

public class Mediator {

    private UserList userList;
    private ArrayList<Service> serviceList;
    private DriverDatabaseDAO driverDatabase;
    private ServiceDatabaseDAO serviceDatabase;
    private StatusDatabaseDAO statusDatabase;


    private static final Mediator instance = new Mediator();

    public static Mediator getInstance(){
        return instance;
    }

    private Mediator(){
        driverDatabase = new DriverDatabaseDAO();
        serviceDatabase = new ServiceDatabaseDAO();
        statusDatabase = new StatusDatabaseDAO();
        ServiceFactory.getInstance().setCount( serviceDatabase.getCount() );
        UserFactory.getInstance().setCount( driverDatabase.getCount() );
        StatusFactory.getInstance().setCount( statusDatabase.getCount() );
        serviceList = serviceDatabase.getAll();
        userList = driverDatabase.getAll();
        for (User user : userList) {
            user.setService( getServiceByDriverID( user.getID() ) );
        }
    }

    public Service getServiceByDriverID(String driverID) {
        for (Service service : serviceList) {
            if ( service.getDriver().getID().equals( driverID ) ) {
                return service;
            }
        }
        return null;
    }

    public void addUser(User user) {
        userList.add( user );
        driverDatabase.add( user );
    }

    public void removeUser(User user) {
        userList.remove( user );
        driverDatabase.remove( user );
    }

    public void addService(Service service) {
        serviceList.add( service );
        serviceDatabase.add( service );
    }

    public void editService(Service service) {
        serviceDatabase.update( service );
    }

    public void removeService(Service service) {
        serviceList.remove( service );
        serviceDatabase.remove( service );
    }

    public void addStatus(Service service, Status status) {
        service.setCurrentStatus( status );
        statusDatabase.add( service, status );
    }

    public void updateStatus(Service service, Status status) {
        statusDatabase.update( service, status );
    }

    public void removeStatus(Status status) {
        statusDatabase.remove( status );
    }

    public UserList getUserList() {
        return userList;
    }

}
