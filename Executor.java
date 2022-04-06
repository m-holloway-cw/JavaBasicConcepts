/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Raxa
 */
public class Executor {
    private static ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
    public static void main(String[] args) {
        for(int i = 0; i < 3; i++){
            ses.scheduleWithFixedDelay(new Runnable(){
                int p = 0;
                @Override
                public void run(){
                    try{

                            System.out.println("Test" + p);

                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            },0, 2, TimeUnit.SECONDS);
        }
        System.out.println("Done");
    }
}
