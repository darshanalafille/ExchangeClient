package com.stockexchange.entity.local;

/**
 * Created by darshanas on 12/8/2017.
 */
public class TimeAndSales {

    private final String time;
    private final String symbol;
    private final double qty;
    private final double price;

    public TimeAndSales(String time, String symbol, double qty, double price) {
        this.time = time;
        this.symbol = symbol;
        this.qty = qty;
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }
}
