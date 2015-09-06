package com.java.strings;

import java.io.UnsupportedEncodingException;

/**
 * quite safe way to accept a string as an input
 * and break it into an array of bytes
 * 
 * @author john
 *
 */
public class InputString {

	boolean ThereIsString = false;
	//the incoming string
	private String InString;
	
	/**
	 * every character of the incoming string will be assigned to 
	 * this array. byte arrays are suppose to be faster
	 */
	byte [] StringInByte;
	
	/**
	 * @param InString
	 * @throws UnsupportedEncodingException
	 * basic constructor. It needs the incoming string, the one
	 * that will be analyzed to find its entropy
	 */
	InputString(String InString) throws UnsupportedEncodingException{
		if(!(InString==null)) {
			this.InString=InString;
			this.ThereIsString =true;
			StringInByte=new byte[InString.length()];
			/*
			 * break the incoming string to characthers and put them
			 * inside the byte array StringInByte
			 */
			for(int i=0;i<InString.length();i++) {
				char charAt=(char) InString.charAt(i);
				StringInByte[i]=(byte) charAt;
			}
		
		}else
			System.out.println("empty string");
	}
	
	/**
	 * printing the string after being broken into chars
	 * inside the byte array StringInByte. Just use this method
	 * if you want to see every char of the input
	 */
	public void PrintString() {
		//System.out.println(InString);
		for(int i=0;i<StringInByte.length;i++) {
			System.out.print((char)StringInByte[i]);
			if(i<StringInByte.length-1)
				System.out.print(", ");
		}
	}	
	
	public byte[] retString() {
		return StringInByte;
	}
}
