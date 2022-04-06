/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Raxa
 */
public class UserQueue {

    boolean enabled = true;
    public static java.util.ArrayList<String> namesNonSub = new java.util.ArrayList<>();
    public static java.util.ArrayList<String> namesSub = new java.util.ArrayList<>();
    private final static LinkedHashMap<String, Integer> MAP = new LinkedHashMap<>();

    private int size = MAP.size();

    private final List<String> prevWinner = new java.util.ArrayList<>();

    private final List<String> currPool = new java.util.ArrayList<>();

    public static void main(String[] args) {
        namesSub.add("Test1");
        namesNonSub.add("Stskjfld");
        namesSub.add("Test3");
        namesNonSub.add("Stskjf6d");
        namesSub.add("Test4");
        namesNonSub.add("Stsk5fld");
        namesSub.add("Test5");
        namesNonSub.add("Stskj5ld");
        popUser("!pop 7");

        MAP.put("user", 7);
        MAP.put("user1", 7);
        MAP.put("user2", 7);
        MAP.put("user3", 7);
        MAP.put("user4", 7);
        MAP.put("user5", 7);
        MAP.put("user6", 7);
        MAP.put("user7", 7);
        MAP.put("user8", 7);
        MAP.entrySet().forEach((m) -> {
            System.out.println("Current map item: " + m.getKey() + "  current tickets: " + m.getValue());
        });
        //Write to file
        try {
            FileOutputStream fout = new FileOutputStream("file.out");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(MAP);

            //Read from file
            FileInputStream fin = new FileInputStream("file.out");
            ObjectInputStream ois = new ObjectInputStream(fin);
            Map<String, Integer> m2 = (LinkedHashMap<String, Integer>) ois.readObject();
            m2.entrySet().forEach((m) -> {
                System.out.println("Current map item: " + m.getKey() + "  current tickets: " + m.getValue());
            });
        } catch (Exception e) {

        }
    }

    public static void popUser(String num) {
        boolean enabled = true;
        int usersToDraw;
        int nonSubsDraw = 0;
        int totalEntries = namesSub.size() + namesNonSub.size();
        if (!enabled) {
            sendMessage("Queue is disabled, !queue-open to reopen queue.");
            return;
        }
        if (num.trim().equals("!pop")) {
            usersToDraw = 1;
        } else {
            num = num.substring(num.indexOf(" ") + 1, num.length());
            usersToDraw = Integer.parseInt(num);
        }
        StringBuilder sb = new StringBuilder();
        if (namesSub.isEmpty() && namesNonSub.isEmpty()) {
            sendMessage("Queue is empty!");
        } else if (usersToDraw > totalEntries) {
            sendMessage("Not enough entries!");
        } else {
            int subsDraw = usersToDraw;
            if (usersToDraw > namesSub.size()) {
                subsDraw = namesSub.size();
                nonSubsDraw = usersToDraw - subsDraw;
            }
            System.out.println(subsDraw + " " + nonSubsDraw + " " + usersToDraw);
            if (subsDraw > 0) {
                //draw from sub queue first
                for (int i = 0; i < subsDraw; i++) {
                    sb.append(namesSub.get(0) + " ");
                    System.out.println("Next: " + namesSub.get(0));
                    namesSub.remove(0);
                    sendMessage(namesSub.toString());
                    sendMessage(sb.toString());
                }
                //draw from non sub queue second
                for (int i = 0; i < nonSubsDraw; i++) {
                    sb.append(namesNonSub.get(0) + " ");
                    namesNonSub.remove(0);
                    sendMessage(namesNonSub.toString());
                }
            } else {
                //draw from non sub queue second
                for (int i = 0; i < nonSubsDraw; i++) {
                    sb.append(namesNonSub.get(0) + " ");
                    namesNonSub.remove(0);
                    sendMessage(namesNonSub.toString());
                }
            }
            if (usersToDraw == 1) {
                sendMessage(sb + "is up and has been removed from the queue!");
            } else {
                sendMessage(sb + "are up and have been removed from the queue!");
            }
            System.out.println(namesSub.toString());
            System.out.println(namesNonSub.toString());
        }
    }

    public static void sendMessage(String msg) {
        System.out.println(msg);
    }
}
