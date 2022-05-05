package com.hyecheon.creation;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/05
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread(() -> {
            //code that will run in a new thread
            System.out.printf("We are now in thread %s%n", Thread.currentThread().getName());
            System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
        });

        thread.setName("New Worker Thread");

        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.printf("We are in thread: %s before starting a new thread%n", Thread.currentThread().getName());
        thread.start();
        System.out.printf("We are in thread: %s after starting a new thread%n", Thread.currentThread().getName());
        Thread.sleep(10000);
    }
}
