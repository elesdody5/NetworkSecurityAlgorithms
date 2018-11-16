/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksecurityform.algorithms;

import java.util.ArrayList;

/**
 *
 * @author Elesdody
 */
public class Playfair {

    char[][] matrix = new char[5][5];
    ArrayList<String> charPair;
    ArrayList<String> charPosition;

    public Playfair(String input, String key) {
        createMatrix(input, key);
    }

    private void createMatrix(String input, String key) {
        StringBuilder MatrixChar = new StringBuilder(key);
        char alphabet = (char) 97;
        for (int i = 0; i < 26; i++) {

            if (alphabet != 'j')
                // to check every alphabet in key
            {
                if (!MatrixChar.toString().contains(String.valueOf(alphabet))) {
                    MatrixChar.append(String.valueOf(alphabet));
                }
            }
            ++alphabet;

        }
        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 5; j++) {
                matrix[i][j] = MatrixChar.charAt(i * 5 + j);

            }
        }
        // divide the input to array contain pair of charachter in each cell
        //(Arraylist because i don't know how many pairs (can be additional x )
        charPair = new ArrayList<>();
        StringBuilder inputString = new StringBuilder(input);
        // array to hold every input charachter position in matrix 
        charPosition = new ArrayList<>();
        int currentPosition = 0;

        for (int i = 0; i < inputString.length(); i++) {
            if (i % 2 != 0) {
                if (inputString.charAt(i - 1) != inputString.charAt(i)) {///balxlon
                    charPair.add(inputString.substring(i - 1, i + 1));
                } else {
                    inputString.insert(i, "x");
                    charPair.add(inputString.substring(i - 1, i + 1));
                    System.out.println(inputString.length());
                }
            }
            // to get character row and column from the matrix
            char currentcharacter = inputString.charAt(i);
            String charMatrixPos = getCharPosition(currentcharacter, MatrixChar);

            charPosition.add(charMatrixPos);

        }

        if (inputString.length() % 2 != 0) {
            // if input.lenght is odd 
            // add x at the end and get it position in the matrix 
            inputString.append("x");
            charPair.add(inputString.substring(inputString.length() - 2, inputString.length()));
            String Xposition = getCharPosition('x', MatrixChar);
            charPosition.add(Xposition);

        }
    }

    private String getCharPosition(char character, StringBuilder MatrixChar) {

        String currentcharacter = String.valueOf(character);
        int row = MatrixChar.indexOf(currentcharacter) / 5;
        int column = MatrixChar.indexOf(currentcharacter) % 5;
        return row + "" + column;

    }

    public String encrypt() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < charPosition.size(); i += 2) {
            // to get position for each char in matrix  
            int fristCharRow = Integer.parseInt(String.valueOf(charPosition.get(i).charAt(0)));
            int fristCharColumn = Integer.parseInt(String.valueOf(charPosition.get(i).charAt(1)));
            int secondCharRow = Integer.parseInt(String.valueOf(charPosition.get(i + 1).charAt(0)));
            int secondCharColumn = Integer.parseInt(String.valueOf(charPosition.get(i + 1).charAt(1)));

            char newFristchar;
            char newSecondchar;

            if (fristCharRow == secondCharRow) {
                newFristchar = matrix[fristCharRow][(fristCharColumn + 1) % 5];
                newSecondchar = matrix[secondCharRow][(secondCharColumn + 1) % 5];
                output.append(newFristchar);
                output.append(newSecondchar);
            } else if (fristCharColumn == secondCharColumn) {

                newFristchar = matrix[(fristCharRow + 1) % 5][fristCharColumn];
                newSecondchar = matrix[(secondCharRow + 1) % 5][secondCharColumn];
                output.append(newFristchar);
                output.append(newSecondchar);
            } else {
                newFristchar = matrix[fristCharRow][secondCharColumn];
                newSecondchar = matrix[secondCharRow][fristCharColumn];
                output.append(newFristchar);
                output.append(newSecondchar);
            }
        }
        return output.toString();

    }

    public String decrypt() {

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < charPosition.size(); i += 2) {
            // to get position for each char in matrix  
            int fristCharRow = Integer.parseInt(String.valueOf(charPosition.get(i).charAt(0)));
            int fristCharColumn = Integer.parseInt(String.valueOf(charPosition.get(i).charAt(1)));
            int secondCharRow = Integer.parseInt(String.valueOf(charPosition.get(i + 1).charAt(0)));
            int secondCharColumn = Integer.parseInt(String.valueOf(charPosition.get(i + 1).charAt(1)));

            char newFristchar;
            char newSecondchar;

            if (fristCharRow == secondCharRow) {
                // to avoid underflow (minuse)
                if (fristCharColumn != 0) {
                    newFristchar = matrix[fristCharRow][(fristCharColumn - 1)];
                } else {
                    newFristchar = matrix[fristCharRow][(4)];
                }
                if (secondCharColumn != 0) {
                    newSecondchar = matrix[secondCharRow][(secondCharColumn - 1)];
                } else {
                    newSecondchar = matrix[secondCharRow][(4)];
                }
                output.append(newFristchar);
                output.append(newSecondchar);
            } else if (fristCharColumn == secondCharColumn) {
                if (fristCharRow != 0) {
                    newFristchar = matrix[(fristCharRow - 1)][fristCharColumn];
                } else {
                    newFristchar = matrix[(4)][fristCharColumn];
                }

                if (secondCharRow != 0) {
                    newSecondchar = matrix[(secondCharRow - 1)][secondCharColumn];
                } else {
                    newSecondchar = matrix[(4)][secondCharColumn];
                }
                output.append(newFristchar);
                output.append(newSecondchar);
            } else {
                newFristchar = matrix[fristCharRow][secondCharColumn];
                newSecondchar = matrix[secondCharRow][fristCharColumn];
                output.append(newFristchar);
                output.append(newSecondchar);
            }
        }
        // to remove addational x after decryption
        return removeX(output);

    }

    private String removeX(StringBuilder output) {
        int xposition = 0;
        int length = output.length();

        
            for (int i = 0; i < output.length(); i++) {
                if (output.toString().contains("x")) {
                xposition = output.indexOf("x", xposition);
                if  (output.length() - 1 == xposition) {
                    if(length % 2 == 0)
                    output.deleteCharAt(xposition);
                } else if (output.charAt(xposition - 1) == output.charAt(xposition + 1)) {
                    output.deleteCharAt(xposition);
                }

            }
                else
                    break;
        }
        return output.toString();
    }

}
