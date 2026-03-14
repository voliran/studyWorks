package org.example.patterns;

public interface TripSubject {
    void addObserver(TripObserver observer);
    void removeObserver(TripObserver observer);
    void notifyObservers();
}
