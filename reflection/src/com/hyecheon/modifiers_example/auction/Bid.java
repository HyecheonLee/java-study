package com.hyecheon.modifiers_example.auction;

import java.io.Serializable;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/22
 */
public abstract class Bid implements Serializable {

    protected int price;
    protected String bidderName;

    public static Builder builder() {
        return new Builder();
    }

    public int getPrice() {
        return price;
    }

    public String getBidderName() {
        return bidderName;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "price=" + price +
                ", bidderName='" + bidderName + '\'' +
                '}';
    }

    public static class Builder {
        private int price;
        private String bidderName;

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setBidderName(String bidderName) {
            this.bidderName = bidderName;
            return this;
        }
        public Bid build() {
            return new BidImpl();
        }
        private class BidImpl extends Bid {
            private BidImpl() {
                super.price = Builder.this.price;
                super.bidderName = Builder.this.bidderName;
            }
        }
    }
}
