package com.stockexchange.entity.local;

/**
 * Created by darshanas on 12/5/2017.
 */
public class OrderLocal {

    private final long timeStmp;
    private final String symbol;
    private final char side;
    private final char type;
    private final double price;
    private final double qty;

    public OrderLocal(String symbol, char side, char type, double price, double qty) {
        this.timeStmp = System.nanoTime();
        this.symbol = symbol;
        this.side = side;
        this.type = type;
        this.price = price;
        this.qty = qty;
    }

    public OrderLocal(String symbol, char side, char type, double qty) {
        this.timeStmp = System.nanoTime();
        this.symbol = symbol;
        this.side = side;
        this.type = type;
        this.qty = qty;
        this.price = 0;
    }

    public long getTimeStmp() {
        return timeStmp;
    }

    public String getSymbol() {
        return symbol;
    }

    public char getSide() {
        return side;
    }

    public char getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public double getQty() {
        return qty;
    }
}
