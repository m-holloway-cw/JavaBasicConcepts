/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

/**
 *
 * @author Raxa
 */
public class Constructor {

    private int a = 0;
    private String b = "Blank";

    public Constructor() {
        a = 10;
        b = "Default";
    }

    public Constructor(int a1, String b1) {
        a = a1;
        b = b1;
    }

    public Constructor(int a) {
        this.a = a;
    }

    private static Constructor obj = null;

    public static Constructor objectCreator() {
        if (obj == null) {
            obj = new Constructor();
        }
        return obj;
    }

    public int ConstructorAdd(int a1) {
        a = a + a1;
        return a;
    }

    public Constructor(String b) {
        this.b = b;
    }

    public void conMethod() {
        //System.out.println("Construction method");
        System.out.println("A: " + a + " B: " + b);
    }

    public void setInt(int a) {
        this.a = a;
    }

    public void setString(String b) {
        this.b = b;
    }

    public void setBoth(int a, String b) {
        this.a = a;
        this.b = b;
    }

    public int getInt() {
        return a;
    }

    public String getString() {
        return b;
    }

    public static void main(String args[]) {
        Constructor ct = new Constructor();
        ct.conMethod();
        ct.setBoth(1, "ct set");
        System.out.println("Both ct: " + ct.getInt() + ", " + ct.getString());
        Constructor ct1 = new Constructor("Test");
        ct1.conMethod();
        System.out.println("Both ct1: " + ct1.getInt() + ", " + ct1.getString());
        Constructor ct2 = new Constructor(1, "TestA");
        ct2.conMethod();
        System.out.println("Object creation method");
        Constructor object = Constructor.objectCreator();
        object.conMethod();
    }
}
