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
public class Interface {

    public static void main(String args[]) {
        SumClass object = new SumClass();
        System.out.println("1 + 2 = " + object.method(1, 2));
        System.out.println("643 + 2839 = " + object.method(643, 2839));
        Display obj = new Display();
        obj.print();
        obj.show();
    }
}

interface Printable {

    void print();
}

interface Showable extends Printable {

    void show();
}

class Display implements Showable {

    public void print() {
        System.out.println("Hello");
    }

    public void show() {
        System.out.println("World");
    }
}

interface Interface1 {

    public int method(int a, int b);
}

class SumClass implements Interface1 {

    @Override
    public int method(int a, int b) {
        int sum = a + b;
        return sum;
    }

}
