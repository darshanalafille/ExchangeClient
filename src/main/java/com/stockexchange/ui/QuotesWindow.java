package com.stockexchange.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.stockexchange.entity.local.OrderLocal;
import com.stockexchange.store.QuotesStore;
import quickfix.field.OrdType;
import quickfix.field.Side;

import java.io.IOException;
import java.util.List;

/**
 * Created by darshanas on 12/5/2017.
 */
public class QuotesWindow implements UpdateWindow {

    private DefaultTerminalFactory defaultTerminalFactory = null;
    Terminal terminal = null;
    private String symbol = null;

    public QuotesWindow(){
        try {
            this.defaultTerminalFactory = new DefaultTerminalFactory();
            terminal = defaultTerminalFactory.createTerminal();
            handleAction();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QuotesWindow(String symbol){

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
        terminal.setCursorVisible(false);
        KeyStroke keyStroke = null;
        final TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        if(symbol == null){
            textGraphics.putString(25, 1, "ALL QUOTES", SGR.BOLD);
        }else {
            textGraphics.putString(25, 1, "QUOTES FOR - "+symbol, SGR.BOLD);
        }

        textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
        textGraphics.putString(3, 3, "ORDERS::MATCHS ");
        //textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
        textGraphics.putString(22, 3, "0   0",SGR.BOLD);


        textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
        textGraphics.putString(5, 5, "SYMBOL", SGR.BOLD);
        textGraphics.putString(20, 5, "TYP", SGR.BOLD);
        textGraphics.putString(35, 5, "SID", SGR.BOLD);
        textGraphics.putString(45, 5, "PRICE", SGR.BOLD);
        textGraphics.putString(65, 5, "QTY", SGR.BOLD);
        terminal.flush();

        while (true){

            if(keyStroke != null){
                if(keyStroke.getKeyType() == KeyType.Escape){
                    break;
                }
            }

            List<OrderLocal> ordList = null;

            if(symbol == null){
                ordList = QuotesStore.getQuotes();
            }else {
                ordList = QuotesStore.getQuotes(symbol);
            }

            int loopFactor = 0;
            if(ordList != null){
                if(ordList.size() > 0){
                    int x = ordList.size();
                    setOrderCount(textGraphics,x);
                    if(x > 50)
                        loopFactor = x - 50;
                    int startCol = 5;
                    int startRaw = 7;
                    int count = 1;
                    for(int i = x; i > loopFactor; i--){
                        OrderLocal ord = ordList.get(i-1);

                        if(ord.getSide() == Side.BUY){
                            textGraphics.setForegroundColor(TextColor.ANSI.MAGENTA);
                        }else {
                            textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
                        }

                        for(int j = 0; j < 5; j++){

                            switch (j){
                                case 0:
                                    textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                                    textGraphics.putString(startCol,(startRaw+count),ord.getSymbol());
                                    break;
                                case 1:
                                    if(ord.getType() == OrdType.LIMIT){
                                        textGraphics.setForegroundColor(TextColor.ANSI.BLUE);
                                        textGraphics.putString(terminal.getCursorPosition().withColumn(20),"LMT");
                                    }else {
                                        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                                        textGraphics.putString(terminal.getCursorPosition().withColumn(20),"MKT");
                                    }
                                    break;
                                case 2:
                                    if(ord.getSide() == Side.BUY){
                                        textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
                                        textGraphics.putString(terminal.getCursorPosition().withColumn(35),"BUY");
                                    }else {
                                        textGraphics.setForegroundColor(TextColor.ANSI.MAGENTA);
                                        textGraphics.putString(terminal.getCursorPosition().withColumn(35),"SEL");
                                    }
                                    break;
                                case 3:
                                    textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                                    textGraphics.putString(terminal.getCursorPosition().withColumn(45),Double.toString(ord.getPrice()));
                                    break;
                                case 4:
                                    textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                                    textGraphics.putString(terminal.getCursorPosition().withColumn(65),Integer.toString((int)ord.getQty()));
                                    break;
                                default:
                                    break;
                            }

                        }

                        terminal.flush();
                        count++;

                    }

                }
            }

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            keyStroke = terminal.pollInput();

        }

        terminal.exitPrivateMode();
        terminal.clearScreen();
        terminal.close();


    }

    private void setOrderCount(TextGraphics textGraphics,int count){
        textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
        textGraphics.putString(22, 3, QuotesStore.orderCount + "   " + QuotesStore.matchCount,SGR.BOLD);
    }

}
