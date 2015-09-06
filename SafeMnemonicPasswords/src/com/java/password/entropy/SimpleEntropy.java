package com.java.password.entropy;

import java.lang.Math;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
 
/**
 * Simple calculation of classical Shannon Entropy.
 * 
 * It is very useful since it takes the frequency
 * of a character in the 1st strings, and finds
 * the frequency of this character in the 2nd string
 * @author john
 *
 */
public class SimpleEntropy {
 
	public double getShannonEntropy(String inString){
		double entr=0.0;
		entr = ShannonEntropy(freqs1stString(inString), inString.length());		
		return entr;
	}
	
  @SuppressWarnings("boxing")
  private static double ShannonEntropy(Map<Character, Integer> occ, int stringSize) {
  
    double e = 0.0;
    for (Map.Entry<Character, Integer> entry : occ.entrySet()) {
    	//char cx = entry.getKey();
      double p = (double) entry.getValue() / stringSize;
      e += p * log2(p);
    }
    return -e;
  }
  
  //***************** Freqs of INITIAL STRING **********************
  public Map<Character, Integer> freqs1stString(String s){
		
	  Map<Character, Integer> occ = new LinkedHashMap<Character, Integer>();
	
	  for (int c_ = 0; c_ < s.length(); ++c_) {
	    char cx = s.charAt(c_);
	    if (occ.containsKey(cx)) {
	      occ.put(cx, occ.get(cx) + 1);
	    } else {
	      occ.put(cx, 1);
	    }
	  }
	  return occ;
  }
 //*****************************************************************
  
  //************ Freqs of  TARGET STRING*************
  /**
   * Returns a Mapping of the characters in the 1st String, with their
   * frequency in the 2nd string.
   * 
   * @param freqs: a Mapping of char-freq for the 1st string
   * @param s: the 2nd String
   * @return a mapping of all chars in the 1st string with their respective
   * frequencies in the 2nd string. The mapping could obviously contain zeros
   */
  public Map<Character, Integer> frequencies4TargString(Map<Character, Integer> freqs, String s){
		
  	Map<Character, Integer> result = new LinkedHashMap<Character, Integer>();	
  	
	  Map<Character, Integer> occ = new LinkedHashMap<Character, Integer>();	
	  for (int c_ = 0; c_ < s.length(); ++c_) {
	    char cx = s.charAt(c_);
	    if (occ.containsKey(cx)) {
	      occ.put(cx, occ.get(cx) + 1);
	    } else {
	      occ.put(cx, 1);
	    }
	  } 
	  
	  for (Entry<Character, Integer> entry : freqs.entrySet()) {
	  	boolean b=false;
	  	for (Entry<Character, Integer> entry2 : occ.entrySet()) {
		  		if( entry.getKey()==entry2.getKey() ){
		  			result.put(entry2.getKey(),entry2.getValue());
		  			b=true;
		  		}
	  	}
	  	if(!b)
	  		result.put(entry.getKey(),0);
	  }
	  return result;
  }
	//*************************************************************** 
  
  //might not work anymore
  public void printFreqs(Map<Character, Integer> occ){
  	for (Entry<Character, Integer> entry : occ.entrySet()) {
  		char cx = entry.getKey();
  		System.out.println("cx="+cx+" entry.getValue="+entry.getValue() );
  	}
  }
  
  private static double log2(double a) {
    return Math.log(a) / Math.log(2);
  }
  
}