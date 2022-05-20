package com.hyecheon.dynamic_config_loader;

import java.util.concurrent.ThreadLocalRandom;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/20
 */
public class GameConfig {
    private final int releaseYear = 2000;
    private String gameName;
    private double price;


    public GameConfig() {
//        this.releaseYear = ThreadLocalRandom.current().nextInt(2000);
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getGameName() {
        return gameName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "GameConfig{" +
                "releaseYear=" + releaseYear +
                ", gameName='" + gameName + '\'' +
                ", price=" + price +
                '}';
    }
}
