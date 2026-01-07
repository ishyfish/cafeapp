/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbexamples;

import dbutils.SQLQueries;
import static dbutils.SQLQueries.sendSQLQuery;

/**
 *
 * @author MikeRD
 */
public class ExampleOlympicsAccess {

    public static void main(String[] args) {
        //Select all perople in the Olympics with a last name of Smith
        String query = "SELECT * FROM Events WHERE name = '100m Women'";
        
        //find out all the olympics covered in this data set
        query = "SELECT * FROM Olympics";
        String response = sendSQLQuery(query);      
        System.out.print("Response from server:");
                System.out.println(response.toString());
        System.out.println(SQLQueries.parseJSONList(response));
        
        //select all information from events that have the word jump in it
        query = "SELECT * FROM Events WHERE name LIKE '%jump%'";
        response = sendSQLQuery(query);      
        System.out.print("Response from server:");
        System.out.println(response.toString());
        
        //List the name of all gold medalists in both olympics
        query = "SELECT Players.name from Results JOIN Players ON Results.player_id = Players.player_id WHERE Results.medal = 'GOLD'";
        response = sendSQLQuery(query);      
        System.out.print("Response from server:");
        System.out.println(response.toString());
        

        
        //UPDATE an item in a table
         query = "UPDATE Persons SET Address = 'RHHSS', City='Richmond Hill' WHERE PersonID=4";
        response = sendSQLQuery(query);      
        System.out.print("Response from server:");
        System.out.println(response.toString());      
        
        
        
        
        
    }

}
