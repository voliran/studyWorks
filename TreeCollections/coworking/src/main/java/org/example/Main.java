package org.example;


import org.example.model.CoworkingSystem;
import org.example.model.User;
import org.example.model.UserNotRegisteredException;
import org.example.model.Workspace;

public class Main {
    public static void main(String[] args) {
        CoworkingSystem coworkingSystem = new CoworkingSystem();

        User user1 = new User("Антон", "Иванов");
        User user2 = new User("Михаил", "Говорунов");

        Workspace workspace1 = new Workspace(1, "стандартное");
        Workspace workspace2 = new Workspace(2, "переговорная комната");
        Workspace workspace3 = new Workspace(3, "VIP");

        coworkingSystem.registerUser(user1);
        coworkingSystem.registerUser(user2);

        coworkingSystem.addWorkspace(workspace1);
        coworkingSystem.addWorkspace(workspace2);
        coworkingSystem.addWorkspace(workspace3);

        try {
            coworkingSystem.bookWorkspace(user1, workspace1);
        } catch (UserNotRegisteredException e) {
            System.out.println("Пользователь не зарегестрирован в системе.");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка.");
        }

        try {
            coworkingSystem.bookWorkspace(user2, workspace2);
        } catch (UserNotRegisteredException e) {
            System.out.println("Пользователь не зарегестрирован в системе.");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка.");
        }

        try {
            coworkingSystem.bookWorkspace(user1, workspace3);
        } catch (UserNotRegisteredException e) {
            System.out.println("Пользователь не зарегестрирован в системе.");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка.");
        }

        coworkingSystem.allAvailableSeats();

        try {
            coworkingSystem.cancelBooking(user1, workspace1);
        } catch (UserNotRegisteredException e) {
            System.out.println("Пользователь не зарегестрирован в системе.");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка.");
        }


        coworkingSystem.allAvailableSeats();
    }
}
