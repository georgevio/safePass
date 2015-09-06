package com.java.password.entropy;

import java.nio.charset.Charset;

public class Entropy {

	byte[] string2byteArray =null;
	//CodingFrequencies entropy=null;
	
	public double binEntropy(String inString){

		String2ByteArray(inString); //convert the String to byte array
		//double binEntropy = entropy
		double binEntropy = CodingFrequencies
				.fromValues(string2byteArray, 0,string2byteArray.length)
				.binaryEntropy(); 
		//if(binEntropy >0 && binEntropy<100)   //SOS is this ok ???????????????????????????????
			return binEntropy;
		//throw new IllegalArgumentException("Binary Entropy was not found...");
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
