package com.hyecheon.constructors_webserver.web;

import com.hyecheon.constructors_webserver.init.ServerConfiguration;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/18
 */
public class WebServer {
    public void startServer() throws IOException {
        final var serverConfiguration = ServerConfiguration.getInstance();
        final var httpServer = HttpServer.create(serverConfiguration.getServerAddress(), 0);

        httpServer.createContext("/greeting").setHandler(exchange -> {
            final var responseMessage = serverConfiguration.getGreetingMessage();

            exchange.sendResponseHeaders(200, responseMessage.length());
            try (final var responseBody = exchange.getResponseBody()) {
                responseBody.write(responseMessage.getBytes());
                responseBody.flush();
            }
        });

        System.out.printf("Starting server on address %s:%d%n",
                serverConfiguration.getServerAddress().getHostName(),
                serverConfiguration.getServerAddress().getPort()
        );

        httpServer.start();
    }
}
