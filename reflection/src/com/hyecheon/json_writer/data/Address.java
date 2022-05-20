package com.hyecheon.json_writer.data;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/20
 */
public class Address {
    private final String street;
    private final short apartment;

    public Address(String street, short apartment) {
        this.street = street;
        this.apartment = apartment;
    }
}
