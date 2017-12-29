package com.stockexchange.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.stockexchange.entity.local.BidAskBean;
import com.stockexchange.entity.local.TradeSymbol;
import com.stockexchange.store.OrderBookStore;
import com.stockexchange.store.SymbolStore;
import com.stockexchange.store.TimeAndSalesStore;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darshanas on 12/4/2017.
 */
public class WatchListWindow implements UpdateWindow{

    private DefaultTerminalFactory defaultTerminalFactory = null;
    Terminal terminal = null;


    public WatchListWindow(){
        try {
            this.defaultTerminalFactory = new DefaultTerminalFactory();
            terminal = defaultTerminalFactory.createTerminal();
            handleAction();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAction() throws IOException{

        terminal.clearScreen();
        terminal.enterPrivateMode();
        terminal.setCursorVisible(false);
        KeyStroke keyStroke = null;
        final TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        textGraphics.putString(30, 1, "***** BLOTTER *****", SGR.BOLD);

        textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);

        textGraphics.putString(2, 5, "SYMBOL", SGR.BOLD);
        textGraphics.putString(12,5, "NAME", SGR.BOLD);
        textGraphics.putString(54,5, "LTP", SGR.BOLD);
        textGraphics.putString(62,5,"BID_QTY",SGR.BOLD);
        textGraphics.putString(77,5,"BID_PRICE",SGR.BOLD);
        textGraphics.putString(92,5,"OFFER_QTY",SGR.BOLD);
        textGraphics.putString(107,5,"OFFER_PRICE",SGR.BOLD);
        textGraphics.putString(122,5,"VOLUME",SGR.BOLD);
        textGraphics.putString(140,5,"TRADE_TIME",SGR.BOLD);

        List<TradeSymbol> tradeSymbolList = null;

        while(true) {

            if(keyStroke != null){
                if(keyStroke.getKeyType() == KeyType.Escape){
                    break;
                }
            }

            tradeSymbolList = SymbolStore.getChangedSymbols();
            int startCol = 2;
            int startRaw = 7;

            if(tradeSymbolList.size() > 0){
                int count = 1;

                for(TradeSymbol tr : tradeSymbolList){

                    textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                    textGraphics.putString(startCol,(startRaw+count),tr.getSymbol());
                    BidAskBean bestBidAsk = OrderBookStore.getBestBidAsk(tr.getSymbol());

                    for(int j = 0; j < 8; j++){

                        switch (j){
                            case 0 :
                                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                                textGraphics.putString(terminal.getCursorPosition().withColumn(12),tr.getName());
                                break;
                            case 1:
                                textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
                                String ltpStr = Double.toString(tr.getPrice());
                                int startPosition = (8 - ltpStr.length()) + 52;
                                textGraphics.putString(terminal.getCursorPosition().withColumn(startPosition),ltpStr);
                                break;
                            case 2:
                                textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
                                textGraphics.putString(terminal.getCursorPosition().withColumn(62),Integer.toString((int)tr.getAvilBuy()));
                                break;
                            case 3:
                                textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
                                String bestBidStr = Double.toString(bestBidAsk.getBestBid());
                                int startPos = (8-bestBidStr.length()) + 77;
                                textGraphics.putString(terminal.getCursorPosition().withColumn(startPos),bestBidStr);
                                break;
                            case 4:
                                textGraphics.setForegroundColor(TextColor.ANSI.MAGENTA);
                                textGraphics.putString(terminal.getCursorPosition().withColumn(92),Integer.toString((int)tr.getAvilSell()));
                                break;
                            case 5:
                                textGraphics.setForegroundColor(TextColor.ANSI.MAGENTA);
                                String bestAskStr = Double.toString(bestBidAsk.getBestAsk());
                                int stp = (8-bestAskStr.length()) + 107;
                                textGraphics.putString(terminal.getCursorPosition().withColumn(stp),bestAskStr);
                                break;
                            case 6:
                                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                                long lx = Double.valueOf(tr.getTotal()).longValue();
                                textGraphics.putString(terminal.getCursorPosition().withColumn(122),Long.toString(lx));
                                break;
                            case 7:
                                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                                String ltt = TimeAndSalesStore.getLastTradedTime(tr.getSymbol());
                                if(ltt != null){
                                    textGraphics.putString(terminal.getCursorPosition().withColumn(140), ltt);
                                }

                            default:
                                break;
                        }

                    }
                    count++;
                }


            }

            terminal.flush();

//            try {
//                Thread.sleep(250);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            keyStroke = terminal.pollInput();
        }
        terminal.exitPrivateMode();
        terminal.clearScreen();
        terminal.close();

    }

    private void putChar(Character character) throws IOException {
        terminal.putCharacter(character);
        terminal.flush();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void putString(String str) throws IOException{
        char [] chars = str.toCharArray();

        for(int i = 0; i < chars.length; i++){
            terminal.putCharacter(new Character(chars[i]));
        }
        terminal.flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
