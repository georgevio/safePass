package com.java.password.testing;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import com.java.password.entropy.KLDSafe;

public class test20150727 {
	public static void main(String[] args) {
			
		String str_inHands_p="kostas";
		String str2TestAgainst_Q="tsougrias";
		
		KLDSafe kldSafe = new KLDSafe();
		
		List<String> targetStringsList= new ArrayList<String>();
	

		targetStringsList.add("tsougr1a$"); //L=2
		targetStringsList.add("t$0ugrias"); //L=2
		targetStringsList.add("tsoUgriaS"); //L=2
		targetStringsList.add("tsOUgrias"); //L=2
		targetStringsList.add("tsougrIA$"); //L=3
		targetStringsList.add("tsoUgraas"); //L=3
		targetStringsList.add("T$ougriAs"); //L=3
		targetStringsList.add("tsoufri@$"); //L=3
		targetStringsList.add("ts0uGr1as"); //L=3
		targetStringsList.add("T$ougria$"); //L=3
		targetStringsList.add("Ts0ugr1as"); //L=3
		targetStringsList.add("tsOUGr1as"); //L=3
		targetStringsList.add("tsouGR1As"); //L=4
		targetStringsList.add("tsoUgR1a$"); //L=4
		targetStringsList.add("TS0ugri@s"); //L=4
		targetStringsList.add("tsOuGr1@s"); //L=4
		targetStringsList.add("T$ouGR1as"); //L=5
		targetStringsList.add("Ts0Ugr1@s"); //L=5
		targetStringsList.add("T$oUgR1aS"); //L=6
		targetStringsList.add("tttssso"); //L>>6

		/*
		System.out.println("----------Strings--------------------");
		for(String s: targetStringsList){
			System.out.println(s);
		}
		System.out.println("----------Strings--------------------");
		System.out.println("strings count="+targetStringsList.size());
		System.out.println();
		*/
		
		System.out.println("-------KL Measure--------------------");
		System.out.println("1/2 KL(p, (p+q)/2  )+ KL (q, (p+q)/2 )");
		List<Double> distanceArray = new ArrayList<Double>();
		double dist=0.0; double currDist=0;
		for(String s: targetStringsList){
			currDist=kldSafe.distance(str2TestAgainst_Q, s);
			System.out.println(currDist);
			distanceArray.add(currDist);
			dist+=currDist;
		}
		dist=dist/targetStringsList.size();
		System.out.println();
		System.out.println("final avg dist="+dist);
		System.out.println(" avg= Σ (d) / distr.size ");

		System.out.println("--------KL Measure--------------------");
		System.out.println();
		
		System.out.println("-----symmetric KLD------------------");
		// Symmetric KLD: DOES NOT SEEM TO WORK VERY WELL
		// Results zero for different strings like tsougrias/tssuGRias
		// unless pi,qi <>0
		double symKLD=0.0;
		List<Double> symKLDs = new ArrayList<Double>();	
		for(String s: targetStringsList){
			symKLD=kldSafe.symmetric_KLD(str2TestAgainst_Q, s);
			System.out.println(symKLD);
			symKLDs.add(symKLD);
		}
		System.out.println("-----symmetric KLD------------------");
		System.out.println();

		double sym_med_final = kldSafe.kld_Symmetic_Med(str2TestAgainst_Q, targetStringsList);
		System.out.println("sym_med_FINAL="+sym_med_final);
		System.out.println("sym_med_final = 1/distr.size ( Σ Q(i) ) for all strings in distr");
		System.out.println();
		
		System.out.println("-----classical assymetric KLD------------------");
		List<Double> getKLDs = new ArrayList<Double>();	
		getKLDs = kldSafe.KLD_Asym_multi_target(str2TestAgainst_Q, targetStringsList);
		for(int i=0;i<targetStringsList.size();i++){
			System.out.println(Math.abs(getKLDs.get(i) )); //attention abs used for negatives
			//System.out.println(" >kld="+getKLDs.get(i)+" <"+targetStringsList.get(i));
		}
		System.out.println("-----classical assymetric KLD------------------");
		System.out.println();
		
		//αυτό με το qi μία μικρή τιμή θα δουλέψει μία χαρά
		//average KLD of a string against multiple strings
		double med = kldSafe.kld_med(str2TestAgainst_Q, targetStringsList);
		System.out.println("d( P(t), P )= KL( P(t), Pk ) where Pk=1/T Σ Pk(t)");
		System.out.println(" >kld_med="+med+" <targetStringList");
		
		System.out.println();
		System.out.println("---Jensen Shannon JSD(P/Q) = 1/2 KLD(P/M) + 1/2KLD(M/P), M=1/2 (P+Q)-----");
		List<Double> JS_KLD=kldSafe.JensenShannonKLD(str2TestAgainst_Q, targetStringsList);
		for(double d:JS_KLD)
			System.out.println(d);
		System.out.println("-----Jensen Shannon JSD(P/Q) = 1/2 KLD(P/M) + 1/2KLD(M/P), M=1/2 (P+Q) ---");

	}
}
