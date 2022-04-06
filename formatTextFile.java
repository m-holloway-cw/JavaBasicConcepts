/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import javafx.application.Platform;

/**
 *
 * @author Raxa
 */
public class formatTextFile {

    public final static List<String> songList = new java.util.ArrayList<>();
    private static Scanner scan;

    public static void main(String[] args) {
        //create songlist based on text content
        try {
            Path location = Paths.get("");
            Path lResolved = location.resolve("noNumberSongList.txt");
            scan = new Scanner(lResolved);
            String temp = "";
            StringBuilder sb = new StringBuilder();
            String numSong = "";
            int num = 0;
            while (scan.hasNext()) {
                temp = scan.nextLine();

                //write string with numbers for only song lines
                
                if (temp.contains(":") || temp.contains("#") || temp.equals(" ")) {
                    //covers first line and just dance topic lines
                    sb.append(temp + "\n");
                } else {
                    temp = temp.replace("â€“", "-");
                    num++;
                    sb.append(num +". " + temp + "\n");
                }
            }
            System.out.println(sb);
            System.out.println("Successfully parsed song list from jdSongs.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occured trying to create song list");
        }
    }
}
