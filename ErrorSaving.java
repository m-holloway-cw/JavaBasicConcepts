/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.io.FileWriter;

/**
 *
 * @author Raxa
 */
public class ErrorSaving {
    public static void main(String[] args) {
        try{
            FileWriter fW = new FileWriter("Error.log", true);
        StackTraceElement[] e = Thread.currentThread().getStackTrace();
        for (int i = 0; i <e.length; i++){
            StackTraceElement s = e[i];
            String error = ("at " + s.getClassName() + "." + s.getMethodName()
        + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
            System.out.println(error);
            fW.write(error);
        }
        fW.write("\nEnd of error");
        fW.write("\n\n");
        fW.close();
        
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
