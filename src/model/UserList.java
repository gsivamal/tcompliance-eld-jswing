package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserList {

    private ObservableList<User> users;
    private static UserList instance = new UserList();

    private UserList(){
        users = FXCollections.observableArrayList();
    }

    public static UserList getInstance(){
        return instance;
    }

    public User getUser(String username, String password) {
        for (User user : users) {
            if ( user.getUsername().equalsIgnoreCase( username ) && user.checkPassword( password ) ) {
                return user;
            }
        }
        return null;
    }

    public boolean remove(User user){
        return users.remove( user );
    }
    public void add(User user) {
        users.add( user );
    }

    public ObservableList<User> observableList(){
        return users;
    }

    @Override
    public String toString() {
        return "UserList{" +
                "users=" + users +
                '}';
    }
}
