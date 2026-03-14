package org.example;

import java.io.*;

public class SerializeTrip {

    public static void main(String[] args) {

        Trip trip = new Trip.Builder()
                .destination("Moscow")
                .duration(10)
                .build();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("trip.ser"))) {
            oos.writeObject(trip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("trip.ser"))) {
            Trip newTrip = (Trip) ois.readObject();
            System.out.println("Куда: " + newTrip.getDestination() + " путь займет: " + newTrip.getDuration());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
