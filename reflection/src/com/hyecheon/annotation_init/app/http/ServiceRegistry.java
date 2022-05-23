package com.hyecheon.annotation_init.app.http;

import com.hyecheon.annotation_init.annotations.InitializerClass;
import com.hyecheon.annotation_init.annotations.InitializerMethod;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/24
 */
@InitializerClass
public class ServiceRegistry {

    @InitializerMethod
    public void registerService() {
        System.out.println("Service successfully registered");
    }
}
