package ru.nmago.crypt.sdes;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Nur-Magomed (nmago) on 06.03.2017.
 */

public class SDESKeyGen{

    private final boolean isDebug = true;

    private final byte[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6},
                         P8  = {6, 3, 7, 4, 8, 5, 10, 9};

    private int key, roundKey1, roundKey2;

    public SDESKeyGen(int key){
        this.key = key % 1024; //10 bits
    }

    public void setKey(int key){
        this.key = key % 1024; //10 bits
    }

    public int getRoundKey1(){
        return this.roundKey1;
    }

    public int getRoundKey2(){
        return this.roundKey2;
    }

    public void generate(){
        int[] gen = new int[10];
        gen = this.getKeyArray();
        debugShow(gen);
        this.permutation10(gen);
        debugShow(gen);
    }

    private void permutation10(int[] arr){
        int[] temp = new int[arr.length];
        System.arraycopy(arr, 0, temp, 0, arr.length);

        for(int i = 0; i < arr.length; i++) {
            arr[i] = temp[this.P10[i] - 1];
        }
    }

    private void leftShiftSubBlocks(int[] sequence, int shiftCount, int from, int to){
        shiftCount = shiftCount % (to - from);
        for(int c = 0; c < shiftCount; c++) {
            int[] A = {1,2,3,45};
            for (int i = from; i < to; i++) {

            }
        }
    }

    private int[] getKeyArray(){
        int[] keyArray = new int[10];
        int temp = this.key;
        for(int i = keyArray.length - 1; i >= 0; i--){
            keyArray[i] = temp % 2;
            temp /= 2;
        }
        return keyArray;
    }



    private void debugShow(int[] array){
        if(this.isDebug)
            System.out.println("SDESKeyGen: Length = " + array.length + "; " + Arrays.toString(array));
    }
}
