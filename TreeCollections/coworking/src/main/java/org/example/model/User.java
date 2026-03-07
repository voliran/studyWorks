package org.example.model;

import java.util.TreeSet;
import java.util.UUID;

public class User implements Comparable<User> {

    private String name;
    private String surname;
    private UUID id;
    private TreeSet<Workspace> bookedSeats;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.id = UUID.randomUUID();
        this.bookedSeats = new TreeSet<>();

        System.out.printf("Зарегистрирован новый пользователь: %s %s.\n", this.name, this.surname);
    }

    @Override
    public int compareTo(User user) {
        if (!this.name.equals(user.name)) {
            return this.name.compareTo(user.name);
        } else {
            return this.surname.compareTo(user.surname);
        }
    }

     String getName() {
        return this.name;
    }

     String getSurname() {
        return this.surname;
    }

     TreeSet<Workspace> getBookedSeats() { return this.bookedSeats; }

     void bookWorkspace(Workspace workspace) throws WorkspaceNotAvailableException {
        if (workspace.isAvailable()) {
            bookedSeats.add(workspace);
            workspace.markAsBooked();
            System.out.printf("%s %s бронирует рабочее место №%d\n", name, surname, workspace.getNumber());
        } else {
            throw new WorkspaceNotAvailableException("Место уже зарезервировано");
        }
    }

     void cancelBooking(Workspace workspace) {
        if (bookedSeats.contains(workspace)) {
            System.out.printf("%s %s отменяет бронирование места №%d\n", name, surname, workspace.getNumber());
            workspace.markAsAvailable();
            bookedSeats.remove(workspace);
        } else {
            System.out.println("Пользователь не бронирует это рабочее место");
        }
    }
}
