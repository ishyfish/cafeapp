/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rhhscaffbackend;

import java.util.*;

import javax.xml.stream.FactoryConfigurationError;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static dbutils.SQLQueries.sendSQLQuery;
import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.Comparable;

// userType, username, password, accountBalance, orderHistory, dietaryRestrictions, shifts
// cafeteriaEmployee, bob, 1234, 9.99, a0923fj-j0ejf9e=2e90fj, ... 
// EACH LINE MUST HAVE 7 ARRAY INDICES

/**
 *
 * @author ishy1
 */
public class CafeteriaApp {
    private User currentUser;
    private CafeteriaEmployee[] cafeStaff;
    private CafeteriaEmployee[] onShift;
    private Order[] workingOrders;
    private Order[] fulfilledOrders;
    private Consumable[] inventory;

    public static final int numOfDietaryRestrictions = 5;

    public CafeteriaApp(){
        
    }
    
    public CafeteriaApp(User currentUser, ConsumerUser[] consumers, CafeteriaEmployee[] cafeStaff,
            CafeteriaEmployee[] onShift, Order[] workingOrders, Order[] fulfilledOrders, Consumable[] inventory) {
        this.currentUser = currentUser;
        this.cafeStaff = cafeStaff;
        this.onShift = onShift;
        this.workingOrders = workingOrders;
        this.fulfilledOrders = fulfilledOrders;
        this.inventory = inventory;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public CafeteriaEmployee[] getCafeStaff() {
        return cafeStaff;
    }

    public void setCafeStaff(CafeteriaEmployee[] cafeStaff) {
        this.cafeStaff = cafeStaff;
    }

    public CafeteriaEmployee[] getOnShift() {
        return onShift;
    }

    public void setOnShift(CafeteriaEmployee[] onShift) {
        this.onShift = onShift;
    }

    public Order[] getWorkingOrders() {
        return workingOrders;
    }

    public void setWorkingOrders(Order[] workingOrders) {
        this.workingOrders = workingOrders;
    }

    public Order[] getFulfilledOrders() {
        return fulfilledOrders;
    }

    public void setFulfilledOrders(Order[] fulfilledOrders) {
        this.fulfilledOrders = fulfilledOrders;
    }

    public Consumable[] getInventory() {
        return inventory;
    }

    public void setInventory(Consumable[] inventory) {
        this.inventory = inventory;
    }
    
    
//    public void registerStudentFile(File f, String username, String password)throws IOException, NoSuchAlgorithmException{
//        PrintWriter pw = new PrintWriter(new FileWriter(f, true));
//    
//        //create salt
//        String salt = this.salt();
//        
//        //add user to file
//        pw.println("student, " + username + ", " + this.encrypt(password, salt) + ", " + salt + ", "+ "0, " + "none, " + "none, " + "none");
//
//        pw.close(); 
//    }
//    
//    public void registerStaffFile(File f, String username, String password)throws IOException, NoSuchAlgorithmException{
//        PrintWriter pw = new PrintWriter(new FileWriter(f, true));
//        
//        String salt = this.salt();
//        
//        //add user to file
//        pw.println("staff, " + username + ", " + this.encrypt(password, salt) + ", " + salt + "0, " + "none, " + "none, " + "none");
//        pw.close();
//    }
//    
//    public void registerWorkerFile(File f, String username, String password)throws IOException, NoSuchAlgorithmException{
//        PrintWriter pw = new PrintWriter(new FileWriter(f, true));
//
//        String salt = this.salt();
//        
//        //add user to file
//        pw.println("cafeteriaWorker, " + username + ", " + this.encrypt(password, salt) + "0, " + "none, " + "none, " + "none");
//        pw.close();
//    }
//
//    public boolean usernameAvailabilityFile(File f, String username) throws IOException{
//        Scanner s = new Scanner(f);
//        
//        while(s.hasNextLine()){
//            String[] line = s.nextLine().split(", ");
//            //check username index of file
//            if (line.length > 1 ){
//                //check if username matches another in file
//              if (line[1].equals(username)){
//                 s.close();
//                 return false;
//               }   
//            }
//        }
//        s.close();
//        return true;
//    }

    /**
     * Creates and inserts a new user into database based off inputted value
     * @param userType inputted user type
     * @param username inputted username
     * @param password inputted password
     * @throws NoSuchAlgorithmException 
     */ //andrew
    public void registerUser(String userType, String username, String password) throws NoSuchAlgorithmException{
        //create salt
        String salt = this.salt();
        
        //insert into user table
        String query = "INSERT INTO UsersAT VALUES ('" + userType + "', '" + username + "', '" + this.encrypt(password, salt) + "', '" + salt + "', " + "0.00);";
        sendSQLQuery(query);      
    }
           
    /***
     * Returns a Boolean that indicates whether a username is available
     * @param username - inputted username
     * @return true if inputted username is available and not already in database; false otherwise
     */ //andrew
    public boolean usernameAvailability(String username){
        //get count of username in database (either 0 or 1)
        String query = "SELECT COUNT(UsersAT.username) FROM UsersAT WHERE UsersAT.username = '" + username + "';";
        
        //parse response
        String response = sendSQLQuery(query); 
        JSONArray jsonArray = new JSONArray(response);
        JSONObject row = jsonArray.getJSONObject(0);
       
        Iterator<String> keys = row.keys();
        String key = keys.next();
        int count = row.getInt(key);
        
        //user does not exist in database; return true
        if (count == 0){
            return true;
        }
        return false;
    }

    /***
     * Inserts a new consumable into consumables table based on inputted values
     * @param name consumable name
     * @param type consumable type
     * @param price consumable price
     * @param description consumable description
     * @param calories consumable calories
     * @param restriction consumable restriction
     */ //andrew
    public void addMenuItem(String name, String type, String price, String description, String calories, String restriction){
        String query = "INSERT INTO ConsumablesAT (enable, name, price, description, calories, type, dietary_restrictions) VALUES (1, '" + name + "', " + price + ", '" + description + "', " + calories + ", '" + type + "', '" + restriction + "');";
        sendSQLQuery(query); 
    }
    
    /***
     * Returns a Boolean that indicates whether a food name is available
     * @param foodName food name
     * @return true if inputted food name is available and not already in database
     */ //andrew
    public boolean itemNameAvailability(String foodName){
        //get count of food name in database (either 0 or 1)
        String query = "SELECT COUNT(ConsumablesAT.name) FROM ConsumablesAT WHERE ConsumablesAT.name = '" + foodName + "';";
        String response = sendSQLQuery(query); 
        
        //parse response
        JSONArray jsonArray = new JSONArray(response);
        JSONObject row = jsonArray.getJSONObject(0);
       
        Iterator<String> keys = row.keys();
        String key = keys.next();
        int count = row.getInt(key);
        
        //food does not exist in database; return true
        if (count == 0){
            return true;
        }
        return false;
    }
    
    public int logIn(String inputtedUsername, String inputtedPassword) throws IOException, NoSuchAlgorithmException { // how to handle invalid input exceptions?? ex. an orderID that doesn't exist
        String saltQuery = "SELECT UsersAT.salt FROM UsersAT WHERE username = '" + inputtedUsername + "'";
        String salt = new JSONArray(sendSQLQuery(saltQuery)).getJSONObject(0).getString("salt");
        String encryptedPassword = encrypt(inputtedPassword, salt);
        String userQuery = "SELECT * FROM UsersAT WHERE username = '"+ inputtedUsername + "' AND password = '" + encryptedPassword + "'";
        String userResponse = sendSQLQuery(userQuery);
        
        if (userResponse.equals("[]")) {
            return -1;
        } else {
            // loggedIn!
            JSONArray usersArr = new JSONArray(userResponse);
            JSONObject user = usersArr.getJSONObject(0);
            String userType = user.getString("user_type");
            String username = user.getString("username");
            String password = user.getString("password");
            double accountBalance = user.getDouble("balance");

            switch (userType) {
                case "Student":
                    currentUser = new Student(username, password, Integer.valueOf(salt), accountBalance);
                    currentUser.logIn();
                    break;
                case "Staff":
                    currentUser = new Staff(username, password, Integer.valueOf(salt), accountBalance);
                    currentUser.logIn();
                    break;
                case "CafeteriaWorker": //** 
                    currentUser = new CafeteriaEmployee(username, password, Integer.valueOf(salt));
                    currentUser.logIn();
                    break;
            }
            return 0;
        }
    }   
    
    // return -1 = username and password don't match, user not found
    // return -2 = username and password match, corrupt user data
    public int logInFile(String inputtedUsername, String inputtedPassword) throws IOException, NoSuchAlgorithmException { // how to handle invalid input exceptions?? ex. an orderID that doesn't exist
        File userInfo = new File("userInfo.txt");
        Scanner sc = new Scanner(userInfo);

        // try to find user in file
        boolean loggedIn = false;
        int userLine = 0;
        while (sc.hasNextLine()) {
            // incrementing current line
            userLine++;

            // creating variables to store user information
            String encryptedPassword="";
            String[] line = sc.nextLine().split(", "); 
            String userType="";
            String username="";
            String password="";
            String salt="";
            double accountBalance = -1;
            ArrayList<Order> orderHistory = new ArrayList<Order>();
            boolean[] dietaryRestrictions = new boolean[numOfDietaryRestrictions];
            ArrayList<Integer> shifts = new ArrayList<Integer>();

            try {
                // inputting information from the line in the file
                userType = line[0];
                username = line[1];
                password = line[2];
                salt = line[3];
                accountBalance = Double.parseDouble(line[4]); // only for consumer users
                ArrayList<String> tempOrderHistory = new ArrayList<String>(Arrays.asList(line[4].split("-"))); // only for consumer users; just the order IDs, map each orderID to an actual order in another file
                String[] tempDietaryRestrictions = line[5].split("-"); // only for consumer users
                String[] tempShifts = line[6].split("-"); // only for cafeteria employees

                // getting encrypted password
                encryptedPassword = encrypt(inputtedPassword, salt);

                // inputting order history from file (UNIMPLEMENTED)

                // inputting dietary restrictions
                if (!tempDietaryRestrictions[0].equals("none")) {
                    for (int i = 0; i < tempDietaryRestrictions.length; i++) { 
                        dietaryRestrictions[i] = Boolean.parseBoolean(tempDietaryRestrictions[i]);
                    }
                }
                
                // inputting shifts
                if (!tempShifts[0].equals("none")) {
                    for (int i = 0; i < tempShifts.length; i++) { 
                        shifts.add(Integer.parseInt(tempShifts[i]));
                    }
                }
                
            } catch (Exception e) {
                // if the username and password are actually equal, and there's only something wrong with the data later on in the line
                if (username.equals(inputtedUsername) && password.equals(encryptedPassword)) {
                    sc.close();
                    return -2; // if user data corrupt
                } else {
                    continue; // ignore input from current line, move onto the next line
                }
            }

            if (username.equals(inputtedUsername) && password.equals(encryptedPassword)) {
                // log in user if user data not corrupt
                switch (userType) {
                    case "student":
                        loggedIn = true;
                        currentUser = new Student(username, password, Integer.valueOf(salt), accountBalance, orderHistory, new ArrayList<Order>(), dietaryRestrictions);
                        currentUser.logIn();
                        break;
                    case "staff":
                        loggedIn = true;
                        currentUser = new Staff(username, password, Integer.valueOf(salt), accountBalance, orderHistory, new ArrayList<Order>(), dietaryRestrictions);
                        currentUser.logIn();
                        break;
                    case "cafeteriaEmployee":
                        loggedIn = true;
                        currentUser = new CafeteriaEmployee(username, password, Integer.valueOf(salt), shifts, new Order());
                        currentUser.logIn();
                        break;
                    default: 
                        sc.close();
                        return -2;
                }
            }
        }

        // if user not found anywhere in the file
        if (!loggedIn) {
            sc.close();
            return -1;
        }
        sc.close();

        return userLine;
    }

    public void logOutFile(int userLine) throws IOException {
        // rewrite current user's information in the file on userLine
        File userInfo = new File("userInfo.txt");
        File output = new File("output");
        Scanner sc = new Scanner(userInfo);
        PrintWriter pw = new PrintWriter(output);

        int currentLine = 0;
        while (sc.hasNextLine()) {
            currentLine++;

            if (currentLine != userLine) {
                pw.println(sc.nextLine());
            } else {
                // going to the next line
                sc.nextLine();

                // updating the user info line
                if (currentUser instanceof Student) {
                    Student s = (Student) currentUser;
                    String orders = "";
                    for (int i = 0; i < s.getOrderHistory().size(); i++) {
                        orders += s.getOrderHistory().get(i).getOrderID();
                        if (i != s.getOrderHistory().size()) {
                            orders += "-";
                        }
                    }
                    String dietaryRestrictions = "";
                    for (int i = 0; i < s.getDietaryRestrictions().length; i++) {
                        dietaryRestrictions += Boolean.toString(s.getDietaryRestrictions()[i]);
                        if (i != s.getDietaryRestrictions().length) {
                            dietaryRestrictions += "-";
                        }
                    }
                    pw.println("student, " + s.getUsername() + ", " + s.getPassword() + ", " + s.getAccountBalance() + ", " + orders + ", " + dietaryRestrictions + ", none");
                } else if (currentUser instanceof Staff) {
                    Staff s = (Staff) currentUser;
                    String orders = "";
                    for (int i = 0; i < s.getOrderHistory().size(); i++) {
                        orders += s.getOrderHistory().get(i).getOrderID();
                        if (i != s.getOrderHistory().size()) {
                            orders += "-";
                        }
                    }
                    String dietaryRestrictions = "";
                    for (int i = 0; i < s.getDietaryRestrictions().length; i++) {
                        dietaryRestrictions += Boolean.toString(s.getDietaryRestrictions()[i]);
                        if (i != s.getDietaryRestrictions().length) {
                            dietaryRestrictions += "-";
                        }
                    }
                    pw.println("staff, " + s.getUsername() + ", " + s.getPassword() + ", " + s.getAccountBalance() + ", " + orders + ", " + dietaryRestrictions + ", none");
                } else if (currentUser instanceof CafeteriaEmployee) {
                    CafeteriaEmployee ce = (CafeteriaEmployee) currentUser;
                    String shifts = "";
                    for (int i = 0; i < ce.getShifts().size(); i++) {
                        shifts += Integer.toString(ce.getShifts().get(i));
                        if (i != ce.getShifts().size()) {
                            shifts += "-";
                        }
                    }
                    pw.println("staff, " + ce.getUsername() + ", " + ce.getPassword() + ", none, none, none, " + shifts);
                } else {
                    // throw new Exception("wtf, currentUser does not have a valid userType");
                }
            }
        }

        sc.close();
        pw.close();

        userInfo.delete();
        output.renameTo(userInfo);
    }

    /***
     * Creates and returns a random 4 digit salt
     * @return 4 digit salt
     */ //andrew
    public String salt(){
        String salt = "";
        Random r = new Random();
        
        //generate rnadom salt of 4 integers
        for (int i=0; i<4; i++){
            salt = salt + r.nextInt(10);
        }
        
        return salt;
    }
    
    public String encrypt(String inputtedPassword, String salt) throws NoSuchAlgorithmException {
        //java helper class to perform encryption
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        //give the helper function the password
        md.update((inputtedPassword + salt).getBytes());
        //perform the encryption
        byte byteData[] = md.digest();
        String encryptedPassword="";
        //To express the byte data as a hexadecimal number (the normal way)
        for (int i = 0; i < byteData.length; ++i) {
            encryptedPassword += (Integer.toHexString((byteData[i] & 0xFF) |
            0x100).substring(1,3));
        }
    
        return encryptedPassword;
    }
    
    public static void createTable() {
        String usersTableQuery = "CREATE TABLE UsersAT " +
                                "(user_type varchar(255), " + 
                                "username varchar(255), " +
                                "password varchar(256), " +
                                "salt char(4), " +
                                "balance double(6, 2), " +
                                "PRIMARY KEY (username))";
        
        String consumablesTableQuery = "CREATE TABLE ConsumablesAT " +
                                "(enable bool, " +
                                "name varchar(255), " +
                                "price double(6, 2), " +
                                "description varchar(1000), " +
                                "calories int(255), " +
                                "expiry_date Date, " +
                                //type
                                //restriction
                                "PRIMARY KEY (name))";

        String ordersTableQuery = "CREATE TABLE OrdersAT " +
                                "(order_id char(8), " +
                                "food_item varchar(255), " +
                                "username varchar(255), " +
                                "timePlaced datetime, " +
                                "timeFulfilled datetime, " +
                                "paid bool, " +
                                "fulfilled bool, " +
                                "PRIMARY KEY (order_id))";

        String dietaryRestrictionsTableQuery = "CREATE TABLE DietaryRestrictionsAT " +
                                "(type varchar(255), " + // user or consumable
                                "username varchar(255), " + 
                                "dr1 bool, " +
                                "dr2 bool, " +
                                "dr3 bool, " +
                                "PRIMARY KEY (username))";

        String shiftsTableQuery = "CREATE TABLE ShiftsAT " +
                                "(username varchar(255), " +
                                "9am bool, " +
                                "10am bool, " +
                                "11am bool, " +
                                "12pm bool, " +
                                "1pm bool, " +
                                "2pm bool, " +
                                "3pm bool, " +
                                "PRIMARY KEY (username))";

        String tempQuery = "ALTER TABLE ConsumablesAT ADD type varchar(255)";

        sendSQLQuery(tempQuery);
    }

    public static void bulkInputConsumables(File f) throws IOException {
        Scanner sc = new Scanner(f);
        String query = "INSERT INTO ConsumablesAT (enable, name, type, price, description, calories, dietary_restrictions) VALUES ";
        int n = 0;

        while (sc.hasNextLine()) {
            // name = [0], type = [1], price = [2], description = [3], calories = [4], dietary restrictions = [5], expiry date = [6]
            String[] line = sc.nextLine().split(","); 
            System.out.println(Arrays.toString(line));
            query += "(1, '" + line[0] + "', '" + line[1] + "', " + line[2] + ", '"  + line[3] + "', " + line[4] + ", '" + line[5] + "')";

            if (sc.hasNextLine()) {
                query += ", ";
            } else {
                query += ";";
                break;
            }
        }

        sc.close();
        System.out.println(query);
        sendSQLQuery(query);
    }

    public int sequentialSearch(ArrayList<Consumable> consumables, String target) {
        // Collections.sort(new ArrayList<Consumable>());
        // System.out.println(consumables.toString());
        for (int i = 0; i < consumables.size(); i++) {
            if (consumables.get(i).equals(target)) {
                return i;
            }
        }
        return -1;
    }
    
    /***
     * Sorts a given array list using bubble sort
     * @param <T> data type of array list
     * @param items array list 
     */ //andrew
    public <T extends Comparable> void bubbleSort(ArrayList<T>items){
        boolean switches = true;
        
        //sort until no more available switches
        while(switches){
            int switchCount = 0;
            
            //loop through every adjacent pair in array
            for (int i=0; i<items.size()-1; i++){
                T x = items.get(i);
                T y = items.get(i+1);

                //if left value in pair greater than right value, swap the values
                if (x.compareTo(y) > 0){
                    items.set(i, y);
                    items.set(i+1, x);
                    switchCount += 1;
                }
            }
            
            //if no swaps occured end sort
            if (switchCount == 0){
                switches = false;
            }        
        }
    }
    
    /***
     * Searches and returns the index of a target in a given array list using binary search
     * @param consumables inputted array list
     * @param target target to be searched for
     * @return index position of target in array if found; else return -1
     */ //andrew
    public int binarySearch(ArrayList<Consumable> consumables, String target){
        //set start, end
        int start = 0; 
        int end = consumables.size()-1;
        int index = -1;
        
        boolean search = true;
        while (search){
            //set mid
            int mid = (start+end)/2;
            if (start<=end){
                //if mid value equals target end search 
                if ((consumables.get(mid)).compareToName(target) == 0){
                    index = mid;
                    search = false;
                }
                //if mid value less than target set lower bound to index above mid
                else if ((consumables.get(mid)).compareToName(target) < 0){
                        start = mid+1;
                    }
                //if mid value greater than target set upper bound to index under mid
                else{
                    end = mid-1;
                } 
            }
            //target not found; return -1
            else{
                return -1;
            }
        }
        return index;
    }

    public <T extends Comparable<T>> void selectionSort(ArrayList<T> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            int smallest = i;
            for (int j = i + 1; j < items.size(); j++) {
                // item at index j is earlier alphabetically than consumable at index smallest
                if (items.get(j).compareTo(items.get(smallest)) < 0) {
                    // new smallest found
                    smallest = j;
                }
            }
            T tempSmallest = items.get(smallest);
            items.set(smallest, items.get(i));
            items.set(i, tempSmallest);
        }

        System.out.println(items);
    }

    public <T extends Comparable<T>> void insertionSort(ArrayList<T> items) {
        ArrayList<T> sorted = new ArrayList<T>();
        sorted.add(items.get(0));

        for (int i = 1; i < items.size(); i++) {
            T current = items.get(i);
            sorted.add(current);
            int indexToSlotCurrentIn = -1;

            for (int j = sorted.size() - 2; j >= 0; j--) {
                if (current.compareTo(sorted.get(j)) >= 0) {
                    indexToSlotCurrentIn = j + 1;
                    break;
                } else if (j == 0) {
                    indexToSlotCurrentIn = 0;
                }
            }

            for (int j = sorted.size()-1; j > indexToSlotCurrentIn; j--) {
                sorted.set(j, sorted.get(j - 1));
            }
            sorted.set(indexToSlotCurrentIn, current);
        }

        System.out.println(sorted);
        items = (ArrayList<T>) sorted.clone();
    }

    public ArrayList<Consumable> loadFood(boolean b) {
        ArrayList<Consumable> c = new ArrayList<Consumable>();
        String query = "SELECT * FROM ConsumablesAT";
        String response = sendSQLQuery(query);
        JSONArray jsonArray = new JSONArray(response);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject row = jsonArray.getJSONObject(i);
            String name = row.getString("name");
            double price = row.getDouble("price");
            String description = row.getString("description");
            int calories = row.getInt("calories");
            String dietaryRestrictions = row.getString("dietary_restrictions");
            // Date date = row.getDate("expiry_date");

            switch (row.getString("type")) {
                case "Prepared":
                    if (dietaryRestrictions.equals("None")) {
                        c.add(new PreparedFood(name, description, price, calories, ""));
                    } else {
                        c.add(new PreparedFood(name, description, price, calories, dietaryRestrictions));
                    }
                    break;
                case "Drink":
                    c.add(new Drink(name, description, price, calories, null));
                    break;
                case "Snack":
                    c.add(new Snack(name, description, price, calories, null));
                    break;
            }
        }

        return c;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String query = "SELECT * FROM ConsumablesAT";
        String response = sendSQLQuery(query);
         System.out.println(response);
        JSONArray jsonArray = new JSONArray(response);
//        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject row = jsonArray.getJSONObject(i);
            System.out.println(row);
             System.out.println("Row " + (i + 1) + ":");

             // Iterate over keys dynamically
             Iterator<String> keys = row.keys();
             while (keys.hasNext()) {
                 String key = keys.next();
                 Object value = row.get(key);
                 System.out.println(key + ": " + value);
             }
        }

        // CafeteriaApp c = new CafeteriaApp();
        // ArrayList<Consumable> foodList = c.loadFood(true);
    }
}


