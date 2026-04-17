package org.example;

public class FragileDistanceExceededException extends RuntimeException {
    public FragileDistanceExceededException() {
        super("Нет возможности доставлять хрупкие грузы на расстояние свыше 30км.");
    }
}
