package ru.nmago.crypt.sdes;

import static org.junit.Assert.*;

/**
 * Created by Nur-Magomed on 08.03.2017.
 */
public class SDESKeyGenTest {
    @org.junit.Test
    public void cyclicLS() throws Exception {
        int[] array = {0,1,2,3,4,5,6,7,8,9};
        int[][] answers = {
                //shift = 0
                //shift = 1
                {1,2,3,4,5,6,7,8,9,0}, //0..10
                {0,1,2,  4,5,6,7,8,9,3}, //3..10
                {1,0,2,3,4,5,6,7,8,9} //0..1
                //shift = 2
        };

        SDESKeyGen kg = new SDESKeyGen(5);
        //kg.cyclicLS(array, 1, 0, 10);

        //assertArrayEquals("Test 1 LS", array, answers[0]);
    }

}