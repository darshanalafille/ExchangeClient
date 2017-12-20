package com.stockexchange.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.stockexchange.entity.local.TimeAndSales;
import com.stockexchange.store.QuotesStore;
import com.stockexchange.store.TimeAndSalesStore;
import com.stockexchange.utils.TimeUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by darshanas on 12/8/2017.
 */
public class TimeAndSalesWindow implements UpdateWindow {

    private DefaultTerminalFactory defaultTerminalFactory = null;
    Terminal terminal = null;
    private Map<String,Integer> matchPerSecMap = new HashMap<>();


    public TimeAndSalesWindow(){
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
        textGraphics.putString(30, 1, "***** TIME AND SALES *****", SGR.BOLD);

        textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
        textGraphics.putString(3, 3, "TOTAL MATCHES : ");

        textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);

        textGraphics.putString(2, 5, "TIME", SGR.BOLD);
        textGraphics.putString(22, 5, "SYMBOL", SGR.BOLD);
        textGraphics.putString(42, 5, "QTY", SGR.BOLD);
        textGraphics.putString(62, 5, "PRICE", SGR.BOLD);
        terminal.flush();



        List<TimeAndSales> tsList = null;
        while(true) {

            if(keyStroke != null){
                if(keyStroke.getKeyType() == KeyType.Escape){
                    break;
                }
            }


            tsList = TimeAndSalesStore.getTimeAndSales();
            int startCol = 2;
            int startRaw = 7;

            if(tsList != null) {
                if (tsList.size() > 0) {
                    setOrderCount(textGraphics);
                    int count = 1;

                    Map<Long,Integer> tradesSec = getTradesPerSecs(tsList);
                    drawTradesPerSec(tradesSec, textGraphics);

                    for (TimeAndSales ts : tsList) {
                        for (int i = 0; i < 4; i++) {
                            switch (i) {
                                case 0:
                                    textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                                    textGraphics.putString(startCol, (startRaw + count), ts.getTime());
                                    break;
                                case 1:
                                    textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                                    textGraphics.putString(terminal.getCursorPosition().withColumn(22), ts.getSymbol());
                                    break;
                                case 2:
                                    textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                                    textGraphics.putString(terminal.getCursorPosition().withColumn(42), Integer.toString((int)ts.getQty()));
                                    break;
                                case 3:
                                    textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                                    textGraphics.putString(terminal.getCursorPosition().withColumn(62), Double.toString(ts.getPrice()));
                                default:
                                    break;
                            }
                        }

                        terminal.flush();
                        count++;

                    }

                }
            }

            keyStroke = terminal.pollInput();

        }


    }

    private void setOrderCount(TextGraphics textGraphics){
        textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
        textGraphics.putString(25, 3, Integer.toString(QuotesStore.matchCount),SGR.BOLD);
    }

    private void drawTradesPerSec(Map<Long,Integer> tradesSec,TextGraphics textGraphics) throws IOException{

        textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
        textGraphics.putString(90, 5, "MATH PER SEC", SGR.BOLD);
        textGraphics.setForegroundColor(TextColor.ANSI.MAGENTA);
        int i = 0;
        int startRaw = 7;
        List<Long> keyList = new ArrayList<>(tradesSec.size());
        tradesSec.forEach((k, v) -> keyList.add(k));
        Collections.sort(keyList);

        int loopLenght = 0;

        if(tradesSec.size() > 3){
            loopLenght = 3;
        }else {
            loopLenght = tradesSec.size();
        }

        for(int j = 0; j < loopLenght; j++){
            long milliVal = keyList.get(j);
            int trades = tradesSec.get(milliVal);
            String displayStr = TimeUtils.getTimeString(milliVal) + " - " + trades;
            textGraphics.putString(90,(startRaw+i),displayStr);
            terminal.flush();
        }



    }

    private Map<Long,Integer> getTradesPerSecs(List<TimeAndSales> tsList){

        Map<Long,Integer> x = new HashMap<>();
        List<Long> milliList = new ArrayList<>(tsList.size());

        tsList.forEach(t -> milliList.add(TimeUtils.getMilliVal(t.getTime())));
        Collections.sort(milliList);

        for(int i = 0; i < milliList.size(); i++){
            long a = milliList.get(i);
            int timeSales = 1;
            for (int k = (i+1); k < milliList.size(); k++){
                long b = milliList.get(k);
                if(a == b){
                    timeSales++;
                }else {
                    x.put(a,timeSales);
                    i = k+1;
                    break;
                }
            }
        }
        return x;
    }

}
