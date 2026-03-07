package org.example;

import org.example.model.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        RankingSystem rankingSystem = new RankingSystem();
        Player player1 = new Player(1, "Peter", 1550);
        Player player2 = new Player(2, "Shon", 1200);
        Player player3 = new Player(3, "Olga", 1000);
        Player player4 = new Player(4, "Oleg", 1650);
        Player player5 = new Player(5, "Denis", 1660);
        Player player6 = new Player(6, "Sebastian", 1700);

        try {
            rankingSystem.addPlayer(player1);
            rankingSystem.addPlayer(player2);
            rankingSystem.addPlayer(player3);
            rankingSystem.addPlayer(player4);
            rankingSystem.addPlayer(player5);
            rankingSystem.addPlayer(player6);
        } catch (DuplicatePlayerException e) {
            System.out.println("Игрок с таким id уже существует");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка");
        }


        printTopPlayers(rankingSystem.getTopPlayers(2));

        try {
            rankingSystem.updatePlayerRating(3, 1760);
        } catch (PlayerNotFoundException e) {
            System.out.println("Игрока с таким id не найдено");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка");
        }

        System.out.println("Топ 2 после обновления");

        printTopPlayers(rankingSystem.getTopPlayers(2));

        try {
            System.out.println(rankingSystem.getPlayerRank(4));
        } catch (PlayerNotFoundException e) {
            System.out.println("Игрок с таким id не найден");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка");
        }


        try {
            rankingSystem.getPlayerRank(1000);
        } catch (PlayerNotFoundException e) {
            System.out.println("Игрок с таким id не найден");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка");
        }

    }

    public static void printTopPlayers(List<Player> players) {
        System.out.printf("Топ %d игрок(а)\n", players.size());
        int place = 1;
        for (Player player : players) {
            System.out.printf("%d. %s - %d\n", place, player.getName(), player.getRating());
        }
    }

}

