package com.hyecheon.annotation_init.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryOperation {
    Class<? extends Throwable>[] retryException() default {Exception.class};

    long durationBetweenRetriesMs() default 0;

    String failureMessage() default "Operation failed after retrying";

    int numberOfRetries();
}