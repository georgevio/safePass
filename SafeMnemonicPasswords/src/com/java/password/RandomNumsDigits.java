package com.java.password;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import com.java.password.messages.Messages;
import com.java.password.randoms.SecureRandoms;

public class RandomNumsDigits {
	
	private final char[] numbers={'0','1','2','3','4','5','6','7','8','9'};
	private final char[] symbols={')','!','@','#','$','%','^','&','*','('};
	
	Random randomGenerator = new SecureRandom();
	Messages messages = new Messages();
	
	private ArrayList<String> part2Array = new ArrayList<String>(); //all combinations of 3 random integers
	private ArrayList<String> part2Messages = new ArrayList<String>(); //message about the combination pattern
	private ArrayList<String> part3Array = new ArrayList<String>(); // all probable combinations of symbols for the above numbers
	private ArrayList<String> part3Messages = new ArrayList<String>(); //message about the combination pattern
	
	//constructor
	public RandomNumsDigits(int times){
		if(times>0 && times <30) // just a precaution for crazy loops
			for(int i=0;i<times;i++)
				populate();
		else 	
			for(int i=0;i<13;i++) // 13 is nothing special. Just a number
				populate();

	}
	
	//part 3 of password
	private String permutate(String st){
		
		char pos1=findSub(st.charAt(0));
		char pos2=findSub(st.charAt(1));
		char pos3=findSub(st.charAt(2));
		
		StringBuilder part3 =new StringBuilder().append(pos1).append(pos2); //the 1st & 2nd digits of the 3-digit part
		if(randomGenerator.nextBoolean())
			part3=part3.append(pos3); //RANDOMLY DECIDE whether the part3 will have two or three symbols.
		return part3.toString();
	}
	
	/**
	 * Find	the substitution character for the input character.
	 * it is based on two arrays numbers and symbols.
	 * numbers[] has the letters which can be substituted
	 * symbols[] has the letters to substitute with
	 * @param c: the character to look for in numbers[]
	 * @return the substitution of the input character c from symbols[]
	 */
	private char findSub(char c){
		
		ArrayList<Character> part3Chars =new ArrayList<Character>(); //all chars of part3 in array so messages can go to Part3Messages
		
		for (int i=0;i<numbers.length;i++){
			if(c==numbers[i]){	
				part3Chars.add(symbols[i]);
				return symbols[i];
			}
		}
		System.out.println(part3Chars);
		return c;
	}
	
	
	//fill all the messages of part3 to the Part3Messages Array
	private String fillPart3Msg (char ch){
		switch(ch){
			case '!': return (messages.exclam);
				
			case '@': return(messages.at);
				
			case '#':return(messages.square);
				
			case '$':return(messages.dollar);
				
			case '%':return(messages.percent);
				
			case '^':return(messages.roof);
				
			case '&':return(messages.and);
				
			case '*':return(messages.star);
				
			case '(':return(messages.rightParenthes);
				
			case ')':return(messages.leftParenthes);
				
		}
		return null;
	}
	
	
	//easy way to add the combination and messages of part2 and part3 to the equivalent arrays
	private void add2Arrays(int s1, int s2, int s3, int s4, String message){
		 
		StringBuilder st =new StringBuilder().append(s1).append(s2).append(s3);	
		
		ArrayList<Character> part3chars = new ArrayList<Character>(); //it will be used BEFORE the 4th symbol addition
		
		 if(s4!=10){ //if it is 10, it is not needed..... (randomly decided NOT TO Have a fourth digit)
			 st=st.append(s4);
			 message=message+messages.oneExtraDigit; // if s4 was added, an appropriate message will be added as well
		 }

		StringBuilder p3msg=new StringBuilder();
		
		String part3=permutate(st.toString());
		for(int i=0;i<part3.length();i++){
			p3msg.append(fillPart3Msg(part3.charAt(i))); // fill the messages of part3 which is now formed
			if(i<part3.length()-1)
				p3msg.append(", ");
		}
			
		part2Array.add(st.toString());
		part2Messages.add(message);
		 
		part3Array.add(part3);
		 
		part3Messages.add(p3msg.toString());
	}
	
	
	/*
	 * in case the last (3rd int) is number 9, then the 4th digit has to be 1 NOT 10....
	 * Otherwise it can be the next int(n+1) for mnemonic reasons
	 * Also: randomly decide wheter to add a fourth digit. If the decision is negative, the
	 * digit to return will be 10, and hence ignored
	 */
	private int add4thChar2Part2(int s3){
		int pos1=s3;//it is usually position 3, but maybe change in the future
		
		if(randomGenerator.nextBoolean()) // randomly decide whether to add a 4th digit or not
			pos1=10;	
		else
			if(pos1==9) 
				pos1=1; //change it to anything to break the symmetry
			else 
				pos1=pos1+1; // the 4th char should be something "easy" to remember. I.e. the next one
		
		return pos1;
	}
	
	//part 2 of a password
	private void populate(){
		int s= randomGenerator.nextInt(10); //decide randomly where to start
		int s1=0, s2=0, s3=0, s4=0;
		
		StringBuilder st = new StringBuilder(); // append all the produced numbers into one String
		
		int rndDist=randomGenerator.nextInt(4); //random number between 0,1,2,3. the last for a prime number

		// let it on purpose not produce all "correct" combinations. So there is no pattern
		switch(rndDist){
		case 0:
			if(s<4){
				s1= s;  s2= s+3;  s3=s+6;	
				s4=add4thChar2Part2(s3);
				add2Arrays(s1,s2,s3,s4, messages.permPlus3);
				String str=st.append(s1).append(s2).append(s3).toString();
				permutate(str);
				break;
			}
			else {
				if(s<7){
					s1=s;  s2= s-2;  s3=s-4;
					s4=add4thChar2Part2(s3);
					add2Arrays(s1,s2,s3,s4, messages.permMinus2);
					String str=st.append(s1).append(s2).append(s3).toString();
					permutate(str);
					break;
				}
				else {
					 s1=s;s2=s-1;s3=s-3;
					 s4=add4thChar2Part2(s3);
					 add2Arrays(s1,s2,s3,s4, messages.permMinus3);
					 String str=st.append(s1).append(s2).append(s3).toString();
					 permutate(str);
					 break;
				}
			}
		case 1:
			if(s<6){
				 s1= s;  s2= s+2;  s3=s+4;	
				 s4=add4thChar2Part2(s3);
				 add2Arrays(s1,s2,s3,s4, messages.permPlus2);
				 String str=st.append(s1).append(s2).append(s3).toString();
				 permutate(str);
				 break;
			}
			else{
				 s1=s;  s2= s-2;  s3=s-4;
				 s4=add4thChar2Part2(s3);
				 add2Arrays(s1,s2,s3,s4, messages.permMinus2);
				 String str=st.append(s1).append(s2).append(s3).toString();
				 permutate(str);
				 break;
			}
		case 2:
			if(s<8){
				 s1= s;  s2= s+1;  s3=s+2;
				 s4=add4thChar2Part2(s3);
				 add2Arrays(s1,s2,s3,s4, messages.permPlus1);
				 String str=st.append(s1).append(s2).append(s3).toString();
				 permutate(str);
				 break;
			}
			else{
				 s1=s;  s2= s-1;  s3=s-2;	
				 s4=add4thChar2Part2(s3);
				 add2Arrays(s1,s2,s3,s4, messages.permMinus1);
				 String str=st.append(s1).append(s2).append(s3).toString();
				 permutate(str);
				 break;
			}
		case 3: // Adding a prime number instead of a mnemonic combination. Just a complexity add-on !!!!!!
			s= new SecureRandoms(100, 1000).getPrime();
			s1=s%10;
			s2=(s%100)/10;
			s3=(s-s%100)/100;
			s4=10;// the number was prime. so no 4th digit is added... No special thinking
			add2Arrays(s1,s2,s3,s4, messages.primeNumber);
			String str=st.append(s1).append(s2).append(s3).toString();
			permutate(str);
			break;
		}

	}
	
	//getters....................
	
	public ArrayList<String> getPart2Array(){
		return part2Array;
	}
	public ArrayList<String> getPart2Messages(){
		return part2Messages;
	}
	public ArrayList<String> getPart3Array(){
		return part3Array;
	}
	public ArrayList<String> getPart3Messages(){
		return part3Messages;
	}
}
