/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.JsonObject;
import org.java_websocket.WebSocket;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 *
 * @author Raxa
 */
@ClientEndpoint
public class WSClass {

    private final String botName;
    private final String channelName;
    private final String oAuth;
    private final URI uri;
    private long lastPong = System.currentTimeMillis();
    private long lastPing = 0l;
    private long lastReconnect = 0;

    Session userSession = null;
    private MessageHandler messageHandler;

    
    public WSClass(URI uri, String channelName, String botName, String oAuth) {

        this.uri = uri;
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, uri);
           
        } catch (Exception e) {
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        this.channelName = channelName;
        this.botName = botName;
        this.oAuth = oAuth;
    }


    /**
     * Callback that is called when we open a connect to Twitch.
     *
     * @param {ServerHandshake} handshakedata
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    /**
     * Callback that is called when the connection with Twitch is lost.
     *
     * @param {int} code
     * @param {String} reason
     * @param {boolean} remote
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("On Close: code: " + reason + " remote: " + userSession.getId());
        //reconnect here
        //reconnect();
    }


    /**
     * Callback that is called when we get a message from Twitch.
     *
     * @param {String} message
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Message: " + message);
        
            parse(message);
        
    }

    void parse(String msg) {
        System.out.println("PARSE INCOMING: " + msg);
        if (msg.contains("USERNOTICE #")) {
            if (msg.contains("Prime")) {
                System.out.println("PRIME Message:" + msg);
            } else {
                System.out.println("Notification Message:" + msg);
            }
        }
    }

    /*
     * Method that handles reconnecting with Twitch.
     */
    @SuppressWarnings("SleepWhileInLoop")
    public void reconnect() {

    }

    public void addMessageHandler(MessageHandler handler){
        this.messageHandler = handler;
    }
    
    public void sendJSON(JsonObject json){
        this.userSession.getAsyncRemote().sendObject(json);
    }
    
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    public static interface MessageHandler {

        public void handleMessage(String message);
    }

}
