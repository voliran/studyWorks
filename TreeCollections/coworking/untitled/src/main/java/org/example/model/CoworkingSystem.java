package org.example.model;

import com.sun.source.util.Trees;

import java.util.TreeMap;
import java.util.TreeSet;

public class CoworkingSystem implements BookingManager {

    private TreeSet<Workspace> registeredSeats;
    private TreeSet<User> registeredUsers;
    private TreeMap<User, TreeSet<Workspace>> allBookings;

    public CoworkingSystem() {
        this.registeredSeats = new TreeSet<>();
        this.registeredUsers = new TreeSet<>();
        this.allBookings = new TreeMap<>();
    }

    public TreeSet<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public TreeSet<Workspace> getRegisteredSeats() {
        return registeredSeats;
    }

    public TreeMap<User, TreeSet<Workspace>> getAllBookings() {
        return allBookings;
    }

    public void addWorkspace(Workspace workspace) {
        if (!registeredSeats.contains(workspace)) {
            this.registeredSeats.add(workspace);
            System.out.printf("Место №%d добавлено в систему.\n", workspace.getNumber());
        } else {
            System.out.println("Место уже в системе.");
        }
    }

    public void removeWorkSpace(Workspace workspace) {
        if (registeredSeats.contains(workspace)) {
            this.registeredSeats.remove(workspace);
            System.out.printf("\nМесто №%d удалено из системы.\n", workspace.getNumber());
        } else {
            System.out.println("Такого места нет в системе.");
        }
    }

    public void registerUser(User user) {
        if (!this.registeredUsers.contains(user)) {
            this.registeredUsers.add(user);
            System.out.printf("Пользователь %s %s добавлен в систему.\n", user.getName(), user.getSurname());
        } else {
            System.out.println("Пользователь уже в системе.");
        }
    }

    @Override
    public void bookWorkspace(User user, Workspace workspace) throws UserNotRegisteredException {
        if (!registeredUsers.contains(user)) {
            throw new UserNotRegisteredException("Пользователь не зарегестрирован.");
        }

        if (!registeredSeats.contains(workspace)) {
            System.out.println("Место не добавлено в систему.");
            return;
        }

        if (workspace.isAvailable()) {
            try {
                user.bookWorkspace(workspace);
            } catch (WorkspaceNotAvailableException e) {
                System.out.println("Место уже зарезервировано.");
            } catch (Exception e) {
                System.out.println("Произошла неизвестная ошибка.");
            }


            allBookings.put(user, user.getBookedSeats());
        }
    }

    @Override
    public void cancelBooking(User user, Workspace workspace) throws UserNotRegisteredException {
        if (!registeredUsers.contains(user)) {
            throw new UserNotRegisteredException("Пользователь не зарегестрирован.");
        }

        if (!registeredSeats.contains(workspace)) {
            System.out.println("Место не добавлено в систему.");
            return;
        }

        if (user.getBookedSeats().contains(workspace)) {
            user.cancelBooking(workspace);

            if (user.getBookedSeats().isEmpty()) {
                allBookings.remove(user);
            } else {
                allBookings.put(user, user.getBookedSeats());
            }
        }
    }

    public void allAvailableSeats() {
        System.out.println("Список доступных рабочих мест:");
        int index = 1;
        for (Workspace workspace : registeredSeats) {
            if (workspace.isAvailable()) {
                System.out.printf("%d. №%d, тип: %s\n", index, workspace.getNumber(), workspace.getType());
                index++;
            }
        }

        if (index == 1) {
            System.out.println("[пусто]");
        }
    }
}
