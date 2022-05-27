package com.hyecheon.graph.database;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/27
 */
public class Database {

    private Map<String, List<Float>> GAME_TO_PRICE = Map.of(
            "Fortnite", Arrays.asList(5f, 10f),
            "Minecraft", Arrays.asList(4.3f, 100f),
            "League Of Legends", Arrays.asList(4.9f, 89f),
            "Ace Combat", Arrays.asList(4.8f, 50f),
            "StartCraft", Arrays.asList(4.7f, 66f),
            "Burnout", Arrays.asList(4.4f, 31f)
    );

    public Set<String> readAllGames() {
        return GAME_TO_PRICE.keySet();
    }

    public Map<String, Float> readGameToRatings(Set<String> games) {
        return GAME_TO_PRICE.entrySet().stream().filter(entry -> games.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get(0)));
    }


    public Map<String, Float> readGameToPrice(Set<String> games) {
        return GAME_TO_PRICE.entrySet()
                .stream()
                .filter(entry -> games.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get(1)));
    }

}
