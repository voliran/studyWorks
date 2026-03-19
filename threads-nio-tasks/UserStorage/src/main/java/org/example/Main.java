package org.example;

import java.util.Scanner;

public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        int choice;
        do {
            System.out.println("""
                    Выберите действие:
                    1 - Добавить пользователя
                    2 - Показать список пользователей
                    3 - Выход""");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("Введите имя пользователя: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите город пользователя: ");
                    String city = scanner.nextLine();
                    userManager.addUser(name, city);
                }
                case 2 -> {
                    userManager.loadFromFile();
                    userManager.displayAllUsers();
                }
                case 3 -> {
                    System.out.println("Завершаюсь...");
                }
            }
        } while (choice != 3);
    }
}
