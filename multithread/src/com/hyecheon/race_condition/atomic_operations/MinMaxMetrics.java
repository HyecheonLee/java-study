package com.hyecheon.race_condition.atomic_operations;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/07
 */
public class MinMaxMetrics {
    volatile long min;
    volatile long max;

    public MinMaxMetrics() {
        min = Long.MAX_VALUE;
        max = Long.MIN_VALUE;
    }

    public void addSample(long newSample) {
        synchronized (this) {
            min = Math.min(newSample, min);
            max = Math.max(newSample, max);
        }
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}
