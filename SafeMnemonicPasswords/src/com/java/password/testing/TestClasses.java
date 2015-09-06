package com.java.password.testing;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.java.password.entropy.SimpleEntropy;
import com.java.password.randoms.RandomString;


/**
 * ONLY USED FOR TESTING
 * Creating random strings and testing them against 
 * an initial string (tsougrias)
 * 
 * @author john
 *
 */
public class TestClasses {
	public static void main(String[] args) {

		String initString=  "tsougrias"; // entropy of tsougrias = 2.94770277922009

		RandomString randStr = new RandomString(initString.length());
		
		//target string created at random
		//String targetString=randStr.nextString();
		 
		String targetString="tsougroas";
		
		Map<Character, Integer> freqsInitS = new LinkedHashMap<Character, Integer>();
		Map<Character, Integer> freqsTargetS = new LinkedHashMap<Character, Integer>();
		
		
		SimpleEntropy sEntr = new SimpleEntropy();
		
		//***************** 1st String INIT****************
		//sEntr.printFreqs(sEntr.frequencies(initString));
		
		freqsInitS=sEntr.freqs1stString(initString); //get the frequency map of 1st string (pi)
		
		double entrInit = sEntr.getShannonEntropy(initString);
		System.out.println("entrInit of "+initString+" ="+entrInit);
  	
		List<Double> freqsOnlyInit = new ArrayList<Double>();
		System.out.println("------------Initial String -------------------");
		for (Entry<Character, Integer> entry : freqsInitS.entrySet()) {
  		char cx = entry.getKey();
  		freqsOnlyInit.add((double)entry.getValue()/initString.length());
  		System.out.println("char="+cx+" freq="+entry.getValue());
  		
  	}
		System.out.println("------------Initial String END -------------------");
		System.out.println();
		//for(double i:freqsOnlyInit)
			//System.out.println("freqsOnlyInit="+i);
		
		//************************************************************************
		
		// random strings generation .......... calculate string of random string 
		double maxEntropy=0.0;
		for (int i=0;i<120;i++){
			targetString=randStr.nextString();
			double entrTarget = sEntr.getShannonEntropy(targetString);
			if(entrTarget>maxEntropy) maxEntropy=entrTarget;
			
		}
		System.out.println("max entropy of random strings "+maxEntropy);

		
		//***************** 2nd String TARGET*****************************************
		//sEntr.printFreqs(sEntr.frequencies(targetString));
  	
		//freqs of target string. might contain zeros. THIS IS WHAT KLD NEEDS for qi
		freqsTargetS=sEntr.frequencies4TargString(freqsInitS,targetString); 
		
		
		double entrTargetS = sEntr.getShannonEntropy(targetString);
		System.out.println("entropy of Target String: "+targetString+" ="+entrTargetS);
		
		List<Double> freqsOnlyTarget = new ArrayList<Double>();
		
		System.out.println();
		System.out.println("------------TARGET String  -------------------");
		for (Entry<Character, Integer> entry : freqsTargetS.entrySet()) {
  		freqsOnlyTarget.add((double)entry.getValue()/targetString.length());//correct freqs of 2nd string ONLY
  		System.out.println("char="+entry.getKey()+" f="+entry.getValue() );
  	}
		System.out.println("------------TARGET String END -------------------");
		System.out.println();

		//********************2nd String TARGET *****************************************
		
		/*
		for(double f:freqsOnlyTarget)
			System.out.println("freqsOnlyTarget="+f); 
		for(double f: freqsOnlyInit)
			System.out.println("freqsOnlyInit="+f); 
		*/
		
  	double kld=0.0;

  	double pi=0.0, qi=0.0;
  	for (int i=0;i<freqsOnlyInit.size();i++) {
   		qi=freqsOnlyInit.get(i); pi=freqsOnlyTarget.get(i);
  		System.out.println("pi= "+pi+" ,qi="+qi);
  		if(pi==0) continue; //the specific i-th term is zero
  		
  		// KL(P||Q)= sum([pi * log(pi/qi) for i in P if i in Q])   
  		kld += pi * ( Math.log ( pi / qi )  / Math.log(2) ); 
   		
  	}
  	
  	double symmetricKLD=0.0;
	 	for (int i=0;i<freqsOnlyInit.size();i++) {
   		qi=freqsOnlyInit.get(i); pi=freqsOnlyTarget.get(i);
  		System.out.println("pi= "+pi+" ,qi="+qi);
  		if(pi==0) continue; //the specific i-th term is zero
  		symmetricKLD += ( pi-qi ) * ( Math.log ( pi / qi )  / Math.log(2) ); //KLD= Ó pi * log2 ( pi/qi )
   		
  	}
  	
  	System.out.println("symmetricKLD="+symmetricKLD);
  	/*
   	for (int i=0;i<initString.length() && i<targetString.length();i++) {

   		double Pi= (double) freqsInitS.get(initString.charAt(i))/initString.length();
   		
   		double Qi= (double) freqsTargetS.get(targetString.charAt(i))/targetString.length();
   		
   		System.out.print(initString.charAt(i)+" ,"+Pi+" ");
   		System.out.println(targetString.charAt(i)+" ,"+Qi);

  			kld += - Pi * ( Math.log( Pi / Qi ) / Math.log(2) );
  	} 	
  	*/
  	
  	System.out.println("kld= "+kld);
  	
	}
}
