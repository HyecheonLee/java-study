package com.hyecheon.annotation_init.app.databases;

import com.hyecheon.annotation_init.annotations.InitializerClass;
import com.hyecheon.annotation_init.annotations.InitializerMethod;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/24
 */
@InitializerClass
public class DatabaseConnection {

    @InitializerMethod
    public void connectToDatabase1() {
        System.out.println("Connecting to database 1");
    }

    @InitializerMethod
    public void connectToDatabase2() {
        System.out.println("Connecting to database 2");
    }
}
