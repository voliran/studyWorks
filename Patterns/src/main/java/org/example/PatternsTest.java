package org.example;

import org.example.patterns.TripManager;
import org.example.patterns.TripFactory;
import org.example.patterns.Trip;
import org.example.patterns.TripObserver;

public class PatternsTest {
        public static void main(String[] args) {

            TripManager m1 = TripManager.getInstance();
            TripManager m2 = TripManager.getInstance();
            System.out.println("Singleton работает: " + (m1 == m2));

            m1.addObserver(new TripObserver() {
                public void update(Trip t) {
                    System.out.println("Уведомление получено!");
                    t.planTrip();
                }
            });

            TripFactory f = new TripFactory();
            Trip t1 = f.createTrip("бизнес");
            Trip t2 = f.createTrip("отпуск");

            System.out.println("\nТест 1:");
            m1.setCurrentTrip(t1);

            System.out.println("\nТест 2:");
            m1.setCurrentTrip(t2);
        }
}

