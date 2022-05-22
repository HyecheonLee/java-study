package com.hyecheon.polymorphism.database;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/22
 */
public class DatabaseClient {

    public boolean storeData(String data) {
        System.out.println("Data : %s was successfully stored in the database".formatted(data));
        return true;
    }
}
