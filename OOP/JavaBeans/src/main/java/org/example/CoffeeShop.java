package org.example;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShop {
    private String name;
    private List<MenuItem> menu;
    private List<Order> orders;
    private static CoffeeShopStats stats = new CoffeeShopStats();

    public CoffeeShop(String name) {
        this.name = name;
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();
    }


    public void addToMenu(MenuItem item) {
        menu.add(item);
    }


    public Order createOrder() {
        Order order = new Order();
        orders.add(order);
        System.out.println("Создан заказ");
        return order;
    }

    public static CoffeeShopStats getStats() {
        return stats;
    }

        public class Order {
        private List<MenuItem> items;

        public Order() {
            this.items = new ArrayList<>();
        }

            public void addItem(MenuItem item) {
                items.add(item);
                System.out.println(item.getName() + " добавлен в заказ");

                if (item instanceof Coffee) {
                    stats.incrementItemSold("Coffee");
                } else if (item instanceof Tea) {
                    stats.incrementItemSold("Tea");
                } else if (item instanceof Pastry) {
                    stats.incrementItemSold("Pastry");
                }
            }

            public double getTotal() {
                double total = 0;
                for (MenuItem item : items) {
                    total += item.getPrice();
                }
                return total;
            }

            public void processOrder() {

                for (MenuItem item : items) {
                    if (item instanceof Preparable) {
                        ((Preparable) item).prepare();
                    } else {
                        System.out.println("Подаем " + item.getName());
                    }
                }

                System.out.println("Сумма заказа: " + getTotal());
            }

            public void pay() {
                double total = getTotal();
                stats.addRevenue(total);
                System.out.println("Заказ оплачен: " + total);
            }
        }

    public static class CoffeeShopStats {
        private int totalCoffeesSold;
        private int totalTeasSold;
        private int totalPastriesSold;
        private double totalRevenue;

        public void incrementItemSold(String itemType) {
            switch (itemType) {
                case "Coffee":
                    totalCoffeesSold++;
                    break;
                case "Tea":
                    totalTeasSold++;
                    break;
                case "Pastry":
                    totalPastriesSold++;
                    break;
            }
        }

        public void addRevenue(double amount) {
            totalRevenue += amount;
        }

        public void printStats() {
            System.out.println("СТАТИСТИКА КОФЕЙНИ");
            System.out.println("Продано кофе: " + totalCoffeesSold);
            System.out.println("Продано чая: " + totalTeasSold);
            System.out.println("Продано выпечки: " + totalPastriesSold);
            System.out.println("Всего позиций: " + (totalCoffeesSold + totalTeasSold + totalPastriesSold));
            System.out.println("Общая выручка: " + totalRevenue);
        }
    }
}