package com.java.strings;

import java.nio.charset.Charset;

public class String2Byte {
	
	byte[] string2byteArray =null;
	
	public String2Byte(String inString){
		String2ByteArray(inString);
	}
	/**
	 * 
	 * @param inString the incoming String to convert to a byte array
	 * set the string2byteArray as byte array of the converted incoming String
	 */
	private void String2ByteArray(String inString){
		//convert the input String to an array of bytes
		string2byteArray = inString.getBytes(Charset.forName("UTF-8"));
	}
	
	/**
		 * @return an array of bytes of the corresponding input String
	 */
	public byte[] string2byteArray(){
		if(string2byteArray != null)
			return string2byteArray;
		throw new IllegalArgumentException("byte array is empty...");
	}

}
