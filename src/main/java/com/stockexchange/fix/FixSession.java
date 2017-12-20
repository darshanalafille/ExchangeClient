package com.stockexchange.fix;

import quickfix.*;
import quickfix.field.EncryptMethod;
import quickfix.field.HeartBtInt;
import quickfix.field.ResetSeqNumFlag;
import quickfix.fix42.Logon;
import quickfix.fix42.Logout;
import quickfix.fix42.OrderCancelReplaceRequest;

/**
 * Created by darshanas on 11/27/2017.
 */
public class FixSession {

    SocketInitiator socketInitiator = null;
    private static FixSession fixSession = null;

    public static FixSession getFixSession(){
        if(fixSession == null){
            fixSession = new FixSession();
        }
        return fixSession;
    }

    private FixSession(){
        try {
            System.out.println("Loading the Fix Session");
            SessionSettings sessionSettings = new SessionSettings("./initiatorSettings.txt");
            Application initiatorApplication = new TradeAppInitiator();
            FileStoreFactory fileStoreFactory = new FileStoreFactory(sessionSettings);
            FileLogFactory fileLogFactory = new FileLogFactory(sessionSettings);
            MessageFactory messageFactory = new DefaultMessageFactory();
            socketInitiator = new SocketInitiator(initiatorApplication,fileStoreFactory,sessionSettings,fileLogFactory,
                    messageFactory);
            socketInitiator.start();
        } catch (ConfigError error) {
            error.printStackTrace();
        }

    }

    public void Login(){
        SessionID sessionID = (SessionID) socketInitiator.getSessions().get(0);
        Session.lookupSession(sessionID);
        Logon logon = new Logon();
        logon.set(new HeartBtInt(30));
        logon.set(new ResetSeqNumFlag(true));
        logon.set(new EncryptMethod(0));
        try {
            Session.sendToTarget(logon, sessionID);
        } catch (SessionNotFound sessionNotFound) {
            sessionNotFound.printStackTrace();
        }
    }

    public void LogOut(){
        SessionID sessionId = (SessionID) socketInitiator.getSessions().get(0);
        Session.lookupSession(sessionId);
        Logout logout = new Logout();
        try {
            Session.sendToTarget(logout, sessionId);
        } catch (SessionNotFound sessionNotFound) {
            sessionNotFound.printStackTrace();
        }
    }

    public void sendFixMessage(Message fixMessahe){
        SessionID sessionId = (SessionID) socketInitiator.getSessions().get(0);
        Session.lookupSession(sessionId);
        try{
            Session.sendToTarget(fixMessahe,sessionId);
        }catch (SessionNotFound sessionNotFound){
            sessionNotFound.printStackTrace();
        }
    }
}
