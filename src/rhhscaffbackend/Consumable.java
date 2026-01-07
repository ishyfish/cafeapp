package rhhscaffbackend;

public class Consumable implements Comparable<Consumable> {
    private String name;
    private String description;
    private double price;
    private int calories;
    private String dietaryRestrictions;
    private int sortingAttribute = 1; // 1 = name, 2 = price, 3 = description, 4 = calories

    public Consumable(String name, String description, double price, int calories, String dietaryRestrictions) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.calories = calories;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public Consumable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getCalories() {
        return calories;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setName(String text) {
        name = text;
    }

    public void setPrice(double parseDouble) {
        price = parseDouble;
    }

    public void setDescription(String text) {
        description = text;
    }

    public void setCalories(int parseInt) {
        calories = parseInt;
    }

    public void setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public void setSortingAttribute(int columnIndex){
        sortingAttribute = columnIndex;
    }
    
    /***
     * Compares consumable name with an inputted food name
     * @param name
     * @return true if equal strings; otherwise false
     */
    public boolean equals(String name){
        if (name.toLowerCase().equals(this.name.toLowerCase())){
            return true;
        }
        return false;
    }
    
    // -1 if this is smaller (earlier alphabetically), 1 if this is larger (later alphabetically)
    public int compareTo(Consumable other) {
        if (this.sortingAttribute == 2) { // sort by price
            return compareToPrice(other.getPrice());
        } else if (this.sortingAttribute == 3) { // sort by description
            return compareToDescription(other.getDescription());
        } else if (this.sortingAttribute == 4) { // sort by calories
            return compareToCalories(other.getCalories());
        } else if (this.sortingAttribute == 5) { // sort by dietary restrictions
            return compareToDietaryRestrictions(other.getDietaryRestrictions());
        }

        // default: sort by name
        return compareToName(other.getName());
    }

    public int compareToName(String otherName) {
        for (int i = 0; i < Math.min(this.name.length(), otherName.length()); i++) {
            if (this.name.toLowerCase().charAt(i) < otherName.toLowerCase().charAt(i)) {
                return -1;
            } else if (this.name.toLowerCase().charAt(i) > otherName.toLowerCase().charAt(i)) {
                return 1;
            }
        }

        if (this.name.length() < otherName.length()) {
            return -1;
        } else if (this.name.length() > otherName.length()) {
            return 1;
        }
        return 0;
    
    }

    public int compareToPrice(double otherPrice) {
        if (this.price < otherPrice) {
            return -1;
        } else if (this.price > otherPrice) {
            return 1;
        }
        return 0;
    }

    public int compareToDescription(String otherDescription) {
        for (int i = 0; i < Math.min(this.description.length(), otherDescription.length()); i++) {
            if (this.description.toLowerCase().charAt(i) < otherDescription.toLowerCase().charAt(i)) {
                return -1;
            } else if (this.description.toLowerCase().charAt(i) > otherDescription.toLowerCase().charAt(i)) {
                return 1;
            }
        }

        if (this.description.length() < otherDescription.length()) {
            return -1;
        } else if (this.description.length() > otherDescription.length()) {
            return 1;
        }
        return 0;
    }

    public int compareToCalories(int otherCalories) {
        if (this.calories < otherCalories) {
            return -1;
        } else if (this.calories> otherCalories) {
            return 1;
        }
        return 0;
    }

    public int compareToDietaryRestrictions(String otherDietaryRestrictions) {
        for (int i = 0; i < Math.min(this.dietaryRestrictions.length(), otherDietaryRestrictions.length()); i++) {
            if (this.dietaryRestrictions.toLowerCase().charAt(i) > otherDietaryRestrictions.toLowerCase().charAt(i)) {
                return -1;
            } else if (this.dietaryRestrictions.toLowerCase().charAt(i) < otherDietaryRestrictions.toLowerCase().charAt(i)) {
                return 1;
            }
        }

        if (this.dietaryRestrictions.length() > otherDietaryRestrictions.length()) {
            return -1;
        } else if (this.dietaryRestrictions.length() < otherDietaryRestrictions.length()) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return this.name;
    }
}
