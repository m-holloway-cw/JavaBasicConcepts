/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Raxa
 */
public class JSONParser {

    String json = "{\n"
            + "    \"data\": [\n"
            + "        {\n"
            + "            \"broadcaster_id\": \"87414084\",\n"
            + "            \"broadcaster_name\": \"Raxa\",\n"
            + "            \"gifter_id\": \"\",\n"
            + "            \"gifter_name\": \"\",\n"
            + "            \"is_gift\": false,\n"
            + "            \"plan_name\": \"Don't do it.\",\n"
            + "            \"tier\": \"1000\",\n"
            + "            \"user_id\": \"139015251\",\n"
            + "            \"user_name\": \"Elvie\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"broadcaster_id\": \"87414084\",\n"
            + "            \"broadcaster_name\": \"Raxa\",\n"
            + "            \"gifter_id\": \"\",\n"
            + "            \"gifter_name\": \"\",\n"
            + "            \"is_gift\": false,\n"
            + "            \"plan_name\": \"Really? Don't even look at this one.\",\n"
            + "            \"tier\": \"3000\",\n"
            + "            \"user_id\": \"87414084\",\n"
            + "            \"user_name\": \"Raxa\"\n"
            + "        }\n"
            + "    ],\n"
            + "    \"pagination\": {\n"
            + "        \"cursor\": \"APICURSORTOKEN\"\n"
            + "    }\n"
            + "}";

    public static void main(String[] args) {
        getSubCount();
    }

    private static void getSubCount() {
        try {
            String helixToken = "TOKEN";
            String channelID = "CHANNELID";
            HttpURLConnection con = (HttpURLConnection) (new URL("https://api.twitch.tv/helix/subscriptions?broadcaster_id=" + channelID).openConnection());
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.85 Safari/537.36");
            con.setRequestProperty("Authorization", "Bearer " + helixToken);
            BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = read.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
            JSONObject obj = new JSONObject(sb.toString());
            int t = 0;
            JSONArray array = obj.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                String element = array.getJSONObject(i).getString("tier");
                
                int subPoints = 0;
                if (element.equalsIgnoreCase("Prime")) {
                    subPoints = 1;
                } else if (Integer.parseInt(element) == 1000) {
                    subPoints = 1;
                } else if (Integer.parseInt(element) == 2000) {
                    subPoints = 2;
                } else if (Integer.parseInt(element) == 3000) {
                    if(!array.getJSONObject(i).getString("user_name").equalsIgnoreCase(array.getJSONObject(i).getString("broadcaster_name"))){
                      subPoints = 6;  
                    }  
                }
                t = t + subPoints;
            }
            System.out.println("T-total: " + t);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
