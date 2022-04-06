/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Raxa
 */
public class XMLParser {

    public static void main(String[] args) {
        try {
            String message;
            message = "{\"t\":null,\"s\":null,\"op\":10,\"d\":{\"heartbeat_interval\":41250,\"_trace\":[\"[\\\"gateway-prd-main-wkv3\\\",{\\\"micros\\\":0.0}]\"]}}";

            JsonNode data = new ObjectMapper().readTree(message);
            JsonNode n = data.get("op");
            
            System.out.println(n.asInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
