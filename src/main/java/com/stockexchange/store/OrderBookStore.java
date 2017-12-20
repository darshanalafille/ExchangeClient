package com.stockexchange.store;

import com.stockexchange.entity.local.OrderBookBeanLocal;
import com.stockexchange.entity.price.OrderBook;
import com.stockexchange.entity.price.OrderBookRaw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darshanas on 12/5/2017.
 */
public class OrderBookStore {


    private static Map<String,OrderBookBeanLocal> beanLocalMap = new HashMap<>();


    public static void addOrUpdate(OrderBookBeanLocal bean){
        beanLocalMap.put(bean.getSymbol(),bean);
    }

    public static OrderBook getOrderBook(String symbol){

        OrderBookBeanLocal local = beanLocalMap.get(symbol);
        OrderBook orderBook = null;
        if(local != null){
            if(!local.isRead()){
                orderBook = local.getOrderBook();
                local.setIsRead(true);
            }
        }
        return orderBook;
    }

    public static OrderBook getOrderBookDepthByPrice(String symbol){

        return null;
    }





}
