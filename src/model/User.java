package model;

public class User {

    private String username;
    private String password;
    private Service service;

    public User(String username, String password, Service service) {
        this.username = username;
        this.password = password;
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String checkingPassword) {
        return password.equals( checkingPassword );
    }

    public Service getService() {
        return service;
    }
}
