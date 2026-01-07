package dbexamples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import static dbutils.SQLQueries.sendSQLQuery;

public class FirstExample {

    public static void main(String[] args) {
        // Define the SQL query and password
        String query = "SELECT * FROM Persons";
        String response = sendSQLQuery(query);

        //Response is a JSON String.
        //How to handle JSON?
        
        // 1) Print the response
        System.out.println("Method 1 Response:");
        System.out.println(response);

        //2A) Parse it using the known database field names to pull info you want
        System.out.println("\n\nWhen you knw the fields you want:");
        JSONArray jsonArray = new JSONArray(response);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject row = jsonArray.getJSONObject(i);
            System.out.println("Row " + (i + 1) + ":");

            // Assuming the columns are "id" and "name". Replace with your actual column names
            int id = row.getInt("PersonID");
            String name = row.getString("LastName");

            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
        }

        // 2B) Loop through the JSON array Method 2
        //just grabs every key and value one by one.
        System.out.println("\n\nGrab every key/value pair");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject row = jsonArray.getJSONObject(i);
            System.out.println("Row " + (i + 1) + ":");

            // Iterate over keys dynamically
            Iterator<String> keys = row.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = row.get(key);
                System.out.println(key + ": " + value);
            }
        }
    }
}
