package org.example;

import java.util.ArrayList;
import java.util.List;

public class LeakMemory {

    public static void main(String[] args) {
        List<Trip> tripList = new ArrayList<>();

        System.out.println("ТЕСТ С УТЕЧКОЙ ПАМЯТИ");
        System.out.println("Память до добавления элементов");
        printMemory();

        for (int i = 0; i < 1_000_000; i++) {
            tripList.add(new Trip.Builder()
                    .destination("Moscow")
                    .duration(10)
                    .build());
        }
        System.out.println("Память после добавления 1000000 элементов");
        printMemory();

        System.gc();

        System.out.println("Память после System.gc()");
        printMemory();
    }

    private static void printMemory() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Используемая память: " + usedMemory / 1024 + " KB");
    }
}
