package ru.nmago.crypt.sdes;

import java.util.Arrays;

/**
 * Created by Nur-Magomed (nmago) on 06.03.2017.
 */

public class SDESKeyGen{

    private final boolean isDebug = false;

    private final byte[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6},
                         P8  = {6, 3, 7, 4, 8, 5, 10, 9};

    private int key, roundKey1, roundKey2;
    private int[] roundKey1Seq, roundKey2Seq;


    public SDESKeyGen(int key){
        this.setKey(key);
    }

    public void setKey(int key){
        this.key = key % 1024; //10 bits
    }

    public int getRoundKey(int keyNumber){
        return (keyNumber == 1) ? this.roundKey1 : this.roundKey2;
    }

    public int[] getRoundKeyAsSeq(int keyNumber){
        return (keyNumber==1) ? this.roundKey1Seq : this.roundKey2Seq;
    }

    public void generate(){
        int[] genSequence;
        genSequence = SDESUtils.getSequenceByNum(this.key, 10);
        genSequence = permutate10(genSequence);
        genSequence = leftShift1(genSequence);

        roundKey1Seq = permutate8(genSequence); //get round key sequence 1

        genSequence = leftShift1(genSequence);

        roundKey2Seq = permutate8(genSequence); //get round key sequence 2
        debugShow(roundKey1Seq);
        debugShow(roundKey2Seq);
        roundKey1 = SDESUtils.getNumBySequence(this.roundKey1Seq);
        roundKey2 = SDESUtils.getNumBySequence(this.roundKey2Seq);
    }

    public int[] permutate10(int[] sequence){ // must be private, public for tests
        return SDESUtils.getPermutated(sequence, this.P10);
    }

    public int[] permutate8(int[] sequence){ // must be private, public for tests
        return SDESUtils.getPermutated(sequence, this.P8);
    }

    private int[] leftShift1(int[] sequence){
        int[] result;
        result = SDESUtils.cyclicLS(sequence, 1, 0,5); //LS-1 first 5 bits [0,1,2,3,4]
        debugShow(result);
        result = SDESUtils.cyclicLS(result, 1, 5,10);//LS-1 second 5 bits [5,6,7,8,9]
        debugShow(result);
        return result;
    }


    private void debugShow(int[] array){
        if(this.isDebug)
            System.out.println("SDESKeyGen: Length = " + array.length + "; " + Arrays.toString(array));
    }
}
