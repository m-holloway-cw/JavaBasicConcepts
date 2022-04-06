/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Raxa
 */
public class Websocket {
    public static WSClass WS;
    static Socket socket;
    static BufferedInputStream input = null;
    static BufferedOutputStream out = null;
    static int i = 110;
    
    public static void main(String[] args) {
  
       
        String channelName = "CHANNELNAME";
        String botName = "BOTNAME";
        String OAuth = "AUTHTOKEN";
        try {
  
        WS = new WSClass(new URI("ws://HOST:8025/websockets/events/"), channelName, botName, OAuth);
        
        //WS = new WSClass(new URI("wss://localhost:8081"), channelName, botName, OAuth);
        WS.addMessageHandler(new WSClass.MessageHandler(){
                    public void handleMessage(String message) {
                        JsonObject jsonObject = Json.createReader(new StringReader(message)).readObject();
                        String userName = jsonObject.getString("user");
                        if (!"bot".equals(userName)) {
                            WS.sendMessage(getMessage("Hello " + userName +", How are you?"));
                            
                        }
                    }
                });
        
        while(true){
            //WS.sendMessage(getMessage("{\"Hi There\":\"Test message\""));
            JsonObjectBuilder strBuilder = Json.createObjectBuilder();
            strBuilder.add("update", "Long extra message to test things");
            i++;
            strBuilder.add("number", i);
            JsonObject strJsonObject = strBuilder.build();
            WS.sendJSON(strJsonObject);
            Thread.sleep(3000);
        }
        
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private static String getMessage(String message) {
        return "Test message";
      /*  return Json.createObjectBuilder()
                       .add("user", "bot")
                       .add("message", message)
                   .build()
                   .toString();*/
    }
}
