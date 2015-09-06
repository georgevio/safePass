package com.java.password.randoms;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SecureRandoms {
	
	static Random SecRandGen = new SecureRandom();
	int prime=2;
	
	public int getPrime(){
			return prime;
	}
	
	//constructor
	public SecureRandoms(int min, int max){
		 prime= randomPrime(min, max);
	}
	
	
	public static int randomPrime(int min, int max) {
	    if (min < 0)
	        throw new IllegalArgumentException("min must be positive.");
	    if (min >= max)
	        throw new IllegalArgumentException("min must be smaller the max.");
	    if (!containsPrime(min,max))
	        throw new IllegalArgumentException("no Primes in this interval.");
	    if (SecRandGen == null) {
	    	SecRandGen = new SecureRandom();
	    }
	    int out;
	    do {
	        out = SecRandGen.nextInt(max - min) + min;
	    }while(!isPrime(out));
	    return out;
	}
    private static boolean containsPrime(int min, int max) {
	    if (isPrime(min)||isPrime(max-1)){
	        return true;
	    }
	    if (min % 2 == 0) min++;
	    else min += 2;
	   
	    while (min<max){
	        if (isPrime(min)){
	            return true;
	        }
	        min +=2;
	    }
	    return false;
    }

	static boolean isPrime(long n) {
	    if(n < 2) return false;
	    if(n == 2 || n == 3) return true;
	    if(n%2 == 0 || n%3 == 0) return false;
	    long sqrtN = (long)Math.sqrt(n)+1;
	    for(long i = 6L; i <= sqrtN; i += 6) {
	        if(n%(i-1) == 0 || n%(i+1) == 0) return false;
	    }
	    return true;
	}
	
}
