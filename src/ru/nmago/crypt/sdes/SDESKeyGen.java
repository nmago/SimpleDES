package ru.nmago.crypt.sdes;

import java.util.Arrays;

/**
 * Created by Nur-Magomed (nmago) on 06.03.2017.
 */

public class SDESKeyGen{

    private final boolean isDebug = true;

    private final byte[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6},
                         P8  = {6, 3, 7, 4, 8, 5, 10, 9};

    private int key, roundKey1, roundKey2;
    private int[] roundKey1Seq, roundKey2Seq;

    public SDESKeyGen(int key){
        this.key = key % 1024; //10 bits
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
        genSequence = this.getKeyAsSequence();

        genSequence = this.permutate10(genSequence);

        this.cyclicLS(genSequence, 1, 0,5); //LS-1 first 5 bits [0,1,2,3,4]
        this.cyclicLS(genSequence, 1, 5,10);//LS-1 second 5 bits [5,6,7,8,9]

        this.roundKey1Seq = this.permutate8(genSequence); //we get round key 1

        this.cyclicLS(genSequence, 1, 0,5); //LS-1 first 5 bits [0,1,2,3,4]
        this.cyclicLS(genSequence, 1, 5,10);//LS-1 second 5 bits [5,6,7,8,9]

        this.roundKey2Seq = this.permutate8(genSequence); //we get round key 2

        this.roundKey1 = this.getKeyAsNumber(this.roundKey1Seq);
        this.roundKey2 = this.getKeyAsNumber(this.roundKey2Seq);
    }

    public int[] permutate10(int[] sequence){ // must be private, public for tests
        return this.permutate(sequence, this.P10);
    }

    public int[] permutate8(int[] sequence){ // must be private, public for tests
        return this.permutate(sequence, this.P8);
    }

    private int[] permutate(int[] sequence, byte[] permutator){
        int[] permutatedSeq = new int[permutator.length];

        for(int i = 0; i < permutator.length; i++) {
            permutatedSeq[i] = sequence[permutator[i]-1];
        }
        //debugShow(permutatedSeq);
        return permutatedSeq;
    }

    public void cyclicLS(int[] sequence, int shiftCount, int from, int to){ // must be private, public for tests
        shiftCount = shiftCount % (to - from);
        for(int c = 0; c < shiftCount; c++) {
            int firstElem = sequence[from];
            System.arraycopy(sequence, from + 1, sequence, from, to - from - 1);
            sequence[to-1] = firstElem;
        }
    }

    private int[] getKeyAsSequence(){
        int[] keyArray = new int[10];
        int temp = this.key;
        for(int i = keyArray.length - 1; i >= 0; i--){
            keyArray[i] = temp % 2;
            temp /= 2;
        }
        return keyArray;
    }

    private int getKeyAsNumber(int[] sequence){
        int key = 0, power = 0;
        for (int i = sequence.length-1; i >= 0; i--){
            if(sequence[i] == 1)
                key+= sequence[i] * Math.pow(2, power);
            power++;
        }
        return key;
    }



    private void debugShow(int[] array){
        if(this.isDebug)
            System.out.println("SDESKeyGen: Length = " + array.length + "; " + Arrays.toString(array));
    }
}
