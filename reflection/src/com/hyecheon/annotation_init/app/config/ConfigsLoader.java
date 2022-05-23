package com.hyecheon.annotation_init.app.config;

import com.hyecheon.annotation_init.annotations.InitializerClass;
import com.hyecheon.annotation_init.annotations.InitializerMethod;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/24
 */
@InitializerClass
public class ConfigsLoader {

    @InitializerMethod
    public void loadAllConfigs() {
        System.out.println("Loading all configuration files");
    }
}
