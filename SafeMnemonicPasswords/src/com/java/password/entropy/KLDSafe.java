package com.java.password.entropy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * IMPORTANT NOTICE FOR KLD:
 * ATTENTION: KLD(P||Q)= sum([pi * log(pi/qi) for i in P if i in Q]) 
 * THE TRICK IS, if i in Q !!!!!!!!!!!!!
 * 
 * All the variations of KLD (symmetric, asymmetric) 
 * and various average and median calculations based on various papers
 * check every method description for details
 * 
 * ALSO IT HAS 
 * Jensen Shannon JSD(P/Q) = 1/2 KLD(P/M) + 1/2KLD(M/P), M=1/2 (P+Q)
 * 
 * @author john fixon
 *
 */
public class KLDSafe {
  
	//global variable for initial string P freqs. Used for calculating distances (P, avg_Q)
	private List<Double> frInitString;
	
	
	/**
	 * rounding any double to two decimals. Just for printing purposes
	 * 
	 * @param d: the double to round
	 * @return: a rounded double
	 */
	double r3Dec(double d) {
    DecimalFormat threeDecimals = new DecimalFormat("#.###");
    return Double.valueOf(threeDecimals.format(d));
	}

	/**
	 * calculate the frequencies of two strings. 
	 * ATTENTION: returns even zeros as needed in KLD
	 * It correctly distinguishes between capital/small 
	 * letters. E.g. T<>t
	 * 
	 * @param initString
	 * @param targetString
	 * @return a freqObject which holds two Lists:
	 * One List<Double> and either another List<Double>
	 * or a List<List<Double>> in case you want one string
	 * against a List<String> of multiple strings
	 */
  private freqsObject calcFreqs (String initString, String targetString){ 
		
		SimpleEntropy sEntr = new SimpleEntropy();
		
		Map<Character, Integer> freqsInitS = new LinkedHashMap<Character, Integer>();
		Map<Character, Integer> freqsTargetS = new LinkedHashMap<Character, Integer>();
		

//******************** Entropies of both strings (Not really needed) **************************
		double entrTargetS = sEntr.getShannonEntropy(initString);
		//System.out.println("Init: "+initString+" .Entr="+entrTargetS);
		double entrInitS = sEntr.getShannonEntropy(targetString);
		//System.out.println("Target: "+targetString+" .Entr="+entrInitS);
//*********************************************************************************************
		
		freqsInitS=sEntr.freqs1stString(initString); //get the frequency map of the init string
		
//********************************* Freqs of Init String ***************************************
		ArrayList<Double> freqsOnlyInit = new ArrayList<Double>();
		//System.out.println("Init string:");
		for (Entry<Character, Integer> entry : freqsInitS.entrySet()) {
			freqsOnlyInit.add((double)entry.getValue()/initString.length());//freqs of 1st string 
			//System.out.println("char="+entry.getKey()+" f="+entry.getValue() );
		}
//*********************************************************************************************
		
//******************************* Freqs of Target String **************************************
		ArrayList<Double> freqsOnlyTarget = new ArrayList<Double>();
		//freqs of target string. might contain zeros. THIS IS WHAT KLD NEEDS for qi
		freqsTargetS=sEntr.frequencies4TargString(freqsInitS,targetString); //ATTENTION freqsInitS not freqsTargetS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		//System.out.println("Target String:");
		for (Entry<Character, Integer> entry : freqsTargetS.entrySet()) {
  		freqsOnlyTarget.add((double)entry.getValue()/targetString.length());//correct freqs of 2nd string ONLY
  		//System.out.println("char="+entry.getKey()+" f="+entry.getValue() );
  	}
//*********************************************************************************************

		/*
		for(double d: freqsOnlyInit)
			System.out.println("fr_init="+d);
		*/
		//for(double d: freqsOnlyInit)
			//System.out.println("freqs_init_before="+d); //unti here freqs OK...
		
		
		freqsObject fr1= new freqsObject();
		
		fr1.initFreqs(freqsOnlyInit);
		//fr1.printInitFreqs();
		fr1.targetFreqs(freqsOnlyTarget);
		//fr1.printTargetFreqs();
		
		//fr1.multitargetFreqs(freqsOnlyTarget);
		//fr1.printMultiFreqs();
		
		return fr1;

	}
	
	
	/**
	 * find the kld of a collection of strings against one initial string
	 * 
	 * @param initString: One "correct" string
	 * @param targetStrings: a list of some "not-very correct" strings
	 */
	public List<Double> KLD_Asym_multi_target(String initString, List<String> targetStrings){
		List<Double> klds = new ArrayList<Double>();
		freqsObject frIn ;
		
		for(String s:targetStrings){
			frIn=	calcFreqs (initString, s);
			//frIn.printInitFreqs();
			//frIn.printTargetFreqs();
			List<Double> frInL=frIn.getinitFreqs(); //this happens many times with no reason
			List<Double> frTargL=frIn.getTargetFreqs();
			klds.add(CalcKLD(frInL,frTargL));
		}

		return klds;
	}
	
	
	/**
	 * Get the average of the symmetric KLD of a string against an array of strings
	 * sym_med_final = 1/distr.size ( Ó Q(i) ) for all strings in distro
	 *
	 * @param initString
	 * @param targetStrings
	 * @return a double representing the average of ALL symmetric klds of each string
	 * against the initial ONE string
	 */
	public double kld_Symmetic_Med(String initString, List<String> targetStrings){
		
		List<Double> klds = new ArrayList<Double>();
		
		freqsObject fr ;
		freqsObject frMultiTarget = new freqsObject();
		List<Double> frIn = new ArrayList<Double>();
		List<Double> frTarg;

		//for the 1st pair of strings ONLY
		fr=	calcFreqs (initString, targetStrings.get(0));
		frIn=fr.getinitFreqs(); //this happens only one time as needed
		frTarg=fr.getTargetFreqs();
		frMultiTarget.initFreqs(frIn);
		frMultiTarget.multitargetFreqs(frTarg); //multiple string freqs
		frMultiTarget.targetFreqs(frTarg); //only one string freqs
		klds.add(calcSymmetricKLD(frIn,frTarg)); //only change this to calc_symmetric_KLD

		//System.out.println(klds);
		
		//for all next pairs of strings
		for(int i=1;i<targetStrings.size();i++){
			fr=	calcFreqs (initString, targetStrings.get(i));
			frMultiTarget.multitargetFreqs(fr.getTargetFreqs());
			klds.add(calcSymmetricKLD(frIn,frTarg)); //only change this to calc_symmetric_KLD
			//System.out.println(klds);
		}		
		
		//frMultiTarget.printInitFreqs();
		//frMultiTarget.printTargetFreqs();
		//frMultiTarget.printMultiFreqs();
		
		List<Double> med_sum = new ArrayList<Double>();

		int sumOfStrings=frMultiTarget.getTargetFreqsLists().size();

		//just initialize the array
		for(int p=0;p<frMultiTarget.getTargetFreqsLists().get(0).size();p++){
			med_sum.add(0.0);
		}

		for(int p=0;p<sumOfStrings;p++){	
			for(int item=0;item<frMultiTarget.getTargetFreqsLists().get(p).size();item++){
				//be careful: we want to add p(i)+p+1(i)+p+2(i).....
				med_sum.set(item, med_sum.get(item)+frMultiTarget.getTargetFreqsLists().get(p).get(item));
				//System.out.println(frMultiTarget.getTargetFreqsLists().get(p).get(item));
			}
			//System.out.println();
		}

		for(int p=0;p<med_sum.size();p++){
			//System.out.println(med_sum.get(p));
			med_sum.set(p,med_sum.get(p)/sumOfStrings);
		}
		
		//for(double d: med_sum)
			//System.out.println(d);
		
		//get the symmetric KLD of the initial string against the average of the group of strings
		double d= calcSymmetricKLD(frIn,med_sum);
		//System.out.println(d);
		
		return d;
	}

	
	/**
	 * 1/2 KL(p, (p+q)/2  )+ KL (q, (p+q)/2 )
	 * Remember: in order to avoid infinity both pi,qi 
	 * c(sh)ould be set to <>0....
	 * 
	 * @param initString
	 * @param targetString
	 * @return a double distance between initString and a targetString
	 */
	public double distance(String initString, String targetString){
		double dist=0.0;
		
		freqsObject fr =	calcFreqs (initString, targetString);
		List<Double> frIn=fr.getinitFreqs();
		
		//use the global variable for initial string freqs so it can be used for
		// calculating "average" distance d=(P_freqs, distance_avg_freqs)  
		
		
		List<Double> frTarg=fr.getTargetFreqs();
		List<Double> pANDq_div2= new ArrayList<Double>();
		for(int i=0;i<frTarg.size();i++){
			pANDq_div2.add( ( frIn.get(i)+frTarg.get(i) ) / 2 );
		}
		//System.out.println("pANDq_div2="+pANDq_div2);
		
		double kl1=0.5 * specialKLD(frIn, pANDq_div2);
		
		//System.out.println("kl1="+kl1);
		
		double kl2=specialKLD(frTarg,pANDq_div2 );
		//System.out.println("kl2="+kl2);
		
		dist=kl1+kl2; //check the paper mentioned upfront
	
		return r3Dec(dist);
		
	}

	
	
	
	/**
	 * d( P(t), P )= KL( P(t), Pk ) where Pk=1/T Ó Pk(t)
	 * Calculates the average KLD = "measure" of a string against multiple strings
	 * check the paper
	 * "probabilistic divergence for detecting interspecies combinations"
	 * 
	 * @param initString
	 * @param targetStrings: Array of Strings
	 * @return a double as the average KLD of a string against many strings
	 */
	public double kld_med(String initString, List<String> targetStrings){
		
		List<Double> klds = new ArrayList<Double>();
		
		freqsObject fr ;
		freqsObject frMultiTarget = new freqsObject();
		List<Double> frIn = new ArrayList<Double>();
		List<Double> frTarg;

		//for the 1st pair of strings ONLY
		fr=	calcFreqs (initString, targetStrings.get(0));
		frIn=fr.getinitFreqs(); //this happens only one time as needed
		frTarg=fr.getTargetFreqs();
		frMultiTarget.initFreqs(frIn);
		frMultiTarget.multitargetFreqs(frTarg); //multiple string freqs
		frMultiTarget.targetFreqs(frTarg); //only one string freqs
		klds.add(CalcKLD(frIn,frTarg));

		//for all next pairs of strings
		for(int i=1;i<targetStrings.size();i++){
			fr=	calcFreqs (initString, targetStrings.get(i));
			frMultiTarget.multitargetFreqs(fr.getTargetFreqs());
			klds.add(CalcKLD(frIn,frTarg));
		}		
		
		//frMultiTarget.printInitFreqs();
		//frMultiTarget.printTargetFreqs();
		//frMultiTarget.printMultiFreqs();
		
		List<Double> med_sum = new ArrayList<Double>();

		int sumOfStrings=frMultiTarget.getTargetFreqsLists().size();

		for(int p=0;p<frMultiTarget.getTargetFreqsLists().get(0).size();p++){
			med_sum.add(0.0);
		}

		for(int p=0;p<sumOfStrings;p++){	
			for(int item=0;item<frMultiTarget.getTargetFreqsLists().get(p).size();item++){
				//be careful: we want to add p(i)+p+1(i)+p+2(i).....
				med_sum.set(item, med_sum.get(item)+frMultiTarget.getTargetFreqsLists().get(p).get(item));
				//System.out.println(frMultiTarget.getTargetFreqsLists().get(p).get(item));
			}
			//System.out.println();
		}

		for(int p=0;p<med_sum.size();p++){
			med_sum.set(p,med_sum.get(p)/sumOfStrings);
		}
		
		//Attention: I CHANCED IT and it produces positive numbers
		//double d= CalcKLD(frIn,med_sum);
		
		double measure= CalcKLD(med_sum,frIn);
		//System.out.println(d);
		
		return r3Dec(measure);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Jensen Shannon JSD(P/Q) = 1/2 KLD(P/M) + 1/2KLD(M/P), M=1/2 (P+Q)
	 * 
	 * @param initString: One initial string P
	 * @param targetStrings: A distro of strings Qn
	 * @return an array of doubles the JSD (P/Qn)
	 */
	public List<Double> JensenShannonKLD(String initString, List<String> targetStrings ){
		
		List<Double> JS_KLDs = new ArrayList<Double>(); // array to return
		
		freqsObject fr ;
		freqsObject frMultiTarget = new freqsObject();
		List<Double> frIn = new ArrayList<Double>();
		List<Double> frTarg;

		//for the 1st pair of strings ONLY
		fr=	calcFreqs (initString, targetStrings.get(0));
		frIn=fr.getinitFreqs(); //this happens only one time as needed
		frTarg=fr.getTargetFreqs();
		frMultiTarget.initFreqs(frIn);
		frMultiTarget.multitargetFreqs(frTarg); //multiple string freqs
		frMultiTarget.targetFreqs(frTarg); //only one string freqs

		//for all next pairs of strings
		for(int i=1;i<targetStrings.size();i++){
			fr=	calcFreqs (initString, targetStrings.get(i));
			frMultiTarget.multitargetFreqs(fr.getTargetFreqs());
		}		
		
		// HERE we have freqs of P and all freqs of a distro of strings
		
		//all the strings in the targetStrings List
		int sumOfStrings=frMultiTarget.getTargetFreqsLists().size();
		
		List<Double> frqsIn=frMultiTarget.getinitFreqs();
		List<Double> frqsTarg = new ArrayList<Double>(); 
		List<Double> m = new ArrayList<Double>(); //M=1/2 (P+Q)
		double sumDiv2=0.0;
		
		for(int p=0;p<sumOfStrings;p++){
			//get the freqs of each target string
			frqsTarg=frMultiTarget.getListsFromTargetList(p);
			for(int i=0;i<frqsIn.size();i++){	
				
				// calculate M=1/2 (P+Q) for every Pi,Qi for strings P, Qn n={1..size of distro}
				sumDiv2=(frqsIn.get(i)+frqsTarg.get(i))/2;
				// add the M to the List
				m.add(sumDiv2);
			}
			//calculate the Jensen-SHannon JSD of p against Qn, n={1...distro_size}
			double jsKLD= (CalcKLD(frqsIn, m))/2+(CalcKLD(m,frqsIn))/2;
			//System.out.println("m="+m);
			JS_KLDs.add(r3Dec(jsKLD)); //r3Dec = 3 decimals
			m.clear(); // get ready for the next string

		}

		return JS_KLDs;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * classical "official" version of KLD
	 * Kullbach-Leibner Divergence
	 * 
	 * @param initString
	 * @param targetString
	 * @return the non-symmetric KLD of those two strings
	 */
	public double KLD_ASymmetric(String initString, String targetString){ 
		double kld=0.0;
		freqsObject fr =	calcFreqs (initString, targetString);
		List<Double> frIn=fr.getinitFreqs();
		List<Double> frTarg=fr.getTargetFreqs();
		//for(double d:frIn)
			//System.out.println(d);
		//System.out.println();
		//for(double d:frTarg)
			//System.out.println(d);
		
		kld= CalcKLD(frIn,frTarg);
		return kld;
	}
	
	/**
	 * symmetrical version of KLD
	 * Kullbach-Leibner Divergence
	 * 
	 * @param initString
	 * @param targetString
	 * @return the symmetric KLD of those two strings.
	 * Remember: KLD(s,t)=KLD(t,s)
	 */
	public double symmetric_KLD(String initString, String targetString){ 
		double sym_kld=0.0;
		freqsObject fr =	calcFreqs (initString, targetString);
		List<Double> frIn=fr.getinitFreqs();
		List<Double> frTarg=fr.getTargetFreqs();
		/*
		for(double d:frIn)
			System.out.println("frIn="+d);
		System.out.println();
		for(double f:frTarg)
			System.out.println("frTarg="+f);
		System.out.println("--------------------------------------");
		*/
		sym_kld= calcSymmetricKLD(frIn,frTarg);
		
		//System.out.println("sym_kld="+sym_kld);
		return r3Dec(sym_kld);
	}

  
  /**
   * Asymmetric KLD BUT with BOTH pi,qi <> 0. 
   * It can be used for distance measuring. Many researchers do this
   * 
   * @param freqsOnlyInit
   * @param freqsOnlyTarget
   * @return a double KLD
   */
	private double specialKLD(
			List<Double> freqsOnlyInit, List<Double> freqsOnlyTarget){
		
		double kld=0.0, pi=0.0, qi=0.0;
		for (int i=0;i<freqsOnlyInit.size();i++) {
	 		qi=freqsOnlyInit.get(i); pi=freqsOnlyTarget.get(i);
			//System.out.println("pi= "+pi+" ,qi="+qi);
	 		
			if(pi==0) pi=0.03; //no infinitysss
			
			if(qi==0) qi=0.03; //only for special cases like distance............
			
			// KL(P||Q)= sum([pi * log(pi/qi) for i in P if i in Q])  
			kld += pi * ( Math.log ( pi / qi )  / Math.log(2) ); 
		}
		//System.out.println("KLD="+KLD);
		return r3Dec(kld);
	}
	

	/**
	 * Kullback-Leibler Divergence ASYMMETRIC
	 * Asymmetric original Kullback-Leibler Divengence
   * KLD = Ó p(x) * log2 p(x) /q (x)
	 * 
	 *  *********** ATTENTION ****************************
	 *  qi is THE IDEAL - INITIAL DISTRIBUTION, 
	 *  pi is THE ACTUAL (THE ONE WE HAVE AT HANDS)
	 *  
	 *  There is a problem here:
	 *  KLD is defined for every i in p iff i in q
	 *  
	 *  
	 *  if one wants to find the distance from target to init
	 *  she has to call AGAIN the kld by reversing initString & targetString
	 *  
	 * @param freqsOnlyInit: The not so correct distribution qi
	 * @param freqsOnlyTarget: The pi distribution
	 * @return the Non-symmetric Kullbach-Leibner Divergence
	 */
	private double CalcKLD(
			List<Double> freqsOnlyInit, List<Double> freqsOnlyTarget){
		
		double KLD=0.0, pi=0.0, qi=0.0;
		for (int i=0;i<freqsOnlyInit.size();i++) {
	 		qi=freqsOnlyInit.get(i); pi=freqsOnlyTarget.get(i);
			//System.out.println("pi= "+pi+" ,qi="+qi);
	 		
			if(pi==0) pi=0.03; //testing. it brings negative results...
			//continue; //the specific i-th term is zero.
			//if(qi==0) qi=0.03; //only for special cases like distance............
			
			// KL(P||Q)= sum([pi * log(pi/qi) for i in P if i in Q])  
			KLD += pi * ( Math.log ( pi / qi )  / Math.log(2) ); 
		}
		//System.out.println("KLD="+KLD);
		return r3Dec(KLD);
	}

	/**
	 * Symmetric KLD: SKLD(s,p)=SKLD(p,s)
	 * Symmetric  Kullback-Leibler DISTANCE
   * KLD = Ó p(x) * log2 p(x) /q (x)
	 * 
	 * if not set pi,qi <> 0, DOES NOT SEEM TO WORK VERY WELL
	 * Results zero for different strings like tsougrias/tssuGRias
	 * 
	 * KL(P||Q)= sum([ (pi-qi) * log(pi/qi) for i in P if i in Q])
	 * @param freqsOnlyInit
	 * @param freqsOnlyTarget
	 * @return a symmetric kld. Reversing the input strings should result the same
	 */
	private double calcSymmetricKLD(
			List<Double> freqsOnlyInit, List<Double> freqsOnlyTarget){
		
		double sym_KLD=0.0, pi=0.0, qi=0.0;
		for (int i=0;i<freqsOnlyInit.size();i++) {
	 		qi=freqsOnlyInit.get(i); pi=freqsOnlyTarget.get(i);
			//System.out.println("pi= "+pi+" ,qi="+qi);
	 		//if(qi==0)  qi=0.05; //System.out.println("qi=0"); //only pi is suppose to be zero
	 		
	 		//more pragmatic solutions. Giving to pi a small value, brings NO ZEROS
			if(pi==0) pi=0.03;; //the specific i-th term is zero, because log(0/x)=log0=0
			
			// KL(P||Q)= sum([pi * log(pi/qi) for i in P if i in Q])   
			//System.out.println("pi-qi="+(pi-qi));
			//System.out.println("Math.log ( pi / qi ) ="+Math.log ( pi / qi ) );
			sym_KLD += (pi-qi) * ( Math.log ( pi / qi )  / Math.log(2) ); 
		}
		//System.out.println("sym_KLD="+sym_KLD);
		return r3Dec(sym_KLD);
	}
	
}
