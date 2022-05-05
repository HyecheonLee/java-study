package com.hyecheon.creation;

import java.util.ArrayList;
import java.util.List;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/05
 */
public class MultiExecutor {
    private List<Runnable> tasks;

    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    public void executeAll() {
        final List<Thread> threads = new ArrayList<>(tasks.size());
        for (Runnable task : tasks) {
            final Thread thread = new Thread(task);
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
