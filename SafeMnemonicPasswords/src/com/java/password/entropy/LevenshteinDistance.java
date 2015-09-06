package com.java.password.entropy;

/**
 * The Levenshtein distance is a string metric for measuring the difference between two sequences. 
 * Informally, the Levenshtein distance between two words is the minimum number of 
 * single-character edits (insertion, deletion, substitution) required to change one word into 
 * the other. The phrase edit distance is often used to refer specifically to Levenshtein distance
 * 
 * @author Sten Hjelmqvist, 26 Mar 2012
 * http://www.codeproject.com/Articles/13525/Fast-memory-efficient-Levenshtein-algorithm
 * 
 * adapted to Java, George Violettas, Dec, 2013
 *
 */
public class LevenshteinDistance{

	 // The Levenshtein distance may be calculated iteratively using the following algorithm
	public int LevenshteinDistance(String sIn, String tIn){
	    // degenerate cases
	    if (sIn.equals(tIn)) return 0;
	    if (sIn.length() == 0) return tIn.length();
	    if (tIn.length() == 0) return sIn.length();
	 
	    char s [] =sIn.toCharArray();
	    char t [] =tIn.toCharArray();
	    
	    // create two work vectors of integer distances
	    int[] v0 = new int[tIn.length() + 1];
	    int[] v1 = new int[tIn.length() + 1];
	 
	    // initialize v0 (the previous row of distances)
	    // this row is A[0][i]: edit distance for an empty s
	    // the distance is just the number of characters to delete from t
	    for (int i = 0; i < v0.length; i++)
	        v0[i] = i;
	 
	    for (int i = 0; i < sIn.length(); i++)
	    {
	        // calculate v1 (current row distances) from the previous row v0
	 
	        // first element of v1 is A[i+1][0]
	        //   edit distance is delete (i+1) chars from s to match empty t
	        v1[0] = i + 1;
	 
	        // use formula to fill in the rest of the row
	        for (int j = 0; j < tIn.length(); j++)
	        {
	            //int cost = (s[i] == t[j]) ? 0 : 1;
	      	   int cost = cost(s[i], t[j]);
	            v1[j + 1] = minimum(v1[j] + 1, v0[j + 1] + 1, v0[j] + cost);
	        }
	 
	        // copy v1 (current row) to v0 (previous row) for next iteration
	        for (int j = 0; j < v0.length; j++)
	            v0[j] = v1[j];
	    }
	 
	    return v1[tIn.length()];
	}
	
	int cost(char s, char t) {
		if (s ==t) return 0; else return 1;
	}
	
	int minimum(int a, int b, int c) {
	   int min; 
		if (a >= b) 
	       if (a >= c) {if (b >= c) min= c; else min= b; }
	       else { min= b; }
	    else if (b >= c)
	       {if (a >= c) min= c; else min= a; }
	    else {if (a >= b) min= b; else min= a; }
		return min;
	}
}