package com.stockexchange.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.stockexchange.entity.price.OrderBook;
import com.stockexchange.entity.price.OrderBookRaw;
import com.stockexchange.store.OrderBookStore;

import java.io.IOException;

/**
 * Created by darshanas on 12/5/2017.
 */
public class OrderBookWindow implements UpdateWindow {

    private String symbol;
    private DefaultTerminalFactory defaultTerminalFactory = null;
    Terminal terminal = null;

    public OrderBookWindow(String symbol){
        this.symbol = symbol;
        try {
            this.defaultTerminalFactory = new DefaultTerminalFactory();
            terminal = defaultTerminalFactory.createTerminal();
            handleAction();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleAction() throws IOException {

        terminal.clearScreen();
        terminal.enterPrivateMode();
        KeyStroke keyStroke = null;
        final TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        textGraphics.putString(25, 1, "ORDER BOOK - " + symbol, SGR.BOLD);
        textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
        textGraphics.putString(5, 5, "BID QTY", SGR.BOLD);
        textGraphics.putString(25,5, "BID PRICE", SGR.BOLD);
        textGraphics.putString(45, 5, "ASK PRICE", SGR.BOLD);
        textGraphics.putString(65, 5, "ASK QTY", SGR.BOLD);
        terminal.flush();

        while (true){

            if(keyStroke != null){
                if(keyStroke.getKeyType() == KeyType.Escape){
                    break;
                }
            }

            OrderBook orderBook = OrderBookStore.getOrderBook(symbol);
            int startCol = 5;
            int startRaw = 7;

            if(orderBook != null){

                terminal.clearScreen();
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
                textGraphics.putString(25, 1, "ORDER BOOK - " + symbol, SGR.BOLD);
                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                textGraphics.putString(5, 5, "BID QTY", SGR.BOLD);
                textGraphics.putString(25,5, "BID PRICE", SGR.BOLD);
                textGraphics.putString(45, 5, "ASK PRICE", SGR.BOLD);
                textGraphics.putString(65, 5, "ASK QTY", SGR.BOLD);
                terminal.flush();

                int count = 1;
                for(OrderBookRaw obr : orderBook.getRaws()){

                    for(int j = 0; j < 4; j++){
                        switch (j){
                            case 0:
                                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                                textGraphics.putString(startCol,(startRaw+count),Double.toString(obr.getBidQty()));
                                break;
                            case 1:
                                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                                textGraphics.putString(terminal.getCursorPosition().withColumn(25),Double.toString(obr.getBidValue()));
                                break;
                            case 2:
                                textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
                                textGraphics.putString(terminal.getCursorPosition().withColumn(45),Double.toString(obr.getAskValue()));
                                break;
                            case 3:
                                textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
                                textGraphics.putString(terminal.getCursorPosition().withColumn(65),Double.toString(obr.getAskQty()));
                                break;
                            default:
                                break;
                        }

                    }
                    terminal.flush();
                    count++;
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            keyStroke = terminal.pollInput();

        }

        terminal.exitPrivateMode();
        terminal.clearScreen();
        terminal.close();



    }
}
