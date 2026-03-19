package org.example;


import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static void main() {
        AtomicInteger counter1 = new AtomicInteger(0);
        AtomicInteger counter2 = new AtomicInteger(0);
        AtomicInteger counter3 = new AtomicInteger(0);

        int[] arr = generateNumber(10, 10000);

        Thread thread1 = new Thread(() -> {
           for (int num : arr) {
               if (num >= 10 && num < 100) {
                   counter1.incrementAndGet();
               }
           }
        });
        Thread thread2 = new Thread(() -> {
            for (int num : arr) {
                if (num >= 100 && num < 1000) {
                    counter2.incrementAndGet();
                }
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int num : arr) {
                if (num >= 1000 && num < 10000) {
                    counter3.incrementAndGet();
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("""
                Двузначных чисел: %s
                Трехзначных чисел: %s
                Четырехзначных чисел: %s""", counter1, counter2, counter3);
    }

    public static int[] generateNumber(int min, int max) {
        int[] arr = new int[100_000];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(min, max);
        }
        return arr;
    }
}
