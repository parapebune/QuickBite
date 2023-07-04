package com.sda.QuickBite.enums;

public enum OrderStatus {
    ORDERED("Feast Commences"),
    DELIVERED("Celestial Arrival"),
    CANCELED("Harmonious Abandonment"),
    ON_THE_WAY("Murmurs of Arrival"),

    COOKING("Epicurean Prelude"),

    DELIVERY_FAILED("Lost in the Breeze");

//    RATED("")


    public String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
