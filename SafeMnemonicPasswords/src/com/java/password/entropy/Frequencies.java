package com.java.password.entropy;


/**
 * Basically there are byte arrays (for fast iterations) of
 * English letters, both capital and small and their frequencies
 * in the English language. Not really used anywhere. 
 * It is there for future reference.
 * 
 * There are also return functions for letters & freqs
 * 
 * for more info, check the following
 * http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
 * http://www.reddit.com/r/pics/comments/12zedp/frequency_of_letter_usage_on_a_computer_keyboard/
 * 
 * ONLY SOME symbols in paper A Comparison of the Frequency of Number-Punctuation
 * and Number-Letter Combinations in Literary and Technical Materials
 * @author john
 *
 */
public class Frequencies {
	    
		// space = 0.19
		
	// from http://pages.central.edu/emp/LintonT/classes/spring01/cryptography/letterfreq.html
	final private byte[] EnAlbetCap=new byte[] {'A','B','C','D','E','F','G','H','I','J','K','L','M',
													 'N','O','P','Q','R','S','T','U','V','W','X','Y','Z', ' '};
	/*
	 * Summary is NOT 100% but 99.9899999...%
	 * http://www.math.cornell.edu/~mec/2003-2004/cryptography/subs/frequencies.html
	 */
	final private double [] EnAlbetFreqs = new double[] {8.12,1.49,2.71,4.32,12.02,2.30,2.03,5.92,7.31,
			0.10,0.69,3.98,2.61,6.95,7.68,1.82,0.11,6.02,6.28,9.10,2.88,1.11,2.09,0.17,2.11,0.07, 0.19};
	
	final byte[] DecNums = new byte[] {0,1,2,3,4,5,6,7,8,9};
	
	final private byte[] EnAlbetSmall=new byte[] {'a','b','c','d','e','f','g','h','i','j','k','l','m',
													  'n','o','p','q','r','s','t','u','v','w','x','y','z'}; 

	/**
	 * print the array of English letters WITH the respective frequency
	 */
	
	public void printEnLtFreqs() {
		for (int i=0;i<EnAlbetCap.length;i++) {
			System.out.print("letter: "+(char)EnAlbetSmall[i]+".freq= ");
			System.out.print(EnAlbetFreqs[i]+" ");
		}
	}
	
	/** 
	 * @return double summary of frequencies of the English letters
	 * summarize the frequencies and return a double
	 */
	public double sumFreqs() {
		double sumFreq=0;
		for (int i=0;i<EnAlbetFreqs.length;i++)
			sumFreq+=EnAlbetFreqs[i];
		return sumFreq;
	}
	
	/**
	 * @return array of bytes of the capital English alphabet
	 */
	public byte[] getEnAlbetCap() {
		return EnAlbetCap;
	}
	
	/**
	 * @return array of bytes of small English letters alphabet
	 */
	public byte[] getEnAlbetSmall() {
		return EnAlbetSmall;
	}
	
	/**
	 * @return array of doubles of frequencies of English letters
	 */
	public double[] getEnFreqs() {
		return EnAlbetFreqs;
	}
}


