package com.stockexchange.entity.local;

import com.stockexchange.entity.price.OrderBook;

/**
 * Created by darshanas on 12/5/2017.
 */
public class OrderBookBeanLocal {

    private String symbol;
    private boolean isRead;
    private OrderBook orderBook;

    public OrderBookBeanLocal(){
        this.isRead = false;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }
}
