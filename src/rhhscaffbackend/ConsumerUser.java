/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rhhscaffbackend;

import java.util.*;

/**
 *
 * @author ishy1
 */
public abstract class ConsumerUser extends User {
    private double accountBalance;
    private ArrayList<Order> orderHistory;
    private ArrayList<Order> currentOrders;
    private boolean[] dietaryRestrictions;

    public ConsumerUser(String username, String password, int salt, double accountBalance) {
        super(username, password, salt);
        this.accountBalance = accountBalance;
    }

    public ConsumerUser(String username, String password, int salt, double accountBalance, ArrayList<Order> orderHistory, ArrayList<Order> currentOrders, boolean[] dietaryRestrictions) {
        super(username, password, salt);
        this.accountBalance = accountBalance;
        this.orderHistory = orderHistory;
        this.currentOrders = currentOrders;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    
    public abstract void placeOrder();
    
    public void cancelOrder(){
    }
    
    public void checkOut(){
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public ArrayList<Order> getCurrentOrders() {
        return currentOrders;
    }

    public void setCurrentOrders(ArrayList<Order> currentOrders) {
        this.currentOrders = currentOrders;
    }

    public boolean[] getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(boolean[] dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }
}
