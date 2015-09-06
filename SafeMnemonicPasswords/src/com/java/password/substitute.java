package com.java.password;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class substitute {

	//Can add any time any other "meaningful" substitutions... NO OTHER CHANGE NEEDED
	final char[] letters= {'a','e','i','o','s','t','y','g'};
	final char[] subletters={'@','3','1','0','$','+','?','9'};
	
	String inPass="tsougrias"; //just a default value... It should not be used anywhere
	String temPass=null;
	
	ArrayList<String> AllPossibleVariations = new ArrayList<String>();
	
	Random rand=new Random();
	
	public substitute(String inS){
		inPass=inS; //get a user input password
		int SubLettSiz=letters.length;
		int pasSize = inPass.length();
	    
	    for(int times=0;times<100000;times++){
		    char[] newCharString = inPass.toCharArray();
			for(int i=0;  i<SubLettSiz; i++){	
				
				int whileCounter=0;
				while(whileCounter<pasSize) {  
					
					if(newCharString[whileCounter] == letters[i]){
						//randomly decide whether to change the specific character or not
						if(rand.nextBoolean()){
							newCharString[whileCounter]=subletters[i];
						}
						String newValue=String.valueOf(newCharString);
						boolean exists=false;
						for(String variations: AllPossibleVariations ){
							if(variations.equals(newValue)){
								exists=true;
								break;
							}
						}    
						
						//find all LETTERS and POSSIBLY capitalize them
						for(int k = 0; k <pasSize; k++) {  
							if( Character.isLetter(inPass.charAt(k))  )
								if(rand.nextBoolean()) //randomly decide whether to capitalize letters
									newCharString[k]= Character.toUpperCase(inPass.charAt(k));
						}
						
						
						if(!exists) 
							AllPossibleVariations.add(String.valueOf(newCharString));	
					}
					whileCounter++;	
				}
			}
	    }
	}
	
	public ArrayList<String> getPasses(){
		return AllPossibleVariations;
	}
}
