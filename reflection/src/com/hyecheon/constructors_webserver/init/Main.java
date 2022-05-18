package com.hyecheon.constructors_webserver.init;

import com.hyecheon.constructors_webserver.web.WebServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/18
 */
public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        initConfiguration();

        final var webServer = new WebServer();
        webServer.startServer();
    }

    public static void initConfiguration() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final var constructor = ServerConfiguration.class.getDeclaredConstructor(int.class, String.class);

        constructor.setAccessible(true);
        constructor.newInstance(8080, "Good Day!");
    }
}
