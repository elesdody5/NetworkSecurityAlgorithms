/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksecurityform.algorithms;

/**
 *
 * @author Elesdody
 */
public class Caser {

   public String encrypt(String input, int key) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            // to convert charchter to ascii
            int index = (int) input.charAt(i);
            // to check if the alphabet is small (a-z)
            if (index >= 97 && index <= 122) {
                    index = index-96;
                    output.append(((char) (((index +key)%26)+96)));
                

            } // if the alphabet is capital (A-Z)
            else if (index >= 65 && index <= 90) {
                index = index-64;
                    output.append(((char) (((index +key)%26)+64)));
               

            }
        }
        return output.toString();
    }

   public String decrypt(String input, int key) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            // to convert charchter to ascii
            int index = (int) input.charAt(i);
            // to check if the alphabet is small (a-z)
            if (index >= 97 && index <= 122) {
                index =index-96;
                    output.append(((char) (((index -key%26)%26)+96)));
               

            } // to check if the alphabet is capital  (A-Z)
            else if (index >= 65 && index <= 90) {
             index = index -64;
                    output.append(((char) (((index -key%26)%26)+96)));
               
            }
        }
        return output.toString();

    }

    
}
