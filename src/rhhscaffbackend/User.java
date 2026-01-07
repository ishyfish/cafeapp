/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rhhscaffbackend;

/**
 *
 * @author ishy1
 */
public abstract class User {
    private String username;
    private String password;
    private int salt;
    private boolean isLoggedIn;

    public User(String username, String password, int salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getSalt() {
        return salt;
    }

    public boolean checkLogIn() {
        return isLoggedIn;
    }

    public void logIn() {
        isLoggedIn = true;
    }

    public void logOut() {
        isLoggedIn = false;
    }
}
