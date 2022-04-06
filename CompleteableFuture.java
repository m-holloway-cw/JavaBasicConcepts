/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Raxa
 */
public class CompleteableFuture {

    public static void main(String... argv) throws Exception {
        ExampleService svc = new ExampleService();
        CompleteableFuture listener = new CompleteableFuture();
        CompletableFuture<String> f = CompletableFuture.supplyAsync(svc::work);
        f.thenAccept(listener::notify);
        while(!f.isDone()){
            Thread.sleep(1000);
        }
        System.out.println("Exiting main()");
    }

    void notify(String msg) {
        System.out.println("Received message: " + msg);
    }

}

class ExampleService {

    String work() {
        sleep(7000, TimeUnit.MILLISECONDS);
        /* Pretend to be busy... */
        char[] str = new char[5];
        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int idx = 0; idx < str.length; ++idx) {
            str[idx] = (char) ('A' + current.nextInt(26));
        }
        String msg = new String(str);
        System.out.println("Generated message: " + msg);
        return msg;
    }

    public static void sleep(long average, TimeUnit unit) {
        String name = Thread.currentThread().getName();
        long timeout = Math.min(exponential(average), Math.multiplyExact(10, average));
        System.out.printf("%s sleeping %d %s...%n", name, timeout, unit);
        try {
            unit.sleep(timeout);
            System.out.println(name + " awoke.");
        } catch (InterruptedException abort) {
            Thread.currentThread().interrupt();
            System.out.println(name + " interrupted.");
        }
    }

    public static long exponential(long avg) {
        return (long) (avg * -Math.log(1 - ThreadLocalRandom.current().nextDouble()));
    }

}
