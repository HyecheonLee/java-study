package com.hyecheon.repeatable;

import java.lang.annotation.*;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/28
 */
public @interface Annotations {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ScanPackages {
        String[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ScheduledExecutorClass {

    }

    @Repeatable(ExecutionSchedules.class)
    @Target(ElementType.METHOD)
    public @interface ExecuteOnSchedule {
        int delaySeconds() default 0;

        int periodSeconds();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExecutionSchedules {
        ExecuteOnSchedule[] value();
    }
}
