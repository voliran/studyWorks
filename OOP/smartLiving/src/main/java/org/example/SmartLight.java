package org.example;

public class SmartLight extends SmartDevice implements Controllable {
    private int brightness;

    public SmartLight(String name, RoomType room) {
        super(name,room);
        brightness = 0;
    }

    @Override
    public void increaseValue() {
        brightness = Math.min(brightness + 10, 100);
        System.out.println("Уровень яркости: " + brightness);
    }

    @Override
    public void decreaseValue() {
        brightness = Math.max(0, brightness - 10);
        System.out.println("Уровень яркости: " + brightness);
    }
}
