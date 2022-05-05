package com.hyecheon.creation;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/05
 */
public class Main2 {
    public static void main(String[] args) {
        final Thread thread = new Thread(() -> {
            throw new RuntimeException("International Exception");
        });
        thread.setName("Misbehaving thread");
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("A critical error happened in thread" + t.getName() + " the error is " + e.getMessage());

        });
        thread.start();
    }
}
