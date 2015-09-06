package com.java.password.messages;
public class Messages {

	
	//be careful 
	
	
	
	//they might be  wrong !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	final String m0="Congratulations: THIS WORD BELONGS TO THE 500 WORST PASSWORDS. TRY AGAIN...";
			
	final String m1="This word is an adequate word to use as a BASIS for passwords";
	final String m2="This word is a VERY BAD WORD TO USE FOR PASSWORDS. DON'T YOU DARE TO USE IT";
	
	final String m3="This word is a bad word to use as a BASIS for passwords. AVOID IT...";
	final String m4="This is a good word to use as a BASIS for passwords";
	
	final String m60="This word is extremely simplistic. Use something more complex";
	final String m70="This word is very short. Please use a longer one (More than 6 characters)";
	final String m71="This word is very long. Please use a shorter one (Less than 15 characters)";
	
	
	final String errorMess="Something went wrong. Word was NOT evaluated...";
	
	public final static String permPlus1="Numbers are +1 (n, n+1, n+2)";
	public final static String permMinus1="Numbers are  -1 (n, n-1, n-2)";
	public final static String permPlus2="Numbers are +2 (n, n+2, n+4)";
	public final static String permMinus2="Numbers are -2 (n, n-2, n-4)";
	public final static String permMinus3="Numbers are -3 (n, n-1, n-3)";
	public final static String permPlus3="Numbers are +3 (n, n+3, n+6)";
	
	public final static String primeNumber="A random Prime number ";
	public final static String oneExtraDigit=", with an extra digit at the end";
	
	public final static String exclam="!=Shift+1";
	public final static String at="@=Shift+2";
	public final static String square="#=Shift+3";
	public final static String dollar="$=Shift+4";
	public final static String percent="%=Shift+5";
	public final static String roof="^=Shift+6";
	public final static String and="&=Shift+7";
	public final static String star="*=Shift+8";
	public final static String leftParenthes="(=Shift+9";
	public final static String rightParenthes=")=Shift+0";
	
	
	public String numberOrDigitOut(String inS){
		return inS;
	}
	
	public String messageOut(int k){
			switch (k){
				case 0:  return m0;  
				case 1:  return m1;  
				case 2:  return m2;  
				case 3:  return m3;  
				case 4:  return m4;  
				case 60: return m60; 
				case 70: return m70;
				default: return errorMess;
			}		
	}

}
