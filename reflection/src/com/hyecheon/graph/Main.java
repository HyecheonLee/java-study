package com.hyecheon.graph;

import com.hyecheon.graph.annotations.Annotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/27
 */
public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        final var bastGamesFinder = new BastGamesFinder();
        final List<String> bestGamesInDescendingOrder = execute(bastGamesFinder);
        System.out.println(bestGamesInDescendingOrder);

        final var sqlQueryBuilder = new SqlQueryBuilder(
                List.of("1", "2", "3"),
                10,
                "Movies",
                List.of("Id", "Name"));

        final var sqlQuery = execute(sqlQueryBuilder);
        System.out.println(sqlQuery);

    }

    private static <T> T execute(Object instance) throws InvocationTargetException, IllegalAccessException {
        final Class<?> clazz = instance.getClass();

        final var operationToMethod = getOperationToMethod(clazz);
        final var inputToField = getInputToField(clazz);

        final var finalResultMethod = findFinalResultMethod(clazz);

        return (T) executeWithDependencies(instance, finalResultMethod, operationToMethod, inputToField);
    }

    private static Map<String, Field> getInputToField(Class<?> clazz) {
        final var inputNameToField = new HashMap<String, Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Annotations.Input.class)) {
                continue;
            }
            final var input = field.getAnnotation(Annotations.Input.class);
            inputNameToField.put(input.value(), field);
        }
        return inputNameToField;
    }

    private static Object executeWithDependencies(Object instance, Method currentMethod,
                                                  Map<String, Method> operationToMethod,
                                                  Map<String, Field> inputToField
    ) throws InvocationTargetException, IllegalAccessException {
        final var parameterValues = new ArrayList<>(currentMethod.getParameterCount());
        for (var parameter : currentMethod.getParameters()) {
            Object value = null;
            if (parameter.isAnnotationPresent(Annotations.DependsOn.class)) {
                final var dependencyOperationName = parameter.getAnnotation(Annotations.DependsOn.class).value();
                final var dependencyMethod = operationToMethod.get(dependencyOperationName);

                value = executeWithDependencies(instance, dependencyMethod, operationToMethod, inputToField);
            } else if (parameter.isAnnotationPresent(Annotations.Input.class)) {
                final var inputName = parameter.getAnnotation(Annotations.Input.class).value();

                final var field = inputToField.get(inputName);
                field.setAccessible(true);
                value = field.get(instance);
            }
            parameterValues.add(value);
        }
        return currentMethod.invoke(instance, parameterValues.toArray());
    }


    private static Map<String, Method> getOperationToMethod(Class<?> clazz) {
        final var operationNameToMethod = new HashMap<String, Method>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Annotations.Operation.class)) {
                continue;
            }
            final var operation = method.getAnnotation(Annotations.Operation.class);

            operationNameToMethod.put(operation.value(), method);
        }
        return operationNameToMethod;
    }

    private static Method findFinalResultMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Annotations.FinalResult.class)) {
                return method;
            }
        }

        throw new RuntimeException("No method found with FinalResult annotation");
    }
}
