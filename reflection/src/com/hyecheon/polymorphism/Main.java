package com.hyecheon.polymorphism;

import com.hyecheon.polymorphism.database.DatabaseClient;
import com.hyecheon.polymorphism.http.HttpClient;
import com.hyecheon.polymorphism.logging.FileLogger;
import com.hyecheon.polymorphism.udp.UdpClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/22
 */
public class Main {
    public static void main(String[] args) throws Throwable {

        final var storeData = DatabaseClient.class.getDeclaredMethod("storeData");


        final var databaseClient = new DatabaseClient();
        final var httpClient1 = new HttpClient("123.456.789.0");
        final var httpClient2 = new HttpClient("11.33.55.0");
        final var fileLogger = new FileLogger();
        final var udpClient = new UdpClient();

        final var requestBody = "request data";

        final var methodParameterTypes = Arrays.asList(new Class<?>[]{String.class});

        final var requestExecutors = groupExecutor(Arrays.asList(databaseClient, httpClient1, httpClient2, fileLogger, udpClient), methodParameterTypes);

        executeAll(requestExecutors, requestBody);

    }

    public static void executeAll(Map<Object, Method> requestExecutors, Object... arguments) throws Throwable {
        try {
            for (Map.Entry<Object, Method> requestExecutorEntry : requestExecutors.entrySet()) {
                final var requestExecutor = requestExecutorEntry.getKey();
                final var method = requestExecutorEntry.getValue();

                final Object result;

                result = method.invoke(requestExecutor, arguments);
                if (result != null && result.equals(Boolean.FALSE)) {
                    System.out.println("Received negative result. Aborting...");
                    return;
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Map<Object, Method> groupExecutor(List<Object> requestExecutors, List<Class<?>> methodParameterTypes) {
        final var instanceToMethod = new HashMap<Object, Method>();

        for (Object requestExecutor : requestExecutors) {
            final var allMethods = requestExecutor.getClass().getDeclaredMethods();
            for (Method method : allMethods) {
                if (Arrays.asList(method.getParameterTypes()).equals(methodParameterTypes)) {
                    instanceToMethod.put(requestExecutor, method);
                }
            }
        }
        return instanceToMethod;
    }
}
