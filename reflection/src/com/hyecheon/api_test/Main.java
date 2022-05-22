package com.hyecheon.api_test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/22
 */
public class Main {
    public static void main(String[] args) {
        testGetters(Product.class);
        testGetters(Product.class);

    }

    public static void testSetters(Class<?> dataClass) {
        final var fields = getAllFields(dataClass);

        for (Field field : fields) {
            final var setterName = "set %s".formatted(capitalizeFirstLetter(field.getName()));

            try {
                final var setterMethod = dataClass.getMethod(setterName, field.getType());
                if (!setterMethod.getReturnType().equals(void.class)) {
                    throw new IllegalStateException("Setter method %s has to return void".formatted(setterName));
                }
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("Setter : %s not found".formatted(setterName));
            }

        }
    }

    public static void testGetters(Class<?> dataClass) {
        final var fields = getAllFields(dataClass);

        final var methodNameToMethod = mapMethodNameToMethod(dataClass);
        for (Field field : fields) {
            final var getterName = "get%s".formatted(capitalizeFirstLetter(field.getName()));
            if (!methodNameToMethod.containsKey(getterName)) {
                throw new IllegalStateException("Field : %s doesn't have a getter method".formatted(field.getName()));
            }
            final var getter = methodNameToMethod.get(getterName);
            if (!getter.getReturnType().equals(field.getType())) {
                throw new IllegalStateException(
                        "Getter method : %s() has return type %s but expected %s".formatted(getter.getName(),
                                getter.getReturnType().getTypeName(), field.getType().getTypeName())
                );
            }
            if (getter.getParameterCount() > 0) {
                throw new IllegalStateException("Getter : %s has %d arguments".formatted(getterName, getter.getParameterCount()));
            }
        }
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        if (clazz == null || clazz.equals(Object.class)) {
            return Collections.emptyList();
        }
        final var currentClassFields = clazz.getDeclaredFields();

        final var inheritedFields = getAllFields(clazz.getSuperclass());

        final var allFields = new ArrayList<Field>();

        allFields.addAll(Arrays.asList(currentClassFields));
        allFields.addAll(inheritedFields);

        return allFields;
    }

    private static String capitalizeFirstLetter(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1));
    }

    public static Map<String, Method> mapMethodNameToMethod(Class<?> dataClass) {
        final var allMethods = dataClass.getMethods();

        return Arrays.stream(allMethods)
                .filter(method -> method.getName().startsWith("get"))
                .collect(Collectors.toMap(Method::getName, Function.identity()));
    }
}
