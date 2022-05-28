package com.hyecheon.repeatable.cache;

import com.hyecheon.repeatable.Annotations;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/28
 */
@Annotations.ScheduledExecutorClass
public class Cache {

    @Annotations.ExecuteOnSchedule(periodSeconds = 5)
    @Annotations.ExecuteOnSchedule(delaySeconds = 10, periodSeconds = 1)
    public static void reloadCache() {
        System.out.println("Reloading cache");
    }
}
