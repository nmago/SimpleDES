package ru.nmago.crypt.sdes;

/**
 * Created by Nur-Magomed (nmago) on 09.03.2017.
 */
public class SDESUtils {

    public static int[] getSequenceByNum(int key){
        return getSequenceByNum(key, 0);
    }

    public static int[] getSequenceByNum(int key, int length){ //return bits sequence (binary)
        int bitsCount = length == 0 ? (int) Math.ceil(Math.log(key) / Math.log(2)) : length;
        int[] keyArray = new int[bitsCount];
        int temp = key;
        for(int i = keyArray.length - 1; i >= 0; i--){
            keyArray[i] = temp % 2;
            temp /= 2;
        }
        return keyArray;
    }

    public static int getNumBySequence(int[] sequence){ //return decimal number
        int key = 0, power = 0;
        for (int i = sequence.length-1; i >= 0; i--){
            if(sequence[i] == 1)
                key+= Math.pow(2, power);
            power++;
        }
        return key;
    }

    public static int[] getPermutated(int[] sequence, byte[] permutator){ //return permutated sequence
        int[] permutatedSeq = new int[permutator.length];

        for(int i = 0; i < permutator.length; i++) {
            permutatedSeq[i] = sequence[permutator[i]-1];
        }
        return permutatedSeq;
    }

    public static int[] cyclicLS(int[] sequence, int shiftCount, int from, int to){ // cyclic left shift of sequence, transform original array
        int[] shiftedSeq = sequence.clone();
        shiftCount = shiftCount % (to - from);
        for(int c = 0; c < shiftCount; c++) {
            int firstElem = sequence[from];
            System.arraycopy(sequence, from + 1, shiftedSeq, from, to - from - 1);
            shiftedSeq[to-1] = firstElem;
        }
        return shiftedSeq;
    }

    public static int[] xorSequences(int[] a, int[] b){ //result in a
        int[] xored = new int[a.length];
        for(int i = 0; i < a.length; i++){
            xored[i] = (a[i] + b[i]) % 2;
        }
        return xored;
    }

    public static int[] concatSequences(int[] a, int[] b){
        int aLen = a.length;
        int bLen = b.length;
        int[] c= new int[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
