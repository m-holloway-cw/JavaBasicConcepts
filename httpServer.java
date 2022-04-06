/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.apache.hc.client5.http.utils.URLEncodedUtils;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Raxa
 */
public class httpServer {

    static String profilePic = "";
    static String displayName = "";
    static String state = "randomAuthenticationString";
    static Map<String, String> ADMINS = new LinkedHashMap<>();

    public static void main(String[] args) {
        try {
            ADMINS.put("raxa", "michaelx232@gmail.com");
            ADMINS.put("kungfufruitcup", "kungfufruitcup@gmail.com");
            com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8027), 0);

            server.createContext("/api", new apiHandler());
            //api.setAuthenticator(new BasicAuthenticator("test") {
            //  @Override
            // public boolean checkCredentials(String user, String pwd) {
            //    return user.equals("test") && pwd.equals("test");
            // }
            //   });

            HttpContext auth = server.createContext("/auth", new authHandler());
            auth.setAuthenticator(new BasicAuthenticator("test") {
                @Override
                public boolean checkCredentials(String user, String pwd) {
                    return user.equals("test") && pwd.equals("test");
                }
            });

            HttpContext commands = server.createContext("/commands", new commandHandler());
            commands.setAuthenticator(new BasicAuthenticator("test") {
                @Override
                public boolean checkCredentials(String user, String pwd) {
                    return user.equals("test") && pwd.equals("test");
                }
            });

            HttpContext queue = server.createContext("/queue", new queueHandler());
            queue.setAuthenticator(new BasicAuthenticator("test") {
                @Override
                public boolean checkCredentials(String user, String pwd) {
                    return user.equals("test") && pwd.equals("test");
                }
            });

            HttpContext getQ = server.createContext("/getQ", new getQHandler());
            getQ.setAuthenticator(new BasicAuthenticator("test") {
                @Override
                public boolean checkCredentials(String user, String pwd) {
                    return user.equals("test") && pwd.equals("test");
                }
            });

            //server.createContext("/form", new formHandler());
            HttpContext cc = server.createContext("/form", new formHandler());
            cc.setAuthenticator(new BasicAuthenticator("test") {
                @Override
                public boolean checkCredentials(String user, String pwd) {
                    return user.equals("test") && pwd.equals("test");
                }
            });
            //server.setExecutor(Executors.newCachedThreadPool());
            server.setExecutor(null);
            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class apiHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println(t.getRequestMethod());
            if (t.getRequestMethod().equals("GET") || t.getRequestMethod().equals("OPTIONS")) {
                System.out.println("get request");
                //check for params
                String q = "";
                List<NameValuePair> params1 = URLEncodedUtils.parse(t.getRequestURI(), "UTF-8");
                for (NameValuePair param : params1) {
                    System.out.println(param.getName());
                    if (param.getName().equals("command")) {
                        System.out.println(param.getValue());
                        q = param.getValue();
                    }
                }
                // }
                System.out.println("q = " + q);
                //build out response
                //full reply if query is missing else send specific data
                if (!q.equals("")) {

                    String response = "{\"data\" :[{\"command\":\"" + q + "\", \"text\": \"abc 123\"}]}";

                    t.getResponseHeaders().set("Content-Type", "application/json");
                    t.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                    t.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
                    t.sendResponseHeaders(200, response.length());
                    //t.close();
                    OutputStream os = t.getResponseBody();
                    System.out.println(response);
                    os.write(response.getBytes());
                    os.close();
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 100; i++) {
                        sb.append("{\"command\":\"!test").append(i).append("\", \"text\":\"text message\", \"auth\":\"+a\", \"cooldown\":\"30\", \"interval\":\"30\", \"sound\":\"sound.mp3\", \"enabled\":\"true\"},");
                    }
                    String response = "{\"data\" :[" + sb.toString() + "]";
                    response = response.substring(0, response.length() - 2);
                    //add end of json
                    response = response + "]}";

                    t.getResponseHeaders().set("Content-Type", "application/json");
                    t.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                    t.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
                    t.sendResponseHeaders(200, response.length());
                    //t.close();
                    OutputStream os = t.getResponseBody();
                    System.out.println(response);
                    os.write(response.getBytes());
                    os.close();
                }
            } else if (t.getRequestMethod().equals("POST")) {
                System.out.println("request found");
                //check authorization header for match to our token
                String auth = t.getRequestHeaders().getFirst("Authorization");
                System.out.println(auth);
                String response;
                if (auth.equals("Bot RANDOM TOKEN")) {
                    System.out.println("successful auth in post");

                    StringBuilder sb = new StringBuilder();
                    InputStream ios = t.getRequestBody();
                    int i;
                    while ((i = ios.read()) != -1) {
                        sb.append((char) i);
                    }
                    System.out.println("hm: " + sb.toString());
                    //send the json string to our handler

                    //reply with 200 ok to frontend
                    response = "{\"status\" :\"200 OK\"}";
                    t.sendResponseHeaders(200, response.length());
                } else {
                    response = "{\"status\":\"401 Not Authorized\"}";
                    t.sendResponseHeaders(401, response.length());
                }
                t.getResponseHeaders().set("Content-Type", "application/json");
                t.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                t.getResponseHeaders().set("Access-Control-Allow-Headers", "*");  
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    static class authHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {

            System.out.println();
            StringBuilder sb = new StringBuilder();
            InputStream ios = t.getRequestBody();
            int i;
            while ((i = ios.read()) != -1) {
                sb.append((char) i);
            }
            System.out.println("hm: " + sb.toString());
            List<String> cookies = t.getRequestHeaders().get("Cookies");
            System.out.println("cookies: " + cookies);
            String oauthLink = "https://id.twitch.tv/oauth2/authorize?client_id=CLIENTID&redirect_uri=http://localhost:8027/commands&force_verify=true&response_type=code&scope=user:read:email&state=" + state;
           
            String response = " <html>\n"
                    + "<body>\n"
                    + "\n"
                    + "<form action=\"http://localhost:8027/auth\" method=\"post\">\n"
                    + "input: <input type=\"text\" name=\"input\"><br>\n"
                    + "input2: <input type=\"text\" name=\"input2\"><br>\n"
                    + "<input type=\"submit\">\n"
                    + "</form>\n"
                    + "<p><a href=\"" + oauthLink + "\"><img src=\"/twitchButton.png\" alt=\"Click here to login with Twitch\"</a></p>"
                    + "</body>\n"
                    + "</html> ";

            t.sendResponseHeaders(200, response.length());
            //t.close();
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }

    }

    static class formHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {

            if (authCheck(t)) {
                //access denied
                String response = " <html>\n"
                        + "<meta http-equiv=\"refresh\" content=\"0; URL=\'http://localhost:8027/commands\'\"/>"
                        + "</html> ";
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                if (t.getRequestMethod().equals("GET")) {
                    //check for params
                    List<NameValuePair> params = URLEncodedUtils.parse(t.getRequestURI(), "UTF-8");
                    for (NameValuePair param : params) {
                        if (param.getName().equals("success")) {
                            if (param.getValue().equals("true")) {
                                //whatever we sent from here was a success
                                System.out.println("success = true");
                            } else if (param.getValue().equals("false")) {
                                //whatever we sent from here failed
                                System.out.println("success = fail");
                            }
                        }
                        //System.out.println(param.getName() + " : " + param.getValue());
                    }
                    StringBuilder sb = new StringBuilder();
                    InputStream ios = t.getRequestBody();
                    int i;
                    while ((i = ios.read()) != -1) {
                        sb.append((char) i);
                    }
                    System.out.println("hm: " + sb.toString());

                    String response = " <html>\n"
                            + "<body>\n"
                            + "\n"
                            + "<form action=\"http://localhost:8027/form\" method=\"post\">\n"
                            + "input: <input type=\"text\" name=\"input\"><br>\n"
                            + "input2: <input type=\"text\" name=\"input2\"><br>\n"
                            + "<input type=\"submit\">\n"
                            + "</form>\n"
                            + "\n"
                            + "</body>\n"
                            + "</html> ";
                    t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else if (t.getRequestMethod().equals("POST")) {
                    handlePostRequest(t);
                }
            }
        }

        String handlePostRequest(HttpExchange he) {
            if (!authCheck(he)) {
                System.out.println("failed auth check");
            } else {
                try {
                    InputStream in = he.getRequestBody();
                    int read = -1;
                    StringBuilder sb = new StringBuilder();
                    while ((read = in.read()) != -1) {
                        //System.out.println((char) read);
                        sb.append((char) read);
                    }
                    System.out.println(sb.toString());
                    //parse data
                    String input = sb.toString();
                    parseCommandPost(input);
                    in.close();
                    //send back to get version
                    redirect(he, "form");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "POST";
        }

        //comes in as nameOfInput=Value&nameOfInput2=Value2
        void parseCommandPost(String input) {
            List<NameValuePair> params = URLEncodedUtils.parse(input, Charset.forName("UTF-8"));
            params.forEach((param) -> {
                System.out.println(param.getName() + " " + param.getValue());
            });
            //do something with the data here
            //most likely send to backend as a command or json
        }

    }

    static class commandHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) {
            //decide whether get or post
            try {

                displayName = "";
                profilePic = "";
                System.out.println(t.getHttpContext().getAuthenticator().authenticate(t).toString());

                System.out.println("Method:" + t.getRequestMethod());
                System.out.println(t.getRequestHeaders().keySet());
                List<String> auth = t.getRequestHeaders().get("Authorization");
                System.out.println("auth: " + auth);
                List<String> cookies = t.getRequestHeaders().get("Cookie");
                try {
                    cookies.forEach((cookie) -> {
                        System.out.println("cookie: " + cookie);
                    });
                } catch (NullPointerException ne) {
                    //ignore as this just means the cookies are empty from the initial request
                }
                List<String> conn = t.getRequestHeaders().get("Connection");
                System.out.println("conn: " + conn);
                List<String> host = t.getRequestHeaders().get("Host");
                System.out.println("host: " + host);
                //simple security check
                //if cookies are null or auth list is empty, send to default login page as this is an unwanted access
                if (cookies == null || auth.isEmpty()) {
                    handleResponse(t, "Login to proceed");
                } else {
                    if ("GET".equals(t.getRequestMethod())) {
                        handleResponse(t, handleGetRequest(t));
                    } else if ("POST".equals(t.getRequestMethod())) {
                        handleResponse(t, handlePostRequest(t));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String handleGetRequest(HttpExchange he) {
            try {
                //split up params at end of ui 
                String token = "";
                String accessToken = "";
                JsonNode data;
                String channelName = "";
                displayName = "";
                profilePic = "";
                List<NameValuePair> params = URLEncodedUtils.parse(he.getRequestURI(), "UTF-8");
                for (NameValuePair param : params) {
                    if (param.getName().equals("code")) {
                        token = param.getValue();
                        accessToken = getAccessToken(token);
                    }
                    if (param.getName().equals("state")) {
                        if (!param.getValue().equals(state)) {
                            //unauthenticated session, kick out
                            he.close();
                            return "unauthorized";
                        }
                    }
                    System.out.println(param.getName() + " : " + param.getValue());
                }
                if (!token.equals("") && !accessToken.equals("")) {
                    //double check the channelName and email against our records for admins
                    data = getUser(accessToken);
                    channelName = data.get("data").get("login").asText();
                    displayName = data.get("data").get("display_name").asText();
                    String email = data.get("data").get("email").asText();
                    profilePic = data.get("data").get("profile_image_url").asText();
                    if (!checkAuth(channelName, email)) {
                        //failed check
                        displayName = "unauthorized";
                        //clear all token/info
                        channelName = "";
                        email = "";
                        profilePic = "";
                        token = "";
                        accessToken = "";
                    }
                    System.out.println("channel login: " + channelName);
                    System.out.println("email: " + email);
                }
                if (displayName.equals("")) {
                    displayName = ", please login with <a href=\"http://localhost:8027/auth\">Twitch.</a>";
                }

                return displayName;
            } catch (ArrayIndexOutOfBoundsException ae) {
                //ignore as this means there were no parameters at end of url
                return "empty";
            } catch (NullPointerException ne) {
                return "unauthorized";
            }
        }

        String getAccessToken(String token) {
            try {
                String tokenRequestURL = "https://id.twitch.tv/oauth2/token"
                        + "?client_id=CLIENTID"
                        + "&client_secret=SECRET"
                        + "&code=" + token
                        + "&grant_type=authorization_code"
                        + "&redirect_uri=http://localhost:8027/commands";

                HttpURLConnection post = (HttpURLConnection) new URL(tokenRequestURL).openConnection();
                post.setRequestMethod("POST");
                post.setDoOutput(true);
                post.setRequestProperty("Content-Type", "application/json");
                post.setRequestProperty("User-Agent", "Mozilla/5.0");
                System.out.println("POST REPLY: " + post.getResponseMessage());
                BufferedReader reader = new BufferedReader(new InputStreamReader(post.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String aC = sb.toString();
                //get our accesstoken
                int beginAccess = aC.indexOf("token\":") + 8;
                int endAccess = aC.indexOf("\"", beginAccess);
                aC = aC.substring(beginAccess, endAccess);
                System.out.println("AC= " + aC);
                return aC;
            } catch (IOException ie) {
                //incorrect data sent to us
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        JsonNode getUser(String accessToken) {
            try {
                System.out.println("access token: " + accessToken);
                HttpURLConnection con = (HttpURLConnection) new URL("https://api.twitch.tv/helix/users").openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.85 Safari/537.36");
                con.setRequestProperty("Client-ID", "x1irw4ybuwuaxuysm2h7sz2q06nmw8");
                con.setRequestProperty("Authorization", "Bearer " + accessToken);
                BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = read.readLine()) != null) {
                    sb.append(line + " \n");
                }
                String reply = sb.toString().replace("[", "").replace("]", "");
                JsonNode data = new ObjectMapper().readTree(reply);

                return data;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        static boolean auth;

        boolean checkAuth(String user, String email) {

            //System.out.println("user: " + user + "  email: " + email);
            //if our admin keys match approve access
            auth = false;
            ADMINS.entrySet().forEach((m) -> {
                if (m.getKey().equals(user)) {
                    auth = (m.getKey().equals(user) && m.getValue().equals(email));
                }
            });
            //System.out.println(auth);
            return auth;
        }

        String handlePostRequest(HttpExchange he) {
            if (!authCheck(he)) {
                System.out.println("failed auth check");
            } else {
                try {
                    InputStream in = he.getRequestBody();
                    int read = -1;
                    StringBuilder sb = new StringBuilder();
                    while ((read = in.read()) != -1) {
                        //System.out.println((char) read);
                        sb.append((char) read);
                    }
                    System.out.println(sb.toString());
                    //parse data
                    String input = sb.toString();
                    parseCommandPost(input);
                    in.close();
                    //send back to get version
                    redirect(he, "form");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "POST";
        }

        //comes in as nameOfInput=Value&nameOfInput2=Value2
        void parseCommandPost(String input) {
            List<NameValuePair> params = URLEncodedUtils.parse(input, Charset.forName("UTF-8"));
            params.forEach((param) -> {
                System.out.println(param.getName() + " " + param.getValue());
            });
            //do something with the data here
            //most likely send to backend as a command or json
        }

        void handleResponse(HttpExchange he, String request) throws IOException {
            OutputStream out = he.getResponseBody();
            StringBuilder sb = new StringBuilder();
            if (request.equals("unauthorized")) {
                String response = " <html>\n"
                        + "<a href=\"" + "http://localhost:8027/auth" + "\"><img src=\"/twitchButton.png\" alt=\"Click here to login with Twitch\"</a></p>"
                        + "</html> ";
                he.sendResponseHeaders(401, response.length());
                out.write(response.getBytes());
                out.flush();
                out.close();
            } else {
                Headers responseHeaders = he.getResponseHeaders();
                List<String> values = new ArrayList();
                Random rng = new Random();
                values.add("CUSTOM_COOKIE=TEST COOKIE THING" + String.valueOf(rng.nextInt(20785)) + displayName);
                responseHeaders.put("Set-Cookie", values);

                sb.append("<html><body><h1>Hello ");
                sb.append(request);
                sb.append("</h1><img src=\"" + profilePic + "\"><p>Go to form page: <a href=\"http://localhost:8027/form\">here</a></p></body></html>");
                he.sendResponseHeaders(200, sb.toString().length());
                out.write(sb.toString().getBytes());
                out.flush();
                out.close();

            }
        }
    }

    static class queueHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) {
            try {

                String response = "<html><body><form onsubmit=\"buttonFunction()\"><button type=\"submit\">button</button></form> </body>"
                        + "<script>function buttonFunction(){console.log(\"form submitted\");}</script>"
                        + "</html>";
                t.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class getQHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) {
            try {
                System.out.println("Exchanged received");

                System.out.println("headers:");
                t.getRequestHeaders().entrySet().forEach(System.out::println);

                System.out.println(t.getRequestHeaders());

                InputStream in = t.getRequestBody();
                int read = -1;
                while ((read = in.read()) != -1) {
                    System.out.println((char) read);
                }
                in.close();
                String response = "<html><body><button onclick=\"buttonClick()\">clicky button</button></body></html>";
                t.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static boolean authCheck(HttpExchange t) {
        //request much include our unique cookie(s) 
        //valid auth
        //valid ref(if not the login page)
        boolean allowed = false;
        try {
            System.out.println(t.getRequestHeaders().keySet());

            List<String> auth = t.getRequestHeaders().get("Authorization");

            List<String> cookies = t.getRequestHeaders().get("Cookie");

            cookies.forEach((cookie) -> {
                System.out.println("cookie: " + cookie);
            });

            List<String> conn = t.getRequestHeaders().get("Connection");
            System.out.println("conn: " + conn);
            List<String> host = t.getRequestHeaders().get("Host");
            System.out.println("host: " + host);
            List<String> ref = t.getRequestHeaders().get("Referer");
            System.out.println("ref: " + ref);

            String secretCookie = "TEST COOKIE THING";
            String baseURL = host.get(0);
            //if any param is missing or invalid deny access
            boolean authE = auth.isEmpty();
            boolean cookieE = cookies.isEmpty();
            boolean connE = conn.isEmpty();
            boolean hostE = host.isEmpty();
            boolean refE = ref.get(0).contains(baseURL);
            boolean cookieC = cookies.get(0).contains(secretCookie);

            System.out.println(authE + "" + cookieE + connE + hostE + refE + cookieC);
            if (!authE && !cookieE && !connE && !hostE && refE && cookieC) {
                allowed = true;
            } else {
                allowed = false;
            }

        } catch (NullPointerException ne) {
            //missing or invalid header
            allowed = false;
        }
        System.out.println("allowed? " + allowed);
        return allowed;
    }

    //generic method to send user to a new destination
    static void redirect(HttpExchange t, String destination) {
        try {
            String response = " <html>\n"
                    + "<meta http-equiv=\"refresh\" content=\"0; URL=\'http://localhost:8027/" + destination + "?success=true\'\"/>"
                    + "</html> ";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
