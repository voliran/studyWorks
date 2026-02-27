package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SmartHome home = new SmartHome();

        SmartLight light = new SmartLight("Люстра",RoomType.BATHROOM);
        SmartThermostat thermostat = new SmartThermostat("Термостат", RoomType.LIVING_ROOM);
        SmartTV TV = new SmartTV("Телевизор", RoomType.BEDROOM);

        light.increaseValue();

        thermostat.increaseValue();

        TV.increaseValue();

        home.addDevice(light);
        home.addDevice(thermostat);
        home.addDevice(TV);

        home.turnAllOn();
        home.getStat().printStat();

        light.turnOff();
        home.getStat().printStat();
    }
}

