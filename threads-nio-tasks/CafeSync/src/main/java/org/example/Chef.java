package org.example;


import com.sun.jdi.event.ThreadDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class Chef implements Runnable {
    private static final List<String> orders = new ArrayList<>();
    private static final List<String>  dishes = new ArrayList<>();

    static void main(String[] args) throws InterruptedException {

        dishes.add("Борщ");
        dishes.add("Бургер");
        dishes.add("Салат");
        dishes.add("Стейк");
        dishes.add("Запеканка");
        Thread chef = new Thread(new Chef());
        chef.start();

        for (int i = 1; i <= 3; i++) {
            final int visitorID = i;
            new Thread(() -> {
                System.out.printf("Посетитель %d зашел в кафе\n", visitorID);
                while (true) {
                    synchronized (orders) {
                        while (orders.isEmpty()) {
                            System.out.printf("Еды нет посетитель %d ждет\n", visitorID);
                            try {
                                orders.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        String order = orders.removeFirst();
                        System.out.printf("Посетитель %d съел %s\n", visitorID, order);
                    }
                }
            }).start();
        }
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(3000);
                synchronized (orders) {
                    String dish = dishes.get((int) (Math.random() * 5));
                    System.out.println("Повар приготовил блюдо " + dish);
                    orders.add(dish);
                    orders.notify();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
