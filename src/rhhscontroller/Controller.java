/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rhhscontroller;

import java.util.ArrayList;
import rhhscaffbackend.Consumable;

/**
 *
 * @author michael.roy-diclemen
 */
public interface Controller {
    public boolean onLogin(String user, String password);    

    public ArrayList<Consumable> loadFood(boolean b);

    public int sequentialSearch(ArrayList<Consumable> foodList, String searchTerm);

    public int binarySearch(ArrayList<Consumable> foodList, String searchTerm);

    public void selectionSort(ArrayList<Consumable> foodList);

    public void bubbleSort(ArrayList<Consumable> foodList);

    public void insertionSort(ArrayList<Consumable> foodList);
}
