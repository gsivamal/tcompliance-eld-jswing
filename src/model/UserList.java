package model;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class UserList extends SimpleListProperty<User>{

    public UserList(){
        super( FXCollections.observableArrayList() );
    }

    public User getUser(String username, String password) {
        for (User user : this) {
            if ( user.getUsername().equalsIgnoreCase( username ) && user.checkPassword( password ) ) {
                return user;
            }
        }
        return null;
    }
}
