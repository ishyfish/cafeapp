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
public class CafeteriaEmployee extends User{
    private ArrayList<Integer> shifts;
    private Order workingOrder;

    public CafeteriaEmployee(String username, String password, int salt) {
        super(username, password, salt);
    }

    public CafeteriaEmployee(String username, String password, int salt, ArrayList<Integer> shifts, Order workingOrder) {
        super(username, password, salt);
        this.shifts = shifts;
        this.workingOrder = workingOrder;
    }
    
    public void addShift(){
        
    }
    
    public void takeWorkingOrder(){
        
    }
    
    public void finishWorkingOrder(){
        
    }

    public ArrayList<Integer> getShifts() {
        return shifts;
    }

    public void setShifts(ArrayList<Integer> shifts) {
        this.shifts = shifts;
    }

    public Order getWorkingOrder() {
        return workingOrder;
    }

    public void setWorkingOrder(Order workingOrder) {
        this.workingOrder = workingOrder;
    }
    
    
    
    
}
