package org.example;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
   public static void main(String[] args) {
       int choice;
       Scanner scanner = new Scanner(System.in);

       Set<String> clues = new HashSet<>();
       Set<String> dbClues = new HashSet<>();

       dbClues.add("отпечатки пальцев на двери");
       dbClues.add("аудиозапись");
       dbClues.add("следы губной помады");

       do {
           System.out.println("Добро пожаловать в Детективную игру!");
           System.out.println("""
                   Выберите действие:
                   1 - Добавить улику
                   2 - Проверить наличие улики
                   3 - Удалить улику
                   4 - Сравнить с базой данных
                   5 - Показать все найденные улики
                   6 - Выход""");
           choice = scanner.nextInt();
           scanner.nextLine();

           switch (choice) {
               case 1:
                   System.out.println("Введите улику");
                   clues.add(scanner.nextLine().toLowerCase());
                   break;
               case 2:
                   System.out.println("Введите улику");
                   if (clues.contains(scanner.nextLine().toLowerCase())) {
                       System.out.println("Улика присутствует в списке найденных");
                   } else {
                       System.out.println("Улики нет в списке найденных");
                   }
                   break;
               case 3:
                   System.out.println("Введите улику");
                   String clueToRemove;
                   clueToRemove = scanner.nextLine().toLowerCase();
                   if (clues.contains(clueToRemove)) {
                       clues.remove(clueToRemove);
                       System.out.println("Улика удалена!");
                   } else {
                       System.out.println("Такой улики нет");
                   }
                   break;
               case 4:
                   System.out.println("Совпадения с базой данных:");
                   boolean found = false;
                   for (String clue : clues) {
                       if (dbClues.contains(clue)) {
                           System.out.printf("-%s\n", clue);
                           found = true;
                       }
                   }
                   if (!found) {
                       System.out.println("Совпадений нет");
                   }
                   break;
               case 5:
                   System.out.println("Все найденные улики");
                   for (String clue : clues) {
                       System.out.println(clue);
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
