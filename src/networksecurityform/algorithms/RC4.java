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
public class RC4 {
   // to take 10 charachter 
    private static final int INPUT_LENGTH = 10;
    int[] s = new int[INPUT_LENGTH];
    String key ;
    // key array 
    int [] k =new int [INPUT_LENGTH];
    public RC4(String key) {
        this.key =key;
        KeySchedulingAlgorithm();
        PseudoRandomGenerateAlgorithm();
        
    }

    private void KeySchedulingAlgorithm() {
        // step 1
        for (int i = 0; i < INPUT_LENGTH; i++) 
        { s[i]=i;
         k[i]= Integer.parseInt(String.valueOf(key.charAt(i%key.length())));
        
        }
        //step 2
        int j =0;
        for (int i = 0; i < INPUT_LENGTH; i++) {
            j= (j+s[i]+k[i])%INPUT_LENGTH;
            swap(i,j);
        }
            
        
    }

    private void swap(int i, int j) {
        int temp = s[i];
        s[i]=s[j];
        s[j]=temp;
    }

    private void PseudoRandomGenerateAlgorithm() {
    int i =0;
    int j =0;
    // the varible t is only pointer to refer to position in k array
   for(int t =0;t<k.length;t++){
        i = (i + 1)% INPUT_LENGTH; 
          j = (j + s[i])% INPUT_LENGTH; 
         swap(i, j); 
          k[t] = s[(s[i] + s[j])% INPUT_LENGTH];
          
   }
    }
    public String encrypt(String plainText)
    {
        StringBuilder output =new StringBuilder();
        for(int i =0;i<plainText.length();i++)
        {
        int ch = Integer.parseInt(String.valueOf(plainText.charAt(i)))^k[i];
        output.append(String.valueOf(ch));
            System.out.println(ch+""+(plainText.charAt(i)^k[i]));
        }
       return output.toString();
    }
    public String decyrpt(String cipherText)
    {
       StringBuilder output =new StringBuilder();
        for(int i =0;i<cipherText.length();i++)
        {
        int ch = Integer.parseInt(String.valueOf(cipherText.charAt(i)))^k[i];
        output.append(String.valueOf(ch));
            System.out.println(ch+""+(cipherText.charAt(i)^k[i]));
        }
       return output.toString();
    }
}
