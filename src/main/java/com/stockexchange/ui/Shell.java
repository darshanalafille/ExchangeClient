package com.stockexchange.ui;

import java.util.Scanner;

/**
 * Created by darshanas on 12/4/2017.
 */
public class Shell {

    private Scanner scanner;

    public Shell(){
        this.scanner = new Scanner(System.in);
        startShell();
    }

    private void startShell(){
        System.out.println("WELCOME TO EXCHANGE CLIENT");
        while (true){
            System.out.println("\n");
            System.out.print(">_ ");
            String command = scanner.nextLine();
            if(command.equalsIgnoreCase("q") || command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit")){
                break;
            }else {
                processCommand(command);
            }

        }
    }


    private void processCommand(String command){
        if(command.equals("wl")){
            new WatchListWindow();
        }else if(command.startsWith("ob")){
            String [] obCommand = command.split("\\s+");
            if(obCommand.length < 2){
                System.out.println("Invalid Command Syntax, ex - (ob 1010)");
            }else {
                String symbol = obCommand[1];
                new OrderBookWindow(symbol);
            }
        }else if(command.startsWith("qt")|| command.equals("qt") ){
            String [] obCommand = command.split("\\s+");
            if(obCommand.length >= 2){
                new QuotesWindow(obCommand[1]);
            }else {
                new QuotesWindow();
            }

        }else if(command.equals("gui")){
            new GUITest();
        }else if(command.equals("ts")){
            new TimeAndSalesWindow();
        }
    }

}
