package com.hyecheon.json_writer.data;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/20
 */
public class Company {

    private final String name;
    private final String city;
    private final Address address;

    public Company(String name, String city, Address address) {
        this.name = name;
        this.city = city;
        this.address = address;
    }
}
