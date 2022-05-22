package com.hyecheon.polymorphism.logging;

import java.io.IOException;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/22
 */
public class FileLogger {
    public void sendRequest(String data) throws IOException {
        throw new IOException("Failed saving request to a file");
    }
}
