package com.java.password.entropy;

import java.util.ArrayList;
import java.util.List;

/**
 * very helpful for creating and manipulating 
 * frequencies of characters of multiple strings
 * It is easily used to return all the frequencies
 * needed for further evaluation in KL distance equations
 * 
 * there are also some printing functions for debugging purposes
 * @author john
 *
 */
public class freqsObject {
	
  private List<Double> initFreqs;
  private List<Double> targetFreqs;
  private List<List<Double>> multiTargetFreqs;

  public freqsObject(ArrayList<Double> fInit, ArrayList<Double> fTargets){
  	this.initFreqs=fInit;
  	this.targetFreqs=fTargets;
  	multiTargetFreqs = new ArrayList<List<Double>>(); //is it really needed here?
  }
  
	public freqsObject() {
  	multiTargetFreqs = new ArrayList<List<Double>>();// pointer problems. needs to declare it
  }

	public void multitargetFreqs(List<Double> freqsOnlyTarget) {
		this.multiTargetFreqs.add(freqsOnlyTarget);
  }
	
	public void targetFreqs(List<Double> freqsOnlyTarget) {
		this.targetFreqs=freqsOnlyTarget;
  }
	
	public void initFreqs(List<Double> freqsOnlyInit) {
		if(initFreqs == null)
			this.initFreqs=freqsOnlyInit;
  }
  
	//getters----------------------------------------------
	
  public List<Double> getinitFreqs(){
  	return initFreqs;
  }

  public List<List<Double>> getTargetFreqsLists(){
  	return multiTargetFreqs;
  }
  
  public List<Double> getTargetFreqs(){
  	return targetFreqs;
  }
  
  public List<Double> getListsFromTargetList(int p){
  	List<Double> retL=null;
  	if(p<multiTargetFreqs.size()){
	  		retL=multiTargetFreqs.get(p);
	  		return retL;
  	}
  	return null;
  }
  
  public double getItemFromTargetLists(int p,int item){
  	double retF=-999.0;
  	//if(p<multiTargetFreqs.size() && item< multiTargetFreqs.get(p).size()){
  		retF=multiTargetFreqs.get(p).get(item);
  	//}
		return retF;
  }
  
  
  //printing utilities-----------------------------------
  
  public void printInitFreqs(){
  	int count=0;
  	if(initFreqs!=null){
	  	for(double d: initFreqs){
	  		System.out.println("f_init="+d);
	  		count++;
	  	}
  		System.out.println("-------counted freqs init="+count);
  	}
  	else System.out.println("initfreqs is empty...");
  }
  
  public void printTargetFreqs(){
  	if(targetFreqs!=null)
	  	for(double d: targetFreqs)
	  		System.out.println("f_targ="+d);
  	else System.out.println("targetFreqs is empty...");
  }
  
  public void printMultiFreqs(){
  	if(multiTargetFreqs!=null){
  		int counter=0;
	  	for(List<Double> l:multiTargetFreqs){
	  		counter++;
		  	for(double d: l)
		  		System.out.println("f_multi="+d);
  			System.out.println("next multi-------------");
	  	}
	  	System.out.println("******** counted objects="+counter);
  	}
  	else System.out.println("multiTargetFreqs is empty...");
  }
  
}
	
