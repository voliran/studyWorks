package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int choice;
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        List<String> characters = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        List<String> places = new ArrayList<>();
        List<String> generatedHistories = new ArrayList<>();

        characters.add("Дракон");
        characters.add("Король");
        characters.add("Гоблин");

        actions.add("сражается");
        actions.add("гуляет");
        actions.add("спит");

        places.add("в лесу");
        places.add("на кухне");
        places.add("в городе");



        do {
            System.out.println("Добро пожаловать в генератор случайных историй!");
            System.out.printf("У нас есть персонажи (%d шт.), действия (%d шт.), места (%d шт.).\n", characters.size(), actions.size(), places.size());
            System.out.println("""
                Выберите пункт:
                1 - Добавить персонажа
                2 - Добавить действие
                3 - Добавить место
                4 - Сгенерировать историю
                5 - Посмотреть все истории
                6 - Выход""");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice) {
                case 1:
                    System.out.print("Введите персонажа: ");
                    characters.add(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Введите действие: ");
                    actions.add(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Введите место: ");
                    places.add(scanner.nextLine());
                    break;
                case 4:
                    if (characters.isEmpty() || actions.isEmpty() || places.isEmpty()) {
                        System.out.println("Недостаточно данных");
                    } else {
                        String generatedHistory = characters.get(rand.nextInt(0, characters.size())) + " " + actions.get(rand.nextInt(0, actions.size())) + " " + places.get(rand.nextInt(0,places.size()));
                        System.out.printf("Сгенерированная история: %s", generatedHistory);
                        generatedHistories.add(generatedHistory);
                    }
                    break;
                case 5:
                    System.out.println("Список сгенерированных историй");
                    if (generatedHistories.isEmpty()) {
                        System.out.println("Историй нет");
                    } else {
                        for (String storie : generatedHistories) {
                            int index = generatedHistories.indexOf(storie) + 1;
                            System.out.println(index + ". " + storie);
                        }
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
