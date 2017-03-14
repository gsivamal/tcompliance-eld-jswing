package model.factory;

import model.Service;
import model.User;

public class UserFactory extends Factory {

    private static final UserFactory instance = new UserFactory();

    private UserFactory() {
    }

    public static UserFactory getInstance() {
        return instance;
    }

    public User createUser(String username, String password, String confirmPassword, String firstName, String middleName, String lastName, String licNumber, String status, String issuedState, String issuedCountry, boolean isAdmin, Service service) {
        try {
            incrementCount();
            return createUser( getCount(), username, password, confirmPassword, firstName, middleName, lastName, licNumber, status, issuedState, issuedCountry, isAdmin, service );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public User createUser(int id, String username, String password, String confirmPassword, String firstName, String middleName, String lastName, String licNumber, String status, String issuedState, String issuedCountry, boolean isAdmin, Service service) {
        if ( !password.equals( confirmPassword ) ) {
            throw new IllegalArgumentException( "Passwords do not match!" );
        }
        return new User( id, username, password, firstName, middleName, lastName, licNumber, status, issuedState, issuedCountry, isAdmin, service );
    }

}