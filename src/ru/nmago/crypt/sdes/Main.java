package ru.nmago.crypt.sdes;

import ru.nmago.crypt.sdes.SDESKeyGen;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	    SDESKeyGen kg = new SDESKeyGen(717); //test on key = 717
	    kg.generate();
        System.out.println("Round Key1 : " + Arrays.toString(kg.getRoundKeyAsSeq(1))
                + "; As num = " + kg.getRoundKey(1));
        System.out.println("Round Key2 : " + Arrays.toString(kg.getRoundKeyAsSeq(2))
                + "; As num = " + kg.getRoundKey(2));

    }
}
