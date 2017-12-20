package com.stockexchange.store;

import com.stockexchange.entity.local.OrderLocal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by darshanas on 12/5/2017.
 */
public class QuotesStore {

    public static int matchCount = 0;
    public static int orderCount = 0;
    private static List<OrderLocal> list = new ArrayList<>();

    public static void addQuote(OrderLocal order){
        list.add(order);
    }

    public static List<OrderLocal> getQuotes(String symbol){
       return list.stream().filter(s -> symbol.equals(s.getSymbol())).collect(Collectors.toList());
    }

    public static List<OrderLocal> getQuotes(){
        return list;
    }

}
