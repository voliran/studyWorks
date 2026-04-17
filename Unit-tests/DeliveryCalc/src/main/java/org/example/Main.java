package org.example;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product(250, "lamp", true, "small"));
        products.add(new Product(10_000, "desk", false, "large"));
        products.add(new Product(25_000, "phone", true, "small"));

        Delivery delivery = new Delivery(20, products, Workload.VERY_HIGH);

        System.out.println(delivery.deliveryCoast());
    }
}