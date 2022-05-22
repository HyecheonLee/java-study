package com.hyecheon.polymorphism.http;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/22
 */
public class HttpClient {
    private String serverAddress;

    public HttpClient(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public boolean sendRequest(String data) {
        System.out.println("Request with body : %s was successfully sent to server with address : %s".formatted(data,
                serverAddress));
        return true;
    }
}
