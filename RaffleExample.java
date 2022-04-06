/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Raxa
 */
public class RaffleExample {

    final static LinkedHashMap<String, Entrant<Integer, String>> MAP = new LinkedHashMap<String, Entrant<Integer, String>>();

    static List<String> prevWinner = new java.util.ArrayList<>();

    static List<String> currPool = new java.util.ArrayList<>();

    static int index = 0;

    static String winner;

    static String content;

    final static Random RNG = new Random();

    static int size = MAP.size();

    public static void main(String args[]) {
        enter("User1", "song choice user 1");
        enter("User2", "song 2");
        enter("User3", "song 3");
        enter("User4", "song 4");
        enter("User5", "song 5");
        enter("User6", "song 6");
        enter("User6", "song 6");
        enter("User7", "song 7");
        for (int i = 0; i < 3; i++) {
            draw();
        }
        MAP.entrySet().forEach((m) -> {
            System.out.print("Username: " + m.getKey() + " tickets: " + m.getValue().getTicket() + " || ");
        });
        System.out.println("");
        enter("User8", "song 8");
        enter("User9", "song 9");
        for (int i = 0; i < 2; i++) {
            draw();
        }
        enter("User10", "song 10");
        enter("User1", "song again");
        enter("User2", "song 2");
        enter("User3", "song 3");
        enter("User4", "song 4");
        enter("User5", "song 5");
        for (int i = 0; i < 4; i++) {
            //draw();
        }
        if (setContent("User4", "new song")) {
            System.out.println("Set content");
        } else {
            System.out.println("User not in lottery pool");
        }
        for (int i = 0; i < 2; i++) {
            draw();
        }
        System.out.print("Final map: ");
        MAP.entrySet().forEach((m) -> {
            System.out.print("Username: " + m.getKey() + " tickets: " + m.getValue().getTicket() + " song choice: " + m.getValue().getContent() + " || ");
        });
    }

    private static void draw() {
        int r = 0;
        size = MAP.size();
        for (int i = 0; i < size; i++) {
            List<String> sA = new java.util.ArrayList<>();
            MAP.entrySet().forEach((m) -> {
                int tempTickets = m.getValue().getTicket();
                while (tempTickets != 0) {
                    sA.add(m.getKey());
                    tempTickets--;
                }
            });
            r = RNG.nextInt(sA.size());
            winner = sA.get(r);
            //System.out.println(sA);
        }
        //System.out.println(r);
        System.out.println("Winner: " + winner + " song choice: " + MAP.get(winner).getContent());
        prevWinner.add(winner);
        MAP.remove(winner);
        currPool.remove(winner);
        MAP.entrySet().forEach((m) -> {
            int ticketValue = 2;
            for (String c : prevWinner) {
                if (c.equals(m.getKey())) {
                    ticketValue = 1;
                }
            }
            m.getValue().addTicket(ticketValue);
            //System.out.println(m.getKey() + " index: " + m.getValue().getIndex() + " content: " + m.getValue().getContent() + " ticket: " + m.getValue().getTicket());
        });

    }

    private static void enter(String user, String content) {
        for (String check : currPool) {
            if (check.equals(user)) {
                //System.out.println("User " + user + " already in pool");
                return;
            }
        }
        currPool.add(user);
        int ticketValue = 2;
        boolean p = true;
        for (String u : prevWinner) {
            if (u.equals(user)) {
                p = false;
                //System.out.println("User " + user + " added again at lower ticket rate");
                ticketValue = 1;
            }
        }
        if (p) {
            //System.out.println(user + " added");
        }
        MAP.put(user, new Entrant(index, content));
        index++;
        MAP.get(user).addTicket(ticketValue);
        MAP.entrySet().forEach((m) -> {
            // System.out.println("Current map item: " + m.getKey() + "  current tickets: " + m.getValue().getTicket());
        });
    }

    public static boolean setContent(String user, String content) {
        try {
            MAP.get(user).content = content;
            return true;
        } catch (NullPointerException ne) {
            return false;
        }
    }

    public static class Entrant<Integer, String> {

        private final int index;
        private String content;
        public int ticket;

        public Entrant(int index, String content) {
            this.index = index;
            this.content = content;
            this.ticket = 0;
        }

        public void addTicket(int pastWinner) {
            this.ticket += pastWinner;
        }

        public String getContent() {
            return this.content;
        }

        public int getIndex() {
            return this.index;
        }

        public int getTicket() {
            return this.ticket;
        }
    }
}
