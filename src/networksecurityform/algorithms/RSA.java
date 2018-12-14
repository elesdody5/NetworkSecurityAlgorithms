/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksecurityform.algorithms;

import java.util.Random;

/**
 *
 * @author Elesdody
 */
public class RSA {
    
    Random random = new Random();
    int p=random.nextInt(100);//17
    int q =random.nextInt(100);//11;
    
    int n ,Q_n,e,d;
    
    public RSA() {
        KeyGeneration();
    }

    private void KeyGeneration() {
        while(p==q||!isPrime(p)||!isPrime(q))
    {
        // intialize p, q
       q =random.nextInt(100);
       p=random.nextInt(100);

    }
        n = q*p;
        Q_n=(q-1)*(p-1);
        e = random.nextInt(Q_n);//7
        while(Q_n%e==0)
        e = random.nextInt(Q_n);
        for(int i =1;;i++)
        {
            d=((Q_n*i)+1)/e;
            if((d*e)%Q_n==1)
                break;
        }
                
    }
    
    /**
     * Checks to see if the requested value is prime.
     */
    private static boolean isPrime(int inputNum){
        if (inputNum <= 3 || inputNum % 2 == 0) 
            return inputNum == 2 || inputNum == 3; //this returns false if number is <=1 & true if number = 2 or 3
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0)) 
            divisor += 2; //iterates through all possible divisors
        return inputNum % divisor != 0; //returns true/false
    }
    public int encrypt(int m)
    {
       
        return (int) ((Math.pow(m, e))%n);
    }
    public int decrypt(int c)
    {
    return (int) ((Math.pow(c, d))%n);
    }
}
