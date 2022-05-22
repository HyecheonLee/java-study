package com.hyecheon.polymorphism.udp;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/22
 */
public class UdpClient {
    public void sendAdnForget(String requestPayload) {
        System.out.println("Request : %s was sent through UDP".formatted(requestPayload));
    }
}
