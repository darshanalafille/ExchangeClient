package com.stockexchange.fix;

import quickfix.*;
import quickfix.fix42.ExecutionReport;
import quickfix.fix42.Heartbeat;

/**
 * Created by darshanas on 11/27/2017.
 */
public class TradeAppInitiator extends MessageCracker implements Application {

    public void onCreate(SessionID sessionID) {

    }


    public void onLogon(SessionID sessionID) {

    }


    public void onLogout(SessionID sessionID) {

    }


    public void toAdmin(Message message, SessionID sessionID) {

    }


    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        int y = 0;
        if(!isHeartBeatMessage(message)){
            System.out.println("Admin Message Received (Initiator) :" + message.toString());
        }
    }

    private boolean isHeartBeatMessage(Message message){
        if(message instanceof Heartbeat){
            return true;
        }
        return false;
    }


    public void toApp(Message message, SessionID sessionID) throws DoNotSend {

    }


    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        //System.out.println("Application Response Received (Initiator) :" +  message.toString());
        crack(message,sessionID);
    }

    public void onMessage(ExecutionReport executionReport,SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue{
        System.out.println("New Execution Report for " + executionReport.getClOrdID().getValue() + ", status " + executionReport.getOrdStatus().getValue());

    }

}
