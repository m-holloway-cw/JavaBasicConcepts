/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmarksort;

import java.util.Arrays;
import java.util.Timer;

/**
 * Insertion method took 0.03 seconds for an array of 1000 numbers Array.sort
 * method took 0.013 seconds for an array of 1000 numbers.
 *
 * Insertion method took 0.103 seconds for an array of 10000 numbers Array.sort
 * method took 0.071 seconds for an array of 10000 numbers.
 *
 * Insertion method took 0.696 seconds for an array of 100000 numbers Array.sort
 * method took 0.651 seconds for an array of 100000 numbers.
 *
 * Array.sort method took 6.168 seconds for an array of 1000000 numbers.
 */
public class BenchmarkSort extends Thread {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {

       /* long startTime = System.currentTimeMillis();
        int arraySize = 10000000;
        int[] arrayOne = new int[arraySize];
        for (int i = 0; i < arrayOne.length; i++) {
            arrayOne[i] = (int) (Integer.MAX_VALUE * Math.random());
        }
        int[] arrayInsert = insertionSort(arrayOne);
        
        for (int j:arrayInsert){
            //System.out.print(j);
           // System.out.print(", ");
        }
        System.out.println("");
        double endTime = System.currentTimeMillis() - startTime;
        System.out.println("Insertion method took " + endTime/1000 + " seconds for an array of " + arraySize + " numbers");
        System.out.println("");
        int[] arrayTwo = arrayOne;
        long startSort = System.currentTimeMillis();
        Arrays.sort(arrayTwo);
        for (int k:arrayTwo){
        //System.out.print(k);
        //System.out.print(", ");
        }
        System.out.println("");
        double endSort = System.currentTimeMillis() - startSort;
        System.out.println("Array.sort method took " + endSort/1000 + " seconds for an array of " + arraySize + " numbers.");*/
       System.out.println(Math.random() + " RANDOM " + Math.random());
    }
    
    public static int[] insertionSort(int[] sortArray){
        int temp;
        for (int i = 1; i < sortArray.length; i++) {
            for(int j = 1; j >0; j--){
                if(sortArray[j] < sortArray[j-1]){
                    temp = sortArray[j];
                    sortArray[j] = sortArray[j-1];
                    sortArray[j-1] = temp;
                }
            }
        }
        return sortArray;
    }
}