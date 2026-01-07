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
public class Staff extends ConsumerUser{
    private static int discountPercentage = 10;

    public Staff(String username, String password, int salt, double accountBalance) {
        super(username, password, salt, accountBalance);
    }

    public Staff(String username, String password, int salt, double accountBalance, ArrayList<Order> orderHistory, ArrayList<Order> currentOrders, boolean[] dietaryRestrictions) {
        super(username, password, salt, accountBalance, orderHistory, currentOrders, dietaryRestrictions);
    }
    
    public void placeOrder(){
    }
}
