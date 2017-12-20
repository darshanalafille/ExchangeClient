package com.stockexchange.ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.stockexchange.entity.price.DfnMessage;
import com.stockexchange.entity.price.OrderBook;
import com.stockexchange.entity.price.OrderBookRaw;
import com.stockexchange.price.PriceClient;

import java.io.IOException;
import java.util.List;

/**
 * Created by darshanas on 11/28/2017.
 */
public class OrderBookWindowOld implements Observer {

    private String symbol;
    private DefaultTerminalFactory dtf = null;
    private PriceClient priceClient = null;
    private Screen screen = null;

    public OrderBookWindowOld(String symbol){
        this.symbol = symbol;
        priceClient = PriceClient.getInstance();
        dtf = new DefaultTerminalFactory();
        try {
            screen = dtf.createScreen();
            priceClient.subscribe(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateView(DfnMessage message) {

        if(message instanceof OrderBook){
            OrderBook ob = (OrderBook) message;
            if(ob.getSymbol() != null){
                if(ob.getSymbol().equals(symbol)){
                    redrawOrderBook(ob.getRaws());
                }
            }
        }

    }

    private void redrawOrderBook(List<OrderBookRaw> raws){
        if(raws != null){
            if(raws.size() > 0){
                screen.clear();
                TextGraphics textGraphics = screen.newTextGraphics();
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.setBackgroundColor(TextColor.ANSI.GREEN);
                int tiPos = 3;
                textGraphics.putString(2, 2, "Bid Qty");
                TerminalPosition startPosition = screen.getCursorPosition();
                textGraphics.putString(startPosition.withRelative(20, 2),"Bid Value");
                textGraphics.putString(screen.getCursorPosition().withRelative(40, 2),"Ask Value");
                textGraphics.putString(screen.getCursorPosition().withRelative(60, 2), "Ask Qty");
                try{
                    screen.refresh();
                    int i = 1;
                    for(OrderBookRaw r : raws){
                        textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
                        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
                        textGraphics.putString(2, (tiPos + i), Double.toString(r.getBidQty()));
                        textGraphics.putString(screen.getCursorPosition().withRelative(20,(tiPos + i)), Double.toString(r.getBidValue()));
                        textGraphics.setForegroundColor(TextColor.ANSI.MAGENTA);
                        textGraphics.putString(screen.getCursorPosition().withRelative(40, (tiPos + i)), Double.toString(r.getAskValue()));
                        textGraphics.putString(screen.getCursorPosition().withRelative(60, (tiPos + i)), Double.toString(r.getAskQty()));
                        screen.refresh();
                        i++;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
