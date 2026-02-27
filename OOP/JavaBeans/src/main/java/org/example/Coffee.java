package org.example;

public class Coffee extends MenuItem implements Preparable {
    private String strength;
    private Size size;

    public Coffee(String name, double price, String strength, Size size) {
        super(name, price);
        this.strength = strength;
        this.size = size;
    }

    public void prepare() {
        System.out.println("Кофе готов");
    }
}
