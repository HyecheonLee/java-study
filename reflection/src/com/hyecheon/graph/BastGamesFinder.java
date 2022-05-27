package com.hyecheon.graph;

import com.hyecheon.graph.database.Database;

import java.util.*;

import static com.hyecheon.graph.annotations.Annotations.*;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/27
 */
public class BastGamesFinder {
    private Database database = new Database();

    @Operation("All-Games")
    public Set<String> getAllGames() {
        return database.readAllGames();
    }

    @Operation("Game-To-Price")
    public Map<String, Float> getGameToPrice(@DependsOn("All-Games") Set<String> allGames) {
        return database.readGameToPrice(allGames);
    }

    @Operation("Game-To-Rating")
    public Map<String, Float> getGameToRating(@DependsOn("All-Games") Set<String> allGames) {
        return database.readGameToRatings(allGames);
    }

    @Operation("Score-To-Game")
    public SortedMap<Double, String> scoreGame(
            @DependsOn("Game-To-Price") Map<String, Float> gameToPrice,
            @DependsOn("Game-To-Rating") Map<String, Float> gameToRating
    ) {
        final var gameToScore = new TreeMap<Double, String>(Collections.reverseOrder());
        for (String gameName : gameToPrice.keySet()) {
            final var score = (double) gameToRating.get(gameName) / gameToPrice.get(gameName);
            gameToScore.put(score, gameName);
        }
        return gameToScore;
    }


    @FinalResult
    public List<String> getToGames(@DependsOn("Score-To-Game") SortedMap<Double, String> gameToScore) {
        return new ArrayList<>(gameToScore.values());
    }
}
