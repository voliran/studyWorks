package org.example;

import java.util.ArrayList;
import java.util.List;

public class SmartHome {
    List<SmartDevice> devices;

    public SmartHome() {
        this.devices = new ArrayList<>();
    }

    public void addDevice(SmartDevice device) {
        devices.add(device);
    }

    public void turnAllOn() {
        for (SmartDevice device : devices) {
            device.turnOn();
        }
        System.out.println("Все устройства включены");
    }

    public void turnAllOff() {
        for (SmartDevice device : devices) {
            device.turnOff();
        }
        System.out.println("Все устройства выключены");
    }

    public HomeStats getStat() {
        int totalDevices = devices.size();
        int workingDevices = 0;
        for (SmartDevice device : devices) {
            if (device.isOn()) {
                workingDevices++;
            }
        }
        return new HomeStats(totalDevices, workingDevices);
    }

    public static class HomeStats {
        private final int totalDevices;
        private final int workingDevices;

        public HomeStats(int totalDevices, int workingDevices) {
            this.totalDevices = totalDevices;
            this.workingDevices = workingDevices;
        }

        public void printStat() {
            System.out.println("Всего устройств: " + totalDevices);
            System.out.println("Включенных устройств: " + workingDevices);
        }
    }
}
