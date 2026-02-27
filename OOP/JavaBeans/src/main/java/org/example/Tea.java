package org.example;

public class Tea extends MenuItem implements Preparable {
    private String type;
    private Size size;

    public Tea(String name, double price, String type, Size size) {
        super(name, price);
        this.type = type;
        this.size = size;
    }

    public void prepare() {
        System.out.println("Чай готов");
    }
}
