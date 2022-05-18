package com.hyecheon.constructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/18
 */
public class Main {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
//        printConstructor(Address.class);
        final var address = createInstanceWithArguments(Address.class, "First Street", 10);
        final var person = createInstanceWithArguments(Person.class, address, "John", 20);
        System.out.println(person);
    }

    public static <T> T createInstanceWithArguments(Class<T> clazz, Object... args) throws InvocationTargetException
            , InstantiationException, IllegalAccessException {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == args.length) {
                return (T) constructor.newInstance(args);
            }
        }
        System.out.println("An appropriate constructor was not found");
        return null;
    }

    public static void printConstructor(Class<?> clazz) {
        final var constructors = clazz.getDeclaredConstructors();
        System.out.printf("clazz %s has %d declared constructors %n", clazz.getSimpleName(), constructors.length);
        for (final var constructor : constructors) {
            final var parameterTypes = constructor.getParameterTypes();

            final var parameterTypeNames = Arrays.stream(parameterTypes)
                    .map(Class::getSimpleName)
                    .collect(Collectors.toList());

            System.out.println(parameterTypeNames);
        }
    }

    public static class Person {
        private final Address address;
        private final String name;
        private final int age;

        public Person() {
            this.address = null;
            this.name = "anonymous";
            this.age = 0;
        }

        public Person(String name) {
            this.name = name;
            this.address = null;
            this.age = 0;
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
            this.address = null;
        }

        public Person(Address address, String name, int age) {
            this.address = address;
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "address=" + address +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static class Address {
        private String street;
        private int number;

        public Address(String street, int number) {
            this.street = street;
            this.number = number;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", number=" + number +
                    '}';
        }
    }
}
