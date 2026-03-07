package org.example.model;

public class Workspace implements Comparable<Workspace> {

    private int number;
    private String type;
    private boolean isAvailable;

    public Workspace(int number, String type) {
        this.number = number;
        this.type = type;
        this.isAvailable = true;
    }

    @Override
    public int compareTo(Workspace workspace) {
        return Integer.compare(this.number, workspace.number);
    }

    String getType() {
        return this.type;
    }

    int getNumber() {
        return this.number;
    }

    boolean isAvailable() {
        return isAvailable;
    }

    void markAsBooked() {
        this.isAvailable = false;
    }

    void markAsAvailable() {
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return String.format("Добавлено рабочее место: №%d, тип: %s\n", number, type);
    }
}
