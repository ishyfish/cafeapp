package rhhscaffbackend;

import java.util.*;

public class Drink extends Consumable {
    private Date expiryDate;

    public Drink(String name, String description, double price, int calories, Date expiryDate) {
        super(name, description, price, calories, "");
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
