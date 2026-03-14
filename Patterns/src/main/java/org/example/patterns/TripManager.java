package org.example.patterns;

import java.util.ArrayList;
import java.util.List;

public class TripManager implements TripSubject {
    private static TripManager instance;
    private List<TripObserver> observers = new ArrayList<>();
    private Trip currentTrip;

    private TripManager() {}

    public static TripManager getInstance() {
        if (instance == null) {
            instance = new TripManager();
        }
        return instance;
    }
    @Override
    public void addObserver(TripObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(TripObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (TripObserver observer : observers) {
            observer.update(currentTrip);
        }
    }

    public void setCurrentTrip(Trip trip) {
        this.currentTrip = trip;
        notifyObservers();
    }
}
