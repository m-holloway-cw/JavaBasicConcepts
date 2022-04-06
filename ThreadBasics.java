/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 *
 * @author Raxa
 */
class PrintDemo {
   public void printCount() {
      try {
         for(int i = 5; i > 0; i--) {
            System.out.println("Counter   ---   "  + i );
         }
      } catch (Exception e) {
         System.out.println("Thread  interrupted.");
      }
   }
}
class ThreadDemo extends Thread {
   private Thread t;
   private String threadName;
   PrintDemo  PD;

   ThreadDemo(String name,  PrintDemo pd) {
      threadName = name;
      PD = pd;
   }
   public void run() {
      synchronized(PD) {
         PD.printCount();
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }
   public void start () {
      System.out.println("Starting " +  threadName );
      
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}
public class ThreadBasics {
   public static void main(String args[]) {
      PrintDemo PD = new PrintDemo();

      ThreadDemo T1 = new ThreadDemo("Thread - 1 ", PD);
      ThreadDemo T2 = new ThreadDemo("Thread - 2 ", PD);

      T1.start();
      T2.start();

      // wait for threads to end
      try {
         T1.join();
         T2.join();
      } catch (Exception e) {
         System.out.println("Interrupted");
      }
      Chat m = new Chat();
      new T1(m);
      new T2(m);
      
            PrintDemo1 PD1 = new PrintDemo1();

      ThreadDemo1 T11 = new ThreadDemo1("Thread - 1 ", PD1);
      ThreadDemo1 T21 = new ThreadDemo1("Thread - 2 ", PD1);

      T11.start();
      T21.start();

      // wait for threads to end
      try {
         T11.join();
         T21.join();
      } catch (Exception e) {
         System.out.println("Interrupted");
      }
   }
}

class Chat {
   boolean flag = false;
   public synchronized void Question(String msg) {
      if (flag) {
         try {
            wait();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      System.out.println(msg);
      flag = true;
      notify();
   }
   public synchronized void Answer(String msg) {
      if (!flag) {
         try {
            wait();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      System.out.println(msg);
      flag = false;
      notify();
   }
}
class T1 implements Runnable {
   Chat m;
   String[] s1 = { "Hi", "How are you ?", "I am also doing fine!" };
    
   public T1(Chat m1) {
      this.m = m1;
      new Thread(this, "Question").start();
   }
   public void run() {
      for (int i = 0; i < s1.length; i++) {
         m.Question(s1[i]);
      }
   }
}
class T2 implements Runnable {
   Chat m;
   String[] s2 = { "Hi", "I am good, what about you?", "Great!" };

   public T2(Chat m2) {
      this.m = m2;
      new Thread(this, "Answer").start();
   }
   public void run() {
      for (int i = 0; i < s2.length; i++) {
         m.Answer(s2[i]);
      }
   }
}

class PrintDemo1 {
   public void printCount() {
      try {
         for(int i = 5; i > 0; i--) {
            System.out.println("Counter   ---   "  + i );
         }
      } catch (Exception e) {
         System.out.println("Thread  interrupted.");
      }
   }
}
class ThreadDemo1 extends Thread {
   private Thread t;
   private String threadName;
   PrintDemo1  PD;

   ThreadDemo1(String name,  PrintDemo1 pd) {
      threadName = name;
      PD = pd;
   }
   public void run() {
      synchronized(PD) {
         PD.printCount();
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }
   public void start () {
      System.out.println("Starting " +  threadName );
      
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}
