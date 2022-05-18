package com.hyecheon.constructors_webserver.init;

import java.net.InetSocketAddress;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/18
 */
public class ServerConfiguration {
    private static ServerConfiguration serverConfigurationInstance;
    private final InetSocketAddress serverAddress;
    private final String greetingMessage;

    public ServerConfiguration(int port, String greetingMessage) {
        this.serverAddress = new InetSocketAddress(port);
        this.greetingMessage = greetingMessage;

        if (serverConfigurationInstance == null) {
            serverConfigurationInstance = this;
        }
    }

    public static ServerConfiguration getInstance() {
        return serverConfigurationInstance;
    }

    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    public String getGreetingMessage() {
        return greetingMessage;
    }
}
