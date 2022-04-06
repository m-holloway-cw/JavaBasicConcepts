/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spellchecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JFileChooser;

/**
 *
 * @author Raxa
 */
public class SpellChecker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO download text file
        try {
            Scanner readTxt = new Scanner(new File("words.txt"));
            HashSet<String> set = new HashSet<String>();
            while (readTxt.hasNext()) {
                String word = readTxt.next();
                word = word.toLowerCase();
                set.add(word);
            }
            System.out.println("Size of set: " + set.size());
            File inputFile = getFileName();
            spellCheck(inputFile, set);

        } catch (FileNotFoundException e) {
            System.out.println("Text file not found.");
        }

    }

    static void spellCheck(File fileToCheck, HashSet dictionary) {
        try {
            Scanner readFile = new Scanner(fileToCheck);
            readFile.useDelimiter("[^a-zA-Z]+");
            TreeSet<String> duplicates = new TreeSet<String>();
            while (readFile.hasNext()) {
                String wordCheck = readFile.next();
                wordCheck = wordCheck.toLowerCase();
                if (!dictionary.contains(wordCheck)) {
                    if (!duplicates.contains(wordCheck)) {
                        duplicates.add(wordCheck);
                        TreeSet<String> corrections = corrections(wordCheck, dictionary);
                        Iterator<String> itr = corrections.iterator();
                        StringBuilder sb = new StringBuilder();
                        while (itr.hasNext()) {
                            sb.append(itr.next());
                            if (itr.hasNext()) {
                                sb.append(", ");
                            }
                        }
                        if (corrections.isEmpty()) {
                            System.out.println("Misspelled word: " + wordCheck + " Possible correction: (no suggestion)");
                        } else {
                            System.out.println("Misspelled word: " + wordCheck + " Possible correction: " + sb);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File lost in method transition");
        }
    }

    static File getFileName() {
        JFileChooser fileName = new JFileChooser();
        fileName.setDialogTitle("Select file by name:");
        int option = fileName.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION) {
            return null;
        } else {
            return fileName.getSelectedFile();
        }
    }

    static TreeSet corrections(String wrong, HashSet dictionary) {
        TreeSet<String> find = new TreeSet<String>();
        //begin delete any 1 letter
        for (int i = 0; i < wrong.length(); i++) {
            String check = wrong.substring(0, i) + wrong.substring(i + 1);
            if (dictionary.contains(check)) {
                find.add(check);
            }
        }

        //begin changing 1 letter 
        for (int i = 0; i < wrong.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                String check = wrong.substring(0, i) + ch + wrong.substring(i + 1);
                if (dictionary.contains(check)) {
                    find.add(check);
                }
            }
        }

        //begin insert letter at any point
        for (int i = 0; i < wrong.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                String check = wrong.substring(0, i + 1) + "" + ch + "" + wrong.substring(i + 1);
                if (dictionary.contains(check)) {
                    find.add(check);
                }
            }
        }

        //begin swap any neighboring character    
        for (int i = 0; i < wrong.length() - 1; i++) {
            String first = wrong.substring(i, i + 1);
            String second = wrong.substring(i + 1, i + 2);
            String check = wrong.substring(0, i) + second + first + wrong.substring(i + 2);
            if (dictionary.contains(check)) {
                find.add(check);
            }
        }

        //begin insert space at any point
        for (int i = 0; i < wrong.length(); i++) {
            String check = wrong.substring(0, i + 1) + " " + wrong.substring(i + 1);
            if (dictionary.contains(check)) {
                find.add(check);
            }
        }

        return find;
    }
}
 