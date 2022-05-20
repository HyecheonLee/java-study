package com.hyecheon.array;

import java.lang.reflect.Array;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/20
 */
public class Main {

    public static void main(String[] args) {
        int[] oneDimensionalArray = {1, 2};
        double[][] twoDimensionalArray = {{1.5, 2.5}, {3.5, 4.5}};

        inspectArrayObject(oneDimensionalArray);
        inspectArrayObject(twoDimensionalArray);

        inspectArrayValue(oneDimensionalArray);
        System.out.println();
        inspectArrayValue(twoDimensionalArray);
    }

    public static void inspectArrayValue(Object arrayObject) {
        final var arrayLength = Array.getLength(arrayObject);
        System.out.print("[");
        for (int i = 0; i < arrayLength; i++) {
            final var element = Array.get(arrayObject, i);
            if (element.getClass().isArray()) {
                inspectArrayValue(element);
            } else {
                System.out.print(element);
            }
            if (i != arrayLength - 1) {
                System.out.print(" , ");
            }
        }
        System.out.print("]");
    }

    public static void inspectArrayObject(Object arrayObject) {
        final Class<?> clazz = arrayObject.getClass();

        System.out.println(String.format("Is array : %s", clazz.isArray()));

        final Class<?> arrayComponentType = clazz.getComponentType();

        System.out.println(String.format("This is an array of type : %s", arrayComponentType.getTypeName()));
    }
}
