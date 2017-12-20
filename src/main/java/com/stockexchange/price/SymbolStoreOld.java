package com.stockexchange.price;

import com.stockexchange.entity.price.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darshanas on 11/28/2017.
 */
public class SymbolStoreOld {

    private static final Map<String,Symbol> symbolMap = new HashMap<>();

    public static void put(Symbol symbol){
        symbolMap.put(symbol.getSymbolCode(),symbol);
    }

    public static Symbol get(String symbolCode){
        return symbolMap.get(symbolCode);
    }

    public static List<Symbol> getAllSymbols(){
        List<Symbol> symbols = new ArrayList<>();
        symbolMap.forEach((k,v) -> symbols.add(v));
        return symbols;
    }

}
