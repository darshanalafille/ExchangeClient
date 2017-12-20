package com.stockexchange.store;

import com.stockexchange.entity.local.TradeSymbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darshanas on 12/4/2017.
 */
public class SymbolStore {

    private static List<String> changedSymbols = new ArrayList<>();
    private static Map<String,TradeSymbol> symbolMap = new HashMap<>();

    public static void registerSymbol(TradeSymbol tradeSymbol){
        symbolMap.put(tradeSymbol.getSymbol(),tradeSymbol);
        changedSymbols.add(tradeSymbol.getSymbol());
    }

    public static List<TradeSymbol> getChangedSymbols(){

        List<TradeSymbol> tradeSymbols = new ArrayList<>();
        symbolMap.forEach((k,v) -> tradeSymbols.add(v));
        return tradeSymbols;
    }

    public static void updateChange(String symbol, double price, double availSell, double availBuy,
                                    double total){

        TradeSymbol ts = symbolMap.get(symbol);
        if(ts != null){
            ts.setPrice(price);
            ts.setAvilBuy(availBuy);
            ts.setAvilSell(availSell);
            ts.setTotal(total);
        }

    }


}
