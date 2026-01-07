package rhhscaffbackend;

import java.util.*;

public class Order {
    private String orderID;
    private Consumable foodItem;
    private ConsumerUser user;
    private Date timePlaced;
    private Date timeFulfilled;
    private boolean paid;
    private boolean fulfilled;
    private QRCode qrCode;

    public Order() {
        
    }

    public Order(String orderID, Consumable foodItem, ConsumerUser user, Date timePlaced, Date timeFulfilled, boolean paid, boolean fulfilled, QRCode qrCode) {
        this.orderID = orderID;
        this.foodItem = foodItem;
        this.user = user;
        this.timePlaced = timePlaced;
        this.timeFulfilled = timeFulfilled;
        this.paid = paid;
        this.fulfilled = fulfilled;
        this.qrCode = qrCode;
    }

    public String getOrderID() {
        return orderID;
    }

    public Consumable getFoodItem() {
        return foodItem;
    }

    public ConsumerUser getUser() {
        return user;
    }

    public Date getTimePlaced() {
        return timePlaced;
    }

    public Date getTimeFulfilled() {
        return timeFulfilled;
    }

    public boolean getPaid() {
        return paid;
    }

    public boolean getFulfilled() {
        return fulfilled;
    }

    public QRCode getQRCode() {
        return qrCode;
    }
    
}
