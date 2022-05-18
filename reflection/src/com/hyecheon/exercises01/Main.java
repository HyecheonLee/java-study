package com.hyecheon.exercises01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/04/16
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        final Class<String> stringClass = String.class;

        final List<String> list = new ArrayList<>();

        final HashMap<String, Integer> mapObject = new HashMap<>();
        final Class<? extends HashMap> hasMapClass = mapObject.getClass();

        final Class<?> squareClass = Class.forName("com.hyecheon.exercises01.Main$Square");

//        printClassInfo(stringClass, hasMapClass, squareClass);

        final Drawable circleObject = () -> 0;
        printClassInfo(Collections.class, boolean.class, int[][].class, Color.class, circleObject.getClass());
    }

    private static void printClassInfo(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            System.out.println("class name : %s, class package name :%s"
                    .formatted(clazz.getSimpleName(), clazz.getPackageName()));

            final Class<?>[] implementedInterfaces = clazz.getInterfaces();

            for (Class<?> implementedInterface : implementedInterfaces) {
                System.out.println(
                        "class %s implements :%s".formatted(clazz.getSimpleName(), implementedInterface.getSimpleName())
                );
            }
            System.out.println("Is array: " + clazz.isArray());
            System.out.println("Is primitive: " + clazz.isPrimitive());
            System.out.println("Is enum: " + clazz.isEnum());
            System.out.println("Is interface: " + clazz.isInterface());
            System.out.println("Is anonymous: " + clazz.isAnonymousClass());
            System.out.println();
            System.out.println();
        }
    }

    private static class Square implements Drawable {

        @Override
        public int getNumberOfCorners() {
            return 0;
        }
    }

    private static interface Drawable {
        int getNumberOfCorners();
    }

    private enum Color {
        BLUE,
        RED,
        GREEN
    }
}
