/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksecurityform.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Elesdody
 */
public class DES {

    private int[] initialPermutation = new int[64];
    private int[] finalPermutaion = new int[64];
    private int[] Expansion = new int[48];
    int[] compressionPermutation = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    private int[] numberShift = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    private int[] permuationDbox = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};
    private static final byte[][] S = {{
        14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
        0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
        4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
        15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13
    }, {
        15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
        3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
        0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
        13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9
    }, {
        10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
        13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
        13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
        1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12
    }, {
        7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
        13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
        10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
        3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14
    }, {
        2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
        14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
        4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
        11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3
    }, {
        12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
        10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
        9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
        4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13
    }, {
        4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
        13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
        1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
        6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12
    }, {
        13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
        1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
        7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
        2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
    }};

    public DES() {
        createMatrises();

    }

    // method to intialize arraies used in algorithm 
    // initial permutaion 
    // final permutaion 
    // expansion 
    // s-Box
    private void createMatrises() {
        // initial permution 
        int frist_element = 58;
        int pcounter;
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0 && i != 0) {
                frist_element += 58;
            }
            if (frist_element == 66) {
                frist_element = 57;
            }

            initialPermutation[i] = frist_element;
            if (frist_element - 8 > 0) {
                frist_element -= 8;
            }
        }
        // final permution array
        int count = 8;
        int mod = 0;
        for (int i = 1; i < 64; i += 2) {
            if ((i + mod) % 9 == 0) {

                count -= 33;
                mod++;
            }
            finalPermutaion[i] = count;
            count += 8;
        }
        count = 40;
        for (int i = 0; i < 64; i += 2) {
            if (i % 8 == 0 && i != 0) {
                count -= 33;
            }
            finalPermutaion[i] = count;
            count += 8;
        }

// expantion array
        int ecounter = 1;
        for (int i = 1; i < 48; i++) {
            if (i % 6 == 0) {
                ecounter -= 2;
            }

            Expansion[i] = ecounter;
            ecounter++;
        }
        Expansion[0] = 32;
        Expansion[47] = 1;
    }

    private ArrayList<String> generateKey(String key) {
        // transform key to binary
        byte[] bytes = key.getBytes();

        StringBuilder builder = new StringBuilder();
        // parity drop 
        for (int i = 0; i < 8; i++) {
            String ch = Integer.toBinaryString(bytes[i]);
            ch = ch.substring(0, ch.length());
            builder.append(ch);
        }
       // System.out.println("Key after parity drop:\n" + builder);

        ArrayList<String> newKey = new ArrayList<>();

        String preLeft = builder.substring(0, builder.length() / 2);
        String preRight = builder.substring(builder.length() / 2);
        for (int i = 0; i < 16; i++) {
            String round_key = shift(0, preLeft, preRight);
            newKey.add(permutaion(round_key, compressionPermutation));
        }

        return newKey;
    }

    public String  encrypt(String input, String key) {

        ArrayList<String> keys = generateKey(key);
        // convert every charachter to binary
        byte[] bytes = input.getBytes();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String s1 = String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0');
            builder.append(s1);
        }
        // initial permutation
        input = builder.toString();
        input = permutaion(input, initialPermutation);

         for(int i=0;i<16;i++)
          input = AlgroithmFunction(input, keys.get(i));
          input = Swap(input);
          return permutaion(input, finalPermutaion);
        

    }
 

    private String permutaion(String input, int[] permutiaon) {

        StringBuilder builder = new StringBuilder();
        int lenght = permutiaon.length;
        for (int i = 0; i < lenght; i++) {
            // get new position from initial permutation array
            int postion = permutiaon[i];
            char ch = input.charAt(postion - 1);
            builder.append(ch + "");

        }
        return builder.toString();
    }

    private String shift(int i, String preLeft, String preRight) {

        int rounds = numberShift[i];
        int left = Integer.parseInt(preLeft, 2);
        String lastbits = preLeft.substring(rounds, preLeft.length());
        left = Integer.rotateLeft(left, rounds);
        preLeft = Integer.toBinaryString(left);
        preLeft = lastbits + preLeft;
        ///////////////////////
        int right = Integer.parseInt(preRight, 2);
        lastbits = preRight.substring( rounds, preRight.length());
        right = Integer.rotateLeft(right, rounds);
        preRight = Integer.toBinaryString(right);
        preRight = lastbits + preRight;
        ///////////////////////
        String key = preLeft + preRight;

        return key;
    }

    private String AlgroithmFunction(String input, String key) {
        // divide input to left and right 
        
        String left = input.substring(0, input.length() / 2);
        String right = input.substring(input.length() / 2);
        String output = right;
        // expantion 
        right = permutaion(right, Expansion);

        // Xor function 
      String XorString = Xor(right,key);
        // Sbox permutation 
        StringBuilder Sbuilder = new StringBuilder();
        for(int i=0;i<48;i+=6)
        {
        int row = Integer.parseInt(XorString.charAt(i)+""+XorString.charAt(i+5),2);
        int column = Integer.parseInt(XorString.substring(i+1,i+5),2);
       
        int postion = row*16+column;
        int newchr=   S[i/6][postion];
            // to avoid under flow
            switch (Integer.toBinaryString(newchr).length()) {
                case 2:
                    Sbuilder.append("00");
                    break;
                case 3:
                    Sbuilder.append("0");
                    break;
                case 1:
                    Sbuilder.append("000");
                    break;
                default:
                    break;
            }
       
        Sbuilder.append(Integer.toBinaryString(newchr));
        
        }
        right= permutaion(Sbuilder.toString(), permuationDbox);
        
        right = Xor(left,right);
        output=output+right;
        
        return output;
    }
    private String Xor(String frist,String right)
    {
      StringBuilder Xorbuilder = new StringBuilder();
        for (int i = 0; i < frist.length(); i++) {
            int ch = Integer.parseInt(String.valueOf( frist.charAt(i))) ^Integer.parseInt(String.valueOf(right.charAt(i)));
            Xorbuilder.append(ch + "");
        }
        return Xorbuilder.toString();
    }

    private String Swap(String input) {
        String left  = input.substring(0,input.length()/2);
        String right = input.substring(input.length()/2);
        return right+left;
    }

     public String dycrypt(String input,String key ){
          ArrayList<String> keys = generateKey(key);
        // convert every charachter to binary
        byte[] bytes = input.getBytes();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String s1 = String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0');
            builder.append(s1);
        }
        // initial permutation
        input = builder.toString();
        input = permutaion(input, initialPermutation);

         for(int i=15;i>0;i--)
        input = AlgroithmFunction(input, keys.get(i));
         
         // final permutaion 
          input = Swap(input);
          return permutaion(input, finalPermutaion);
    
    }
}
