package com.stockexchange.store;

import com.stockexchange.entity.local.TimeAndSales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by darshanas on 12/8/2017.
 */
public class TimeAndSalesStore {

    private static List<TimeAndSales> timeAndSales = new ArrayList<>();

    public static void addTimeAndSales(TimeAndSales ts){
        timeAndSales.add(ts);
    }

    public static List<TimeAndSales> getTimeAndSales(){

        int count = timeAndSales.size();
        if(count == 0){
            return null;
        }

        List<TimeAndSales> rev = new ArrayList<>();
        if(count < 200){
            int x = 0;
            for(int i = count; i > 0; i--){
                rev.add(x,timeAndSales.get(i-1));
                x++;
            }
        }else {
            int y = timeAndSales.size() - 200;
            int x = 0;
            for(int i = timeAndSales.size(); i > y; i--){
                rev.add(x,timeAndSales.get(i-1));
                x++;
            }
        }

        return rev;
    }

    public static String getLastTradedTime(String symbol){

        for(int i = timeAndSales.size(); i > 0; i--){
            if(timeAndSales.get(i-1).getSymbol().equals(symbol)){
                return timeAndSales.get(i-1).getTime();
            }
        }

        return null;
    }

}
