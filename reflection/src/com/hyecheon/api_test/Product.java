package com.hyecheon.api_test;

import java.util.Date;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/22
 */
public class Product {
    private double price;
    private String name;
    private long quantity;
    private Date expirationDate;

    public Product(double price, String name, long quantity, Date expirationDate) {
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public long getQuantity() {
        return quantity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
