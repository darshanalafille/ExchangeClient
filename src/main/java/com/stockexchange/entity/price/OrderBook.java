package com.stockexchange.entity.price;

import java.util.List;

/**
 * Created by darshanas on 11/8/2017.
 */
public class OrderBook extends DfnMessage{

    private final long timeStamp;
    private String symbol;
    private List<OrderBookRaw> raws;

    public OrderBook(List<OrderBookRaw> raws){
        timeStamp = System.currentTimeMillis();
        this.raws = raws;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public List<OrderBookRaw> getRaws() {
        return raws;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
