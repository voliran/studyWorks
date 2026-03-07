package org.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class CoworkingSystemTest {

    private CoworkingSystem coworkingSystem;
    private User user1;
    private User user2;
    private Workspace workspace1;
    private Workspace workspace2;

    @BeforeEach
    void setUp() {
        coworkingSystem = new CoworkingSystem();
        user1 = new User("Иванов", "Иван");
        user2 = new User("Антон", "Максимов");
        workspace1 = new Workspace(1, "стандартное");
        workspace2 = new Workspace(2, "переговорная комната");
    }

    @Test
    void addWorkspace() {
        coworkingSystem.addWorkspace(workspace1);

        Assertions.assertTrue(coworkingSystem.getRegisteredSeats().contains(workspace1));
        Assertions.assertFalse(coworkingSystem.getRegisteredSeats().contains(workspace2));
    }

    @Test
    void removeWorkSpace() {
        coworkingSystem.addWorkspace(workspace1);

        Assertions.assertTrue(coworkingSystem.getRegisteredSeats().contains(workspace1));

        coworkingSystem.removeWorkSpace(workspace1);

        Assertions.assertFalse(coworkingSystem.getRegisteredSeats().contains(workspace1));
    }

    @Test
    void registerUser() {
        coworkingSystem.registerUser(user1);

        Assertions.assertTrue(coworkingSystem.getRegisteredUsers().contains(user1));
        Assertions.assertFalse(coworkingSystem.getRegisteredUsers().contains(user2));
    }

    @Test
    void bookWorkspace() {
        coworkingSystem.addWorkspace(workspace1);
        coworkingSystem.registerUser(user1);

        try {
            coworkingSystem.bookWorkspace(user1, workspace1);
        } catch (UserNotRegisteredException e) {
            System.out.println("Пользователь не зарегистрирован в системе.");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка.");
        }

        Map<User, TreeSet<Workspace>> bookings = coworkingSystem.getAllBookings();
        Assertions.assertTrue(bookings.containsKey(user1));
        TreeSet<Workspace> userBookings = bookings.get(user1);
        Assertions.assertNotNull(userBookings);
        Assertions.assertTrue(userBookings.contains(workspace1));
        Assertions.assertFalse(workspace1.isAvailable());
    }

    @Test
    void cancelBooking() {
        coworkingSystem.addWorkspace(workspace1);
        coworkingSystem.addWorkspace(workspace2);
        coworkingSystem.registerUser(user1);

        try {
            coworkingSystem.bookWorkspace(user1, workspace1);
        } catch (UserNotRegisteredException e) {
            System.out.println("Пользователь не зарегистрирован в системе.");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка.");
        }

        try {
            coworkingSystem.bookWorkspace(user1, workspace2);
        } catch (UserNotRegisteredException e) {
            System.out.println("Пользователь не зарегистрирован в системе.");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка.");
        }

        Map<User, TreeSet<Workspace>> bookings = coworkingSystem.getAllBookings();
        TreeSet<Workspace> userBookings = bookings.get(user1);
        Assertions.assertTrue(userBookings.contains(workspace2));
        Assertions.assertFalse(workspace2.isAvailable());

        try {
            coworkingSystem.cancelBooking(user1, workspace2);
        } catch (UserNotRegisteredException e) {
            System.out.println("Пользователь не зарегестрирован в системе.");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка.");
        }

        Assertions.assertFalse(userBookings.contains(workspace2));
        Assertions.assertTrue(workspace2.isAvailable());
    }

}