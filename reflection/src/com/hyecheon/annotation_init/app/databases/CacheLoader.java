package com.hyecheon.annotation_init.app.databases;

import com.hyecheon.annotation_init.annotations.InitializerClass;
import com.hyecheon.annotation_init.annotations.InitializerMethod;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/24
 */
@InitializerClass
public class CacheLoader {

    @InitializerMethod
    public void loadCache() {
        System.out.println("Loading data from cache");
    }

    public void reloadCache() {
        System.out.println("Reload cache");
    }
}
