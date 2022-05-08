package com.hyecheon.optimizaing.throughput;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/05
 */
public class ThroughputHttpServer {
    private static final String INPUT_FILE = "./multithread/resource/war_and_peace.txt";
    private static final int NUMBER_OF_THREAD = 1;

    public static void main(String[] args) throws IOException {
        final var text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
        startServer(text);
    }

    private static void startServer(String text) throws IOException {
        final var server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/search", new WordCountHandler(text));
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
        server.setExecutor(executor);
        server.start();
    }

    private static class WordCountHandler implements HttpHandler {
        private String text;

        public WordCountHandler(String text) {
            this.text = text;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            final var query = exchange.getRequestURI().getQuery();
            final var keyValue = query.split("=");
            final var action = keyValue[0];
            final var word = keyValue[1];
            if (!action.equals("word")) {
                exchange.sendResponseHeaders(400, 0);
                return;
            }
            var count = countWord(word);
            final var response = Long.toString(count).getBytes();
            exchange.sendResponseHeaders(200, response.length);
            try (final var responseBody = exchange.getResponseBody()) {
                responseBody.write(response);
            }
        }

        private long countWord(String word) {
            long count = 0;
            int index = 0;
            while (index >= 0) {
                index = text.indexOf(word, index);
                if (index >= 0) {
                    count++;
                    index++;
                }
            }
            return count;
        }
    }
}
