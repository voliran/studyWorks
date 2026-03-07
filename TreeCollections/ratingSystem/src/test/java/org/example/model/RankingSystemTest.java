package org.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RankingSystemTest {

    private RankingSystem rankingSystem;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @BeforeEach
    void setUp() {
        rankingSystem = new RankingSystem();
        player1 = new Player(1, "Alexander", 1200);
        player2 = new Player(2, "Andrey", 1300);
        player3 = new Player(3, "Maxim", 1130);
        player4 = new Player(4, "Albert", 1000);
    }

    @Test
    void addPlayer() {
        try {
            rankingSystem.addPlayer(player1);
            rankingSystem.addPlayer(player2);
            rankingSystem.addPlayer(player3);
            rankingSystem.addPlayer(player4);
        } catch (DuplicatePlayerException e) {
            System.out.println("Игрок с таким id уже существует");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка");
        }

        Assertions.assertEquals(4, rankingSystem.getTotalPlayers());
        Assertions.assertTrue(rankingSystem.hasPlayer(3));
        Assertions.assertFalse(rankingSystem.hasPlayer(5));
    }

    @Test
    void updatePlayerRating() {
       try {
           rankingSystem.addPlayer(player1);
       } catch (DuplicatePlayerException e) {
           System.out.println("Игрок с таким id уже существует");
       } catch (Exception e) {
           System.out.println("Произошла неизвестная ошибка");
       }

       Assertions.assertEquals(1200, player1.getRating());

       try {
           rankingSystem.updatePlayerRating(1, 1300);
       } catch (PlayerNotFoundException e) {
           System.out.println("Игрока с таким id не найдено");
       } catch (Exception e) {
           System.out.println("Произошла неизвестная ошибка");
       }

       Assertions.assertEquals(1300,player1.getRating());
    }

    @Test
    void getTopPlayers() {
        try {
            rankingSystem.addPlayer(player1);
            rankingSystem.addPlayer(player2);
            rankingSystem.addPlayer(player3);
            rankingSystem.addPlayer(player4);
        } catch (DuplicatePlayerException e) {
            System.out.println("Игрок с таким id уже существует");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка");
        }


        List<Player> top3 = rankingSystem.getTopPlayers(3);
        assertEquals(3, top3.size());
        assertEquals(1300, top3.get(0).getRating());
        assertEquals(1200, top3.get(1).getRating());
        assertEquals(1130, top3.get(2).getRating());
    }

    @Test
    void getPlayerRank() {
        try {
            rankingSystem.addPlayer(player1);
            rankingSystem.addPlayer(player2);
            rankingSystem.addPlayer(player3);
            rankingSystem.addPlayer(player4);
        } catch (DuplicatePlayerException e) {
            System.out.println("Игрок с таким id уже существует");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка");
        }

        try {
            String test = rankingSystem.getPlayerRank(1);
            Assertions.assertEquals("Текущий ранг Alexander: 2", test);
        } catch (PlayerNotFoundException e) {
            System.out.println("Игрок с таким id не найден");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка");
        }
    }

    @Test
    void getTotalPlayers() {
        try {
            rankingSystem.addPlayer(player1);
            rankingSystem.addPlayer(player2);
            rankingSystem.addPlayer(player3);
            rankingSystem.addPlayer(player4);
        } catch (DuplicatePlayerException e) {
            System.out.println("Игрок с таким id уже существует");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка");
        }

        int total = rankingSystem.getTotalPlayers();

        Assertions.assertEquals(4, total);
    }

    @Test
    void hasPlayer() {
        try {
            rankingSystem.addPlayer(player1);
            rankingSystem.addPlayer(player2);
        } catch (DuplicatePlayerException e) {
            System.out.println("Игрок с таким id уже существует");
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка");
        }

        Assertions.assertTrue(rankingSystem.hasPlayer(2));
        Assertions.assertFalse(rankingSystem.hasPlayer(4));
    }
}