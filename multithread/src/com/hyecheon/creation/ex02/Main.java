package com.hyecheon.creation.ex02;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/05
 */
public class Main {
    public static void main(String[] args) {
        final Thread thread = new NewThread();

        thread.start();
    }

    private static class NewThread extends Thread {
        @Override
        public void run() {
            System.out.printf("Hello from %s%n", this.getName());

        }
    }

}
