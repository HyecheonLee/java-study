package com.hyecheon.dynamic_config_loader;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/20
 */
public class Main {
    private static final Path GAME_CONFIG_PATH = Path.of("resource/game-properties.cfg");
    private static final Path UI_CONFIG_PATH = Path.of("resource/user-interface.cfg");

    public static void main(String[] args) throws Exception {

        final var config = createConfigObject(GameConfig.class, GAME_CONFIG_PATH);
        System.out.println(config);

        final var userInterfaceConfig = createConfigObject(UserInterfaceConfig.class, UI_CONFIG_PATH);
        System.out.println(userInterfaceConfig);
    }

    public static <T> T createConfigObject(Class<T> clazz, Path filePath) throws Exception {
        final var scanner = new Scanner(filePath);

        final var constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);

        final var configInstance = constructor.newInstance();

        while (scanner.hasNextLine()) {
            final var configLine = scanner.nextLine();
            final var nameValuePair = configLine.split("=");
            final var name = nameValuePair[0];
            final var value = nameValuePair[1];
            try {
                final var field = clazz.getDeclaredField(name);
                field.setAccessible(true);

                final Object parsedValue;
                if (field.getType().isArray()) {
                    parsedValue = parseArray(field.getType().getComponentType(), value);
                } else {
                    parsedValue = parseValue(field.getType(), value);
                }

                field.set(configInstance, parsedValue);

            } catch (Exception exception) {
                System.out.println("Property name : %s is unsupported".formatted(name));
                continue;
            }
        }
        return configInstance;
    }

    private static Object parseArray(Class<?> arrayElementType, String value) {
        final var elementValues = value.split(",");

        final var arrayObject = Array.newInstance(arrayElementType, elementValues.length);

        for (int i = 0; i < elementValues.length; i++) {
            Array.set(arrayObject, i, parseValue(arrayElementType, elementValues[i]));
        }

        return arrayObject;
    }

    private static Object parseValue(Class<?> type, String value) {
        if (type.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(short.class)) {
            return Short.parseShort(value);
        } else if (type.equals(long.class)) {
            return Long.parseLong(value);
        } else if (type.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(float.class)) {
            return Float.parseFloat(value);
        } else if (type.equals(String.class)) {
            return value;
        }
        throw new RuntimeException("Type : %s unsupported".formatted(type.getTypeName()));
    }
}
