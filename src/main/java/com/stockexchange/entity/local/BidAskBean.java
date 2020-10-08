package com.stockexchange.entity.local;

public class BidAskBean {

    private final String symbol;
    private final double bidValue;
    private final double askValue;

    public BidAskBean(String symbol, double bidValue, double askValue) {
        this.symbol = symbol;
        this.bidValue = bidValue;
        this.askValue = askValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getBestBid() {
        return bidValue;
    }

    public double getBestAsk() {
        return askValue;
    }
}
