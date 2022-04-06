/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.awt.Desktop;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;

import org.apache.oltu.oauth2.client.*;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;

/**
 *
 * @author Raxa
 */
public class ServerSocketTest {

    public static void main(String[] args) {
        try {
            String clientId = "CLIENTID";
            String clientSecret = "CLIENTSECRET";
            String newCode = "";
            String credURL = "https://accounts.spotify.com/authorize?client_id=CLIENTID&response_type=code&redirect_uri=REDIRECTURL&scope=user-read-currently-playing";
            String clientURL = "https://accounts.spotify.com/api/token";
            String redirURL = "REDIRECTURL";
            //URL url = new URL(clientURL);
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(credURL));

            try {
                OAuthClientRequest request = OAuthClientRequest.authorizationLocation(credURL).setClientId(clientId).setRedirectURI(redirURL).buildQueryMessage();



                URI url = new URI(request.getLocationUri());
                //OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
                //String code = oar.getCode();
//System.out.println(code);
            } catch (Exception ie) {
                ie.printStackTrace();
            }

            HttpClient httpC = new HttpClient();
            PostMethod postMethod = new PostMethod(clientURL);
            NameValuePair[] req = {
                new NameValuePair("grant_type", "client_credentials"),
                new NameValuePair("client_id", clientId),
                new NameValuePair("client_secret", clientSecret),};
            postMethod.setRequestBody(req);
            postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            try {
                httpC.executeMethod(postMethod);
                newCode = postMethod.getResponseBodyAsString();
                System.out.println("Post reply: " + postMethod.getResponseBodyAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("New code: " + newCode);
            ServerSocket ss = new ServerSocket(8080, 0, InetAddress.getLoopbackAddress());

            ss.accept();

            String sO = ss.toString();
            System.out.println(InetAddress.getLoopbackAddress());
            System.out.println(sO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
