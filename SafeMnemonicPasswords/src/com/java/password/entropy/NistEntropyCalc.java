package com.java.password.entropy;

import java.util.ArrayList;

//REMEMBER: THE LENGTH OF THE STRING IS NOT CHECKED HERE !!!!!!
public class NistEntropyCalc {
	
	// remember to get the messages as well if you want
	private ArrayList<String> EntropyMessages = new ArrayList<String>();

	/*
	 * REMEMBER: NIST GIVES ANOTHER 6 bits for dictionary check...
	 * Since the words we provide DO NOT EXIST IN dictionaries, I can add it 
	 * 
	 */
	
	/**
	 * 
	 * @param inString: A string to calculate its NIST Entropy. 
	 * BE CAREFULL: The input String has to be NOT NULL and ALREADY CHECKED if it 
	 * exists in a dictionary.
	 * For more information about NIST entropy download http://goo.gl/KTTTyr
	 * @param existsInDictionary: REMEMBER: NIST GIVES ANOTHER 6 bits for dictionary check...
	 * If the words provided DO NOT EXIST IN dictionaries, it can be added by default
	 * @return a double number as the calculate entropy of a given String.
	 */
	public double NistEntropy(String inString, boolean existsInDictionary){
		
		//boolean hasLowercase = !inString.equals(inString.toLowerCase()); //if there is Lowercase it is true
		boolean hasUppercase = !inString.equals(inString.toLowerCase()); //if there is no Uppercase it is true
		boolean hasDigits = inString.matches(".*\\d+.*"); // if the inString has at least one digit, this is true
		
		int StrLength = inString.length();
		
		double NistEntropy=4; //NIST rule: First char takes 4 bits
		
		if(!existsInDictionary) {
			NistEntropy+=6; //NIST rule: if not in Dictionary add 6 bits
			EntropyMessages.add("Not in a dictionary");
		}
		else EntropyMessages.add("Found in a dictionary");
		
		if (hasUppercase && hasDigits){ 
			NistEntropy+=6;//NIST rule: if uppercase add 6 bits
			EntropyMessages.add("Bonus of 6bits. There is Uppercase & Digit(s) found");
			
		}
		else EntropyMessages.add("Not both Uppercase and digits found");
		
		if (StrLength<=8){	//NIST rule: Up to 8 chars from 2-8 (7 chars) take 2 bits)
			for(int i=2;i<=StrLength;i++){
				NistEntropy+=2;
			}
			EntropyMessages.add("Chars up to 8th, get 2bits each");	
		}
		else { // if (StrLength>8) 
			NistEntropy+=14; //NIST rule: the 1st 7 chars take 2 bits (7*2)
			if(StrLength<=20){			
				//NIST rule:after the 8th char take 1.5 bits
				for(int j=9;j<=StrLength;j++) 
					NistEntropy+=1.5; 
				EntropyMessages.add("Chars up to 8th, get 2bits each");	
				EntropyMessages.add("Chars from 8th to 20th get 1.5bits each");	
			}
			else  { // if(StrLength>20)\
				NistEntropy+=18; //NIST rule: after the 8th until 20st end on (12*1)
				for(int j=21;j<=StrLength;j++) 
					NistEntropy+=1;
				EntropyMessages.add("Chars up to 8th, get 2bits each");	
				EntropyMessages.add("Chars from 8th to 20th get 1.5bits each");	
				EntropyMessages.add("Chars over the 21st get 1bit each");								
				//NIST rule:after the 21st char NO MORE BITS
			}
		}
		return NistEntropy;
	}
	
//-------------getter----------------------------------------------------------------
	/**
	 * Messages produced to explain roughly how the NIST entropy of the given word
	 * was calculated
	 * @return All the messages describing the NIST entropy of a given word (password)
	 * Iterate the Array to get all the messages (arbitrary number)
	 */
	public ArrayList<String> getEntropyMessages(){
		return EntropyMessages;
	}

}
