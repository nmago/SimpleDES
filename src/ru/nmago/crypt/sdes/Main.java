package ru.nmago.crypt.sdes;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
    	SDESCrypt sdes = new SDESCrypt(717);
	    int[] a = {1,0,0,1,1,1,0,0};
		System.out.println("Given block a = " + Arrays.toString(a) + ", as dec number: " + SDESUtils.getNumBySequence(a));
	    a = sdes.encrypt(a);
	    System.out.println("Enrypted block a = " + Arrays.toString(a) + ", dec as number: " + SDESUtils.getNumBySequence(a));
	    a = sdes.decrypt(a);
        System.out.println("Decrypted block a = " + Arrays.toString(a) + ", dec as number: " + SDESUtils.getNumBySequence(a));

    }
}
