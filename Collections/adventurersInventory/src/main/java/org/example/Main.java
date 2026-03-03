package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int choice;
        Scanner scanner = new Scanner(System.in);

        Map<String, Integer> inventory = new HashMap<>();


        do {
            System.out.println("Добро пожаловать в инвентарь приключенца!");
            System.out.println("""
                    Выберите действие:
                    1 - Добавить новый предмет
                    2 - Изменить количество предметов
                    3 - Удалить предмет
                    4 - Найти предмет по названию
                    5 - Показать весь инвентарь
                    6 - Выход""");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    System.out.print("Введите предмет: ");
                    String itemToAdd = scanner.nextLine().toLowerCase();
                    if (inventory.containsKey(itemToAdd)) {
                        System.out.println("Предмет уже есть в инвентаре!");
                        System.out.println("""
                                Что вы хотите сделать?
                                1 - Добавить
                                2 - Удалить""");
                        int innerChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (innerChoice) {
                            case 1:
                                System.out.print("Введите количество: ");
                                int quantityToAdd = scanner.nextInt();
                                scanner.nextLine();
                                if (quantityToAdd <= 0) {
                                    System.out.println("Введено недопустимое значение");
                                } else {
                                    inventory.put(itemToAdd, inventory.get(itemToAdd) + quantityToAdd);
                                }
                                break;
                            case 2:
                                System.out.print("Введите количество: ");
                                int quantityToRemove = scanner.nextInt();
                                scanner.nextLine();
                                if (quantityToRemove < 0) {
                                    System.out.println("Введено недопустимое значение");
                                } else if (quantityToRemove > inventory.get(itemToAdd)) {
                                    inventory.remove(itemToAdd);
                                    System.out.println("Предмет удален!");
                                } else {
                                    inventory.put(itemToAdd, inventory.get(itemToAdd) - quantityToRemove);
                                }
                                break;
                            default:
                                System.out.println("Введено недопустимое значение");
                        }
                    } else {
                        System.out.print("Введите количество: ");
                        int quantityToAdd = scanner.nextInt();
                        scanner.nextLine();
                        if (quantityToAdd < 0) {
                            System.out.println("Введено недопустимое значение");
                        } else {
                            inventory.put(itemToAdd, quantityToAdd);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Введите предмет: ");
                    String item = scanner.nextLine().toLowerCase();
                    if (inventory.containsKey(item)) {
                        System.out.print("Введите новое количество: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();
                        if (quantity == 0) {
                            inventory.remove(item);
                        } else if (quantity < 0) {
                            System.out.println("Введено недопустимое значение");
                        } else {
                            inventory.put(item,quantity);
                        }
                    } else {
                        System.out.println("Предмета нет в инвентаре");
                    }
                    break;
                case 3:
                    System.out.print("Введите предмет: ");
                    String itemToDel = scanner.nextLine().toLowerCase();
                    if (inventory.containsKey(itemToDel)) {
                        inventory.remove(itemToDel);
                        System.out.println("Предмет удален!");
                    } else {
                        System.out.println("Предмета нет в инвентаре!");
                    }
                    break;
                case 4:
                    System.out.print("Введите предмет: ");
                    String itemToFind = scanner.nextLine().toLowerCase();
                    if (inventory.containsKey(itemToFind)) {
                        System.out.printf("Количество  \"%s\": %d", itemToFind, inventory.get(itemToFind));
                    } else {
                        System.out.println("Предмета нет в инвентаре");
                    }
                    break;
                case 5:
                    System.out.println("Текущий инвентарь:");
                    if (!inventory.isEmpty()) {
                        for(Map.Entry<String, Integer> showItem : inventory.entrySet()) {
                            System.out.printf("%s - %d\n", showItem.getKey(), showItem.getValue());
                        }
                    } else {
                        System.out.println("Инвентарь пуст");
                    }
                    break;
                case 6:
                    System.out.println("Завершаюсь...");
                    break;
                default:
                    System.out.println("Введено недопустимое значение");
            }
            System.out.println();
        } while (choice != 6);

        scanner.close();
    }
}
