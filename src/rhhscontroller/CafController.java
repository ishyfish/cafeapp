/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rhhscontroller;

import static dbutils.SQLQueries.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import rhhscaffbackend.CafeteriaApp;
import rhhscaffbackend.Consumable;
import rhhscaffbackend.Drink;
import rhhscaffbackend.PreparedFood;
import rhhscaffbackend.Snack;

/**
 *
 * @author michael.roy-diclemen
 */
public class CafController implements Controller {
    CafeteriaApp c;

    public CafController(CafeteriaApp c) {
        this.c = c;
    }

    public int logIn(String username, String password) throws IOException, NoSuchAlgorithmException {
        int userLine = c.logIn(username, password);
        return userLine;
    }

    public void logOut(int userLine) throws IOException {
        c.logOutFile(userLine);
    }

    public boolean register(String userType, String username, String password) throws NoSuchAlgorithmException {
        // register user and return true if username avalible
        if (c.usernameAvailability(username)) {
            c.registerUser(userType, username, password);
            return true;
        }

        // return false if usenrame not avaliable
        return false;
    }

    public boolean addMenuItem(String name, String type, String price, String description, String calories, String restrictions) {
        // add food item to database and return true if food doesnt already exist
        if (c.itemNameAvailability(name)) {
            c.addMenuItem(name, type, price, description, calories, restrictions);
            return true;
        }

        // return false if item name not available
        return false;
    }

    public CafeteriaApp getC() {
        return c;
    }

    public void setC(CafeteriaApp c) {
        this.c = c;
    }

    @Override
    public boolean onLogin(String user, String password) {
        return true;
    }

    @Override
    public ArrayList<Consumable> loadFood(boolean b) {
        return c.loadFood(b);
    }

    @Override
    public int sequentialSearch(ArrayList<Consumable> consumables, String target) {
        return c.sequentialSearch(consumables, target);
    }

    @Override
    public int binarySearch(ArrayList<Consumable> foodList, String searchTerm) {
        return c.binarySearch(foodList, searchTerm);
    }

    public void selectionSort(ArrayList<Consumable> consumable) {
        c.selectionSort(consumable);
    }

  
    @Override
    public void bubbleSort(ArrayList<Consumable>  consumable)  {
        c.bubbleSort(consumable);
    }

    @Override
    public void insertionSort(ArrayList<Consumable> foodList) {
        c.insertionSort(foodList);
    }
}
