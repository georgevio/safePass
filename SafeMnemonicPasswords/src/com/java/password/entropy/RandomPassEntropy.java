package com.java.password.entropy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NIST 800-63-1 
 * states the the entropy of A RANDOM password is
 * E=log2(b^l) where b is the alphabet used to create
 * the password (up to the 94 ISO chars) and l the 
 * size of the password.
 * 
 * Accept a string as an input, examine it and 
 * set the alphabet it is using as follows:
 * Uppercase 26 
 * Lowercase 26
 * Digits    10
 * Symbols   32
 * ------------
 * TOTAL:    94 printable ISO characters (Latin Alphabet)
 * 
 * It will rais the alphabet size to the power of
 * the string size and convert this number to a 
 * power of two.
 * THE POWER IS THE ENTRORY IN BITS OF A RANDOM STRING
 * @author Violettas_geo
 *
 */
public class RandomPassEntropy {
	
	private boolean hasLowercase=false;
	private boolean hasUppercase=false;
	private boolean hasDigits=false;
	private boolean hasSpecSymbol=false;
	
	int stringLength=0;
	String inString;
	
	/**
	 * Empty constructor, just to create the object.
	 * Call repeatedly the @analyzeString(String inString)
	 * method
	 */
	public RandomPassEntropy(){
		
	}
	
	//Constructor. Examines a string and sets ALL variables
	public RandomPassEntropy(String inString){
		this.inString=inString;
		analyzeString(inString);
	}

	/**
	 * Analyze the incoming string and set all variables
	 * accordingly, i.e. if it has uppercase,lowercase,
	 * special characters, symbols.
	 * 
	 * @param inString
	 */
	public void analyzeString(String inString){
		
		this.inString=inString;
		stringLength=inString.length();
		
		//if there is no Lowercase, THERE IS ONLY UPPERCASE!!!
		hasUppercase = !inString.equals(inString.toLowerCase()); 
		
		//if there is no Uppercase THERE IS ONLY LOWERCASE!!!
		hasLowercase = !inString.equals(inString.toUpperCase()); 
		
		// if the inString has at least one digit, this is true
		hasDigits = inString.matches(".*\\d+.*"); 
		
		//if the String has a special symbol (e.g. #$%) this is true
		hasSpecSymbol = getSpecialCharacterCount(inString);
	}
	
	
	/**
	 * Raise the alphabet size to the power of the length of the string.
	 * and then convert this number to a (double) power of two.
	 * The power is the number of bits representing the entropy of
	 * a RANDOM string...
	 * @return
	 */
	public double calcRandomPassEntropy(){
		int ABetSize=findABet(); //find the alphabet size to use
		
		double entropy = (Math.pow(ABetSize,stringLength));//calculate the entropy
		//System.out.println("entropy="+entropy);
		
		//calculate the bits of the entropy (as a power of two)
		double powerOfTwo = Math.log(entropy)/Math.log(2);
		//System.out.println("logR="+powerOfTwo);
		
		//just round the result to 3 dec points
		return Math.floor(powerOfTwo * 100) / 100;
		
	}

	
	/**
	 * Calculates the alphabet to use for the entropy
	 * calculation.
	 * THe alphabet size is as follows:
	 * 26 if Uppercase
	 * 26 if lowercase
	 * 10 if digits(s)
	 * 32 if special char (@#$...)
	 * Total 94 ISO printable characters
	 * 
	 * @return the size of the alphabet
	 */
	private int findABet(){
		int alphabet=0;
		
		if(hasLowercase){
			alphabet+=26;
			//System.out.println("hasLowercase");
		}
		if(hasUppercase){
			alphabet+=26;
			//System.out.println("hasUppercase");
		}
		if(hasDigits){
			alphabet+=10;
			//System.out.println("hasDigits");
		}
		if (hasSpecSymbol){
			//System.out.println("hasSpecial");
			alphabet+=32;
			
		}
		return alphabet;
	}
	
	/**
	 * Find correctly if any special character(e.g. @#$%)
	 * exist in the incoming String 
	 * @param inString: the incoming String to test
	 * @return true if there is at least one special char
	 */
	private boolean getSpecialCharacterCount(String inString) {
	     if (inString == null || inString.trim().isEmpty()) {
	         //System.out.println("Incorrect format of string");
	         return false;
	     }
	     Pattern p = Pattern.compile("[^A-Za-z0-9]");
	     Matcher m = p.matcher(inString);

	     boolean found = m.find();

	     return found;
	 }

	
	//----getters-------------------------------
	
	public boolean getUpperCase(){
		return hasUppercase;
	}
	public boolean getLowerCase(){
		return hasLowercase;
	}
	public boolean getSpecSymbol(){
		return hasSpecSymbol;
	}
	public boolean getDigits(){
		return hasDigits;
	}
}
