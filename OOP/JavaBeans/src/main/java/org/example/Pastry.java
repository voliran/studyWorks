package org.example;

public class Pastry extends MenuItem {
    private String filling;

    public Pastry(String name, double price, String filling) {
        super(name,price);
        this.filling = filling;
    }
}
