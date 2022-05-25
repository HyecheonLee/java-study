package com.hyecheon.annotation_init.app.databases;

import com.hyecheon.annotation_init.annotations.InitializerClass;
import com.hyecheon.annotation_init.annotations.InitializerMethod;
import com.hyecheon.annotation_init.annotations.RetryOperation;

import java.io.IOException;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/24
 */
@InitializerClass
public class DatabaseConnection {
    private int filCounter = 5;

    @RetryOperation(
            numberOfRetries = 10,
            retryException = IOException.class,
            durationBetweenRetriesMs = 1000,
            failureMessage = "Connection to databse 1 failed after retries"
    )
    @InitializerMethod
    public void connectToDatabase1() throws IOException {
        System.out.println("Connecting to database 1");
        if (filCounter > 0) {
            filCounter--;
            throw new IOException("Connection failed");
        }
        System.out.println("Connection to database 1 succeeded");
    }

    @InitializerMethod
    public void connectToDatabase2() {
        System.out.println("Connecting to database 2");
    }
}
