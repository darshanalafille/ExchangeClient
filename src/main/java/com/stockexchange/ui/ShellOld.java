package com.stockexchange.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.stockexchange.test.GUITextWindowTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by darshanas on 11/28/2017.
 */
public class ShellOld {

    private static Scanner scanner = null;

    DefaultTerminalFactory defaultTerminalFactory = null;
    Terminal terminal = null;

    public ShellOld(){
        defaultTerminalFactory =  new DefaultTerminalFactory();
        try {
            terminal = defaultTerminalFactory.createTerminal();
            final TextGraphics textGraphics = terminal.newTextGraphics();
            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
            textGraphics.putString(25, 1, "Welcome to Stock Exchange Client", SGR.BOLD);
            terminal.flush();
            showWatchList(textGraphics);
//            KeyStroke keyStroke = terminal.readInput();
//            while (keyStroke.getKeyType() != KeyType.Escape){
//
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showWatchList(TextGraphics graphics){
        graphics.setForegroundColor(TextColor.ANSI.YELLOW);
        graphics.putString(2,5,"SYMBOL",SGR.BOLD);
        try {
            TerminalPosition startPosition = terminal.getCursorPosition();
            graphics.putString(startPosition.withRelativeColumn(10),"DESCRIPTION",SGR.BOLD);
            graphics.putString(terminal.getCursorPosition().withRelativeColumn(25),"VOLUME",SGR.BOLD);
            graphics.putString(terminal.getCursorPosition().withRelativeColumn(10),"PRICE",SGR.BOLD);
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
