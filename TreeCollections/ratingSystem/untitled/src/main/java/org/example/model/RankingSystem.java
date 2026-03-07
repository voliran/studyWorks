package org.example.model;

import java.util.*;

public class RankingSystem {

    private TreeMap<Integer, Set<Player>> playerRankings;
    private Map<Integer, Player> playersById;

    public RankingSystem() {
        this.playerRankings = new TreeMap<>(Collections.reverseOrder());
        this.playersById = new HashMap<>();
    }

    public void addPlayer(Player player) throws DuplicatePlayerException {

        if (playersById.containsKey(player.getId())) {
            throw new DuplicatePlayerException("Игрок с ID " + player.getId() + " уже существует");
        }

        playersById.put(player.getId(), player);

        playerRankings.computeIfAbsent(player.getRating(), k -> new HashSet<>()).add(player);
        System.out.printf("Добавлен игрок: %s (ID: %d, Рейтинг: %d)\n", player.getName(), player.getId(), player.getRating());
    }

    public void updatePlayerRating(int playerId, int newRating) throws PlayerNotFoundException {
        Player player = playersById.get(playerId);
        if (player == null) {
            throw new PlayerNotFoundException("Игрок с ID " + playerId + " не найден");
        }

        int oldRating = player.getRating();

        if (oldRating == newRating) {
            return;
        }

        Set<Player> oldRatingGroup = playerRankings.get(oldRating);
        if (oldRatingGroup != null) {
            oldRatingGroup.remove(player);
            if (oldRatingGroup.isEmpty()) {
                playerRankings.remove(oldRating);
            }
        }

        player.setRating(newRating);

        playerRankings.computeIfAbsent(newRating, key -> new HashSet<>()).add(player);

        System.out.printf("Обновлен рейтинг игрока %s: %d\n", player.getName(), player.getRating());
    }

    public List<Player> getTopPlayers(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Количество игроков должно быть положительным числом");
        }


        List<Player> topPlayers = new ArrayList<>();
        int playersNeeded = n;

        for (Map.Entry<Integer, Set<Player>> entry : playerRankings.entrySet()) {
            Set<Player> playersWithRating = entry.getValue();

            for (Player player : playersWithRating) {
                if (playersNeeded > 0) {
                    topPlayers.add(player);
                    playersNeeded--;
                } else {
                    break;
                }
            }

            if (playersNeeded == 0) {
                break;
            }
        }

        return topPlayers;
    }

    public String getPlayerRank(int playerId) throws PlayerNotFoundException {
        Player player = playersById.get(playerId);
        if (player == null) {
            throw new PlayerNotFoundException("Ошибка: игрок с ID " + playerId + " не найден в системе");
        }

        int rank = 1;
        int playerRating = player.getRating();

        for (Map.Entry<Integer, Set<Player>> entry : playerRankings.entrySet()) {
            int rating = entry.getKey();

            if (rating == playerRating) {
                return "Текущий ранг " + player.getName() + ": " + rank;
            }

            rank += entry.getValue().size();
        }

        return "";
    }

    public int getTotalPlayers() {
        return playersById.size();
    }

    public boolean hasPlayer(int playerId) {
        return playersById.containsKey(playerId);
    }
}

