package com.driver;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Order {

    private String id;
    private LocalTime deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.deliveryTime = LocalTime.parse(deliveryTime, formatter);
    }

    public String getId() {
        return id;
    }

    public LocalTime getDeliveryTime() {return deliveryTime;}

    public void setPartner(DeliveryPartner partner) {
    }
}
