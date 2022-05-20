package com.hyecheon.json_writer_with_arrays.writer;

import com.hyecheon.json_writer_with_arrays.data.Actor;
import com.hyecheon.json_writer_with_arrays.data.Movie;

import java.lang.reflect.Array;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/20
 */
public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        final var actor = new Actor("Elijah wood", new String[]{"Lord of the Ring", "The Good Son"});

        final var movie = new Movie("Loard of the Rings",
                8.8f,
                new String[]{"Action", "Adventure", "Drama"},
                new Actor[]{actor}
        );

        final var json = objectToJson(movie, 0);

        System.out.println(json);

    }

    public static String objectToJson(Object instance, int indentSize) throws IllegalAccessException {
        final var fields = instance.getClass().getDeclaredFields();


        final var stringBuilder = new StringBuilder();
        stringBuilder.append(indent(indentSize));
        stringBuilder.append("{");
        stringBuilder.append("\n");
        for (int i = 0; i < fields.length; i++) {
            final var field = fields[i];
            field.setAccessible(true);

            field.setAccessible(true);
            if (field.isSynthetic()) {
                continue;
            }
            stringBuilder.append(indent(indentSize + 1));
            stringBuilder.append(formatStringValue(field.getName()));
            stringBuilder.append(":");
            if (field.getType().isPrimitive()) {
                stringBuilder.append(formatPrimitiveValue(field.get(instance), field.getType()));
            } else if (field.getType().equals(String.class)) {
                stringBuilder.append(formatStringValue(field.get(instance).toString()));
            } else if (field.getType().isArray()) {
                stringBuilder.append(arrayToJson(field.get(instance), indentSize + 1));
            } else {
                stringBuilder.append(objectToJson(field.get(instance), indentSize + 1));
            }

            if (fields.length - 1 != i) {
                stringBuilder.append(",");
            }

            stringBuilder.append("\n");
        }

        stringBuilder.append(indent(indentSize));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static String arrayToJson(Object arrayInstance, int indentSize) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();

        int arrayLength = Array.getLength(arrayInstance);

        Class<?> componentType = arrayInstance.getClass().getComponentType();

        stringBuilder.append("[");
        stringBuilder.append("\n");

        for (int i = 0; i < arrayLength; i++) {
            Object element = Array.get(arrayInstance, i);

            if (componentType.isPrimitive()) {
                stringBuilder.append(indent(indentSize + 1));
                stringBuilder.append(formatPrimitiveValue(element, componentType));
            } else if (componentType.equals(String.class)) {
                stringBuilder.append(indent(indentSize + 1));
                stringBuilder.append(formatStringValue(element.toString()));
            } else {
                stringBuilder.append(objectToJson(element, indentSize + 1));
            }

            if (i != arrayLength - 1) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\n");
        }

        stringBuilder.append(indent(indentSize));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static String formatPrimitiveValue(Object instance, Class<?> type) {

        if (type.equals(boolean.class)
                || type.equals(int.class)
                || type.equals(long.class)
                || type.equals(short.class)
        ) {
            return instance.toString();
        } else if (type.equals(double.class) || type.equals(float.class)) {
            return String.format("%.02f", instance);
        }
        throw new RuntimeException(String.format("Type : %s is unsupported", type.getName()));
    }

    private static String formatStringValue(String value) {
        return String.format("""
                "%s"
                """.trim(), value);
    }

    private static String indent(int indentSize) {
        return "\t".repeat(Math.max(0, indentSize));
    }
}
