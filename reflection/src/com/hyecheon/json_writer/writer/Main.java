package com.hyecheon.json_writer.writer;

import com.hyecheon.json_writer.data.Address;
import com.hyecheon.json_writer.data.Company;
import com.hyecheon.json_writer.data.Person;

import java.lang.reflect.Field;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/20
 */
public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        final var address = new Address("Main Street", (short) 1);
        var json = objectToJson(address, 0);
        System.out.println(json);

        final var company = new Company("Udemy", "San Francisco", new Address("Harrison Street", (short) 600));

        final var person = new Person("John", true, 29, 100.555f, address, company);
        json = objectToJson(person, 0);
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
            if (field.isSynthetic()) {
                continue;
            }
            stringBuilder.append(indent(indentSize + 1));
            stringBuilder.append(formatStringValue(field.getName()));
            stringBuilder.append(":");
            if (field.getType().isPrimitive()) {
                stringBuilder.append(formatPrimitiveValue(field, instance));
            } else if (field.getType().equals(String.class)) {
                stringBuilder.append(formatStringValue(field.get(instance).toString()));
            } else {
                stringBuilder.append(objectToJson(field.get(instance), indentSize + 1));
            }
            if (fields.length - 1 != i) {
                stringBuilder.append(",");
            }
            stringBuilder.append('\n');
        }
        stringBuilder.append(indent(indentSize));
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    private static String formatPrimitiveValue(Field field, Object parentInstance) throws IllegalAccessException {
        if (field.getType().equals(boolean.class)
                || field.getType().equals(int.class)
                || field.getType().equals(long.class)
                || field.getType().equals(short.class)
        ) {
            return field.get(parentInstance).toString();
        } else if (field.getType().equals(double.class) || field.getType().equals(float.class)) {
            return String.format("%.02f", field.get(parentInstance));
        }
        throw new RuntimeException(String.format("Type : %s is unsupported", field.getType().getName()));
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
