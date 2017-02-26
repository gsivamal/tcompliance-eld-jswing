package model;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    private List<User> users;
    private static UserList instance = new UserList();

    private UserList(){
        users = new ArrayList<>();
    }

    public static UserList getInstance(){
        return instance;
    }

    public User getUser(String username, String password) {
        for (User user : users) {
            if ( user.getUsername().equals( username ) && user.checkPassword( password ) ) {
                return user;
            }
        }
        return null;
    }

    public void add(User user) {
        users.add( user );
    }
}
