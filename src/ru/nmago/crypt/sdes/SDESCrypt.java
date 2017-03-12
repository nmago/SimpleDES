package ru.nmago.crypt.sdes;

import java.util.Arrays;

/**
 * Created by Nur-Magomed (nmago) on 06.03.2017.
 */

public class SDESCrypt {
    private final boolean isDebug = false;
    private final byte[] IP = {2, 6, 3, 1, 4, 8, 5, 7},
                         IPinversed = {4, 1, 3, 5, 7, 2, 8, 6},
                         EP = {4, 1, 2, 3, 2, 3, 4, 1},
                         P4 = {2, 4, 3, 1};

    private final byte[][] S0 = {
            {1, 0, 3, 2},
            {3, 2, 1, 0},
            {0, 2, 1, 3},
            {3, 1, 3, 1}
    }, S1 = {
            {1, 1, 2, 3},
            {2, 0, 1, 3},
            {3, 0, 1, 0},
            {2, 1, 0, 3}
    };

    private int key;
    private int[] roundKey1Seq, roundKey2Seq;

    public SDESCrypt(int key){
        setKey(key);
    }

    public void setKey(int key){
        this.key = key;
        SDESKeyGen keygen = new SDESKeyGen(key);
        keygen.generate();
        roundKey1Seq = keygen.getRoundKeyAsSeq(1); //new int[]{1,0,1,1,1,0,1,1};
        roundKey2Seq = keygen.getRoundKeyAsSeq(2); //new int[]{1,1,0,1,1,1,0,0};
    }

    public int[] encrypt(int[] sequence){
        return cryptSequence(sequence, true);
    }

    public int[] decrypt(int[] sequence){
        return cryptSequence(sequence, false);
    }

    private int[] cryptSequence(int[] sequence, boolean encrypt){
        long oldTime = System.currentTimeMillis();
        int[] procSequence = initialPermutation(sequence, false);
        debugShow("IP", procSequence);

        procSequence = cryptionRound(procSequence, encrypt, true);
        debugShow("===== Round 1 =====", procSequence);

        procSequence = swapLeftRight(procSequence);
        debugShow("Swap", procSequence);

        debugShow("\n===== Round 2 =====", procSequence);
        procSequence = cryptionRound(procSequence, encrypt, false);

        procSequence = initialPermutation(procSequence, true);
        debugShow("\n TIME: " + (System.currentTimeMillis()-oldTime) + "ms. RESULT: ", procSequence);
        return procSequence;
    }

    private int[] swapLeftRight(int[] sequence) {
        return SDESUtils.concatSequences(getHalfPart(sequence, false), getHalfPart(sequence, true));
    }

    private int[] cryptionRound(int[] procSequence, boolean encrypt, boolean firstRound){
        int[] leftSeq, rightSeq, epSeq;
        leftSeq = getHalfPart(procSequence, true);
        rightSeq = getHalfPart(procSequence, false);

        debugShow("Left: ", leftSeq);
        debugShow("Right: ", rightSeq);

        epSeq = ePermutation(rightSeq);

        debugShow("EP", epSeq);

        int[] xorkey;
        if(encrypt){ //encrypt: first round with k1, second - with k2
            xorkey = firstRound ? roundKey1Seq : roundKey2Seq;
        }else{ //decrypt: first round with k1, second - with k2
            xorkey = firstRound ? roundKey2Seq : roundKey1Seq;
        }
        debugShow("XORKEY", xorkey);

        epSeq = SDESUtils.xorSequences(epSeq, xorkey);
        debugShow("XOR EP: ", epSeq);

        epSeq = sTransform(epSeq);
        debugShow("sTrans:", epSeq);

        epSeq = permutate4(epSeq);
        debugShow("P4", epSeq);

        leftSeq = SDESUtils.xorSequences(leftSeq, epSeq);
        debugShow("XOR left", leftSeq);
        return SDESUtils.concatSequences(leftSeq, rightSeq);
    }

    private int[] initialPermutation(int[] sequence, boolean inverse){
        return inverse ? SDESUtils.getPermutated(sequence, IPinversed) : SDESUtils.getPermutated(sequence, IP);
    }

    private int[] ePermutation(int[] sequence){
        return SDESUtils.getPermutated(sequence, EP);
    }

    private int[] permutate4(int[] sequence){
        return SDESUtils.getPermutated(sequence, P4);
    }

    private int[] getHalfPart(int[] sequence, boolean left){
        int length = sequence.length/2;
        int[] seq = new int[length];

        //if left is true we return left side, otherwise - right side
        System.arraycopy(sequence, left ? 0 : 4, seq, 0, length);
        return seq;
    }

    private int[] sTransform(int[] sequence){
        int s0Column = sequence[1]*2 + sequence[2],
            s0Row = sequence[0]*2 + sequence[3],
            s1Column = sequence[5]*2 + sequence[6],
            s1Row = sequence[4]*2 + sequence[7];

        int[] left = SDESUtils.getSequenceByNum(S0[s0Row][s0Column],2),
              right = SDESUtils.getSequenceByNum(S1[s1Row][s1Column],2);

        return SDESUtils.concatSequences(left, right);
    }

    private void debugShow(String msg, int[] arr){
        if(isDebug) System.out.println(msg+" : " + Arrays.toString(arr));
    }


}
