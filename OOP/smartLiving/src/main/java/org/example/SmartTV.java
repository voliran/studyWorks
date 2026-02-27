package org.example;

public class SmartTV extends SmartDevice implements Controllable {
    private int volume;


    public SmartTV(String name, RoomType room) {
        super(name,room);
        volume = 0;
    }

    @Override
    public void increaseValue() {
        volume = Math.min(volume + 10, 100);
        System.out.println("Уровень громкости: " + volume);
    }

    @Override
    public void decreaseValue() {
        volume = Math.min(Math.max(volume - 10, 0), 100);
        System.out.println("Уровень громкости: " + volume);
    }
}
