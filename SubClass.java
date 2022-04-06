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
public class SubClass extends SuperClass {

    public void method() {
        super.superMethod();
        System.out.println("Sub Class -> method()");
    }
    public static void main( String args[]){
        SubClass obj = new SubClass();
        obj.method();
        obj.superMethod();
        SuperClass obj1 = new SubClass();
        obj1.superMethod();
        obj1.secondSuperMethod();
    }
}

class SuperClass {

    public void superMethod() {
        System.out.println("Super Class -> superMethod()");
    }
    public void secondSuperMethod(){
        System.out.println("Super Class -> secondSuperMethod()");
    }
}
