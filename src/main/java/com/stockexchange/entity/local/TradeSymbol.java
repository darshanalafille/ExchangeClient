package com.stockexchange.entity.local;

/**
 * Created by darshanas on 12/4/2017.
 */
public class TradeSymbol {

    private String symbol;
    private String name;
    private double price;
    private double avilSell;
    private double avilBuy;
    private double total;

    public TradeSymbol(){

    }

    public TradeSymbol(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        this.price = 0;
        this.avilBuy = 0;
        this.avilSell = 0;
        this.total = 0;
    }



    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAvilSell() {
        return avilSell;
    }

    public void setAvilSell(double avilSell) {
        this.avilSell = avilSell;
    }

    public double getAvilBuy() {
        return avilBuy;
    }

    public void setAvilBuy(double avilBuy) {
        this.avilBuy = avilBuy;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
