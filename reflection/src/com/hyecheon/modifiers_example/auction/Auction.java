package com.hyecheon.modifiers_example.auction;

import java.io.Serializable;
import java.util.*;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/22
 */
public class Auction implements Serializable {

    private final List<Bid> bids = new ArrayList<>();

    private transient volatile boolean isAuctionStarted;

    public synchronized void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public synchronized List<Bid> getAllBids() {
        return Collections.unmodifiableList(bids);
    }

    public synchronized Optional<Bid> getHighestBid() {
        return bids.stream().max(Comparator.comparing(Bid::getPrice));
    }

    public void startAuction() {
        this.isAuctionStarted = true;
    }

    public void stopAuction() {
        this.isAuctionStarted = false;
    }

    public boolean isAuctionRunning() {
        return isAuctionStarted;
    }
}
