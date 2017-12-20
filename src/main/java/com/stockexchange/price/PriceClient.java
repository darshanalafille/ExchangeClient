package com.stockexchange.price;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stockexchange.entity.local.OrderBookBeanLocal;
import com.stockexchange.entity.local.OrderLocal;
import com.stockexchange.entity.local.TimeAndSales;
import com.stockexchange.entity.local.TradeSymbol;
import com.stockexchange.entity.price.*;
import com.stockexchange.store.OrderBookStore;
import com.stockexchange.store.QuotesStore;
import com.stockexchange.store.SymbolStore;
import com.stockexchange.store.TimeAndSalesStore;
import com.stockexchange.ui.Observer;
import quickfix.field.OrdType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by darshanas on 11/27/2017.
 */
public class PriceClient {

//    private final static String ip = "192.168.16.91";
    private final static String ip = "127.0.0.1";
    private final static int port = 16500;

    private static PriceClient priceClient = null;

    private Socket socket = null;
    BufferedReader input;
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();
    private Observer observer = null;


    public static PriceClient getInstance(){
        if(priceClient == null){
            priceClient = new PriceClient();
        }
        return priceClient;
    }

    private PriceClient(){
        while (!connect()){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean connect() {
        try {
            System.out.println("Connecting with price socket");
            socket = new Socket(ip,port);
            System.out.println("Connected with price server");
            return true;
        } catch (IOException e) {
            System.out.println("Cant connect to specified host");
            return false;
        }

    }

    public void startRead(){
        String priceMessage;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((priceMessage = input.readLine()) != null){
                processMessage(priceMessage);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void processMessage(String msg){

        //System.out.println(msg);
        JsonObject object = parser.parse(msg).getAsJsonObject();
        String messageType = object.get("messageType").getAsString();
        DfnMessage dfnMessage = null;
        if(messageType.equals("B")){
            // order book
            OrderBook orderBook = gson.fromJson(msg,OrderBook.class);
            dfnMessage = orderBook;
            updateOrderBookStore(orderBook);
        }else if(messageType.equals("S")){
            gson.fromJson(msg,SymbolList.class).getSymbolList().forEach(s -> {
                TradeSymbol ts = new TradeSymbol(s.getSymbolCode(),s.getSymbol());
                SymbolStore.registerSymbol(ts);
            });
        }else if(messageType.equals("V")){
            MarketVolume mktVol = gson.fromJson(msg,MarketVolume.class);
            SymbolStore.updateChange(mktVol.getSymbol(),mktVol.getLastTradePrice(),mktVol.getSellSide(),mktVol.getBuySide(),
                    mktVol.getExVolume());
        }else if(messageType.equals("D")){
            QuotesStore.orderCount++;
            Quote quote = gson.fromJson(msg,Quote.class);
            OrderLocal ordLocal = null;
            if(quote.getType() == OrdType.LIMIT){
                ordLocal = new OrderLocal(quote.getSymbol(),quote.getSide(),quote.getType(),quote.getPrice(),quote.getQty());
            }else {
                ordLocal = new OrderLocal(quote.getSymbol(),quote.getSide(),quote.getType(),quote.getQty());
            }

            QuotesStore.addQuote(ordLocal);

        }else if(messageType.equals("8")){
            QuotesStore.matchCount++;
            TradeMatch tm = gson.fromJson(msg,TradeMatch.class);
            TimeAndSalesStore.addTimeAndSales(new TimeAndSales(tm.getTime(),tm.getSymbol(),tm.getQty(),tm.getPrice()));
        }else if(messageType.equals("h")){
            StatusMessage statusMessage = gson.fromJson(msg,StatusMessage.class);
            QuotesStore.matchCount = (int) statusMessage.getMatchCount();
            QuotesStore.orderCount = (int) statusMessage.getOrderCount();
        }

        if(observer != null){
            observer.updateView(dfnMessage);
        }
    }


    private void updateOrderBookStore(OrderBook orderBook){
        OrderBookBeanLocal beanLocal = new OrderBookBeanLocal();
        beanLocal.setSymbol(orderBook.getSymbol());
        beanLocal.setOrderBook(orderBook);
        OrderBookStore.addOrUpdate(beanLocal);
    }


    public void subscribe(Observer observer){
        this.observer = observer;
    }

    public void unsubscribe(){
        this.observer = null;
    }

}
