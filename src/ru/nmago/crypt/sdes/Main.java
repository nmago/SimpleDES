package ru.nmago.crypt.sdes;

import ru.nmago.crypt.sdes.SDESKeyGen;

public class Main {

    public static void main(String[] args) {
	    SDESKeyGen kg = new SDESKeyGen(717); //test on key=717
	    kg.generate();
    }
}
