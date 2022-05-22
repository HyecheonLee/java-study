package com.hyecheon.modifiers_example;

import com.hyecheon.modifiers_example.auction.Auction;
import com.hyecheon.modifiers_example.auction.Bid;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/22
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        printClassModifiers(Auction.class);
        printClassModifiers(Bid.class);
        printClassModifiers(Bid.Builder.class);
        printClassModifiers(Class.forName("com.hyecheon.modifiers_example.auction.Bid$Builder$BidImpl"));

        printMethodsModifiers(Auction.class.getDeclaredMethods());

        printFieldsModifiers(Auction.class.getDeclaredFields());
        printFieldsModifiers(Bid.class.getDeclaredFields());
    }

    public static void printFieldsModifiers(Field[] fields) {
        for (Field field : fields) {
            final var modifier = field.getModifiers();
            System.out.println("Files \"%s\" access modifier is %s".formatted(
                    field.getName(), getAccessModifierName(modifier)));
            if (Modifier.isVolatile(modifier)) {
                System.out.println("The field is volatile");
            }
            if (Modifier.isFinal(modifier)) {
                System.out.println("The field is final");
            }
            if (Modifier.isTransient(modifier)) {
                System.out.println("The fields is transient and will not be serialized");
            }
            System.out.println();
        }
    }

    public static void printMethodsModifiers(Method[] methods) {
        for (Method method : methods) {
            final var modifier = method.getModifiers();

            System.out.println("%s() access modifier is %s".formatted(
                    method.getName(), getAccessModifierName(modifier)));

            if (Modifier.isSynchronized(modifier)) {
                System.out.println("The method is Synchronized");
            } else {
                System.out.println("The method is not synchronized");
            }
        }
    }

    public static void printClassModifiers(Class<?> clazz) {
        final var modifier = clazz.getModifiers();

        System.out.println("Class %s access modifier is %s".formatted(clazz.getSimpleName(),
                getAccessModifierName(modifier)));

        if (Modifier.isAbstract(modifier)) {
            System.out.println("The class is abstract");
        }
        if (Modifier.isInterface(modifier)) {
            System.out.println("The class is an interface");
        }
        if (Modifier.isStatic(modifier)) {
            System.out.println("The class is static");
        }
    }

    private static String getAccessModifierName(int modifier) {
        if (Modifier.isPublic(modifier)) {
            return "public";
        } else if (Modifier.isPrivate(modifier)) {
            return "private";
        } else if (Modifier.isProtected(modifier)) {
            return "protected";
        } else {
            return "package-private";
        }
    }

    public static void runAuction() {
        final var auction = new Auction();
        auction.startAuction();

        final var bid1 = Bid.builder()
                .setBidderName("Company1")
                .setPrice(10)
                .build();

        auction.addBid(bid1);

        final var bid2 = Bid.builder()
                .setBidderName("Company2")
                .setPrice(12)
                .build();
        auction.addBid(bid2);

        auction.stopAuction();

        System.out.println(auction.getAllBids());
        System.out.println("Highest : " + auction.getHighestBid().get());
    }
}
