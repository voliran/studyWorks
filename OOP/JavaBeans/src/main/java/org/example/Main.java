package org.example;

public class Main {
    public static void main(String[] args) {
        CoffeeShop shop = new CoffeeShop("JavaBeans");

        MenuItem coffee = new Coffee("Капучино", 115, "Крепкий", Size.MEDIUM);
        MenuItem tea = new Tea("Черный чай", 80, "Черный", Size.SMALL);
        MenuItem pastry = new Pastry("Круассан", 180, "Крем");


        shop.addToMenu(coffee);
        shop.addToMenu(tea);
        shop.addToMenu(pastry);

        CoffeeShop.Order order1 = shop.createOrder();
        order1.addItem(tea);
        order1.addItem(pastry);
        order1.processOrder();
        order1.pay();


        CoffeeShop.Order order2 = shop.createOrder();
        order2.addItem(coffee);
        order2.processOrder();
        order2.pay();

        CoffeeShop.getStats().printStats();
    }
}