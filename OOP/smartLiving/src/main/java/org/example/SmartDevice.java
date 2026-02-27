package org.example;

public abstract class SmartDevice {
    private String name;
    private boolean isOn;
    private RoomType room;

    protected SmartDevice(String name, RoomType room) {
        this.name = name;
        this.room = room;
        this.isOn = false;
    }

    public final void turnOn() {
        isOn = true;
    }

    public final void turnOff() {
        isOn = false;
    }

    public final boolean isOn() {
        return this.isOn;
    }
}
