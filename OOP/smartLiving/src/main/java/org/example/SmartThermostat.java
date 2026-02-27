package org.example;

public class SmartThermostat extends SmartDevice implements Controllable {
    private int temp;

    public SmartThermostat(String name, RoomType room) {
        super(name, room);
        temp = 15;
    }

    @Override
    public void increaseValue() {
        temp = Math.min(temp + 1, 30);
        System.out.println("Температура термостата: " + temp);
    }

    @Override
    public void decreaseValue() {
        temp = Math.max(15, temp - 1);
        System.out.println("Температура термостата: " + temp);
    }
}
