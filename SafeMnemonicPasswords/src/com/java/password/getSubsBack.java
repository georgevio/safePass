package com.java.password;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.java.password.entropy.LevenshteinDistance;

/**
 * Create variations of an initial STring with random
 * substitutions.
 * REMEMBER: Only those with LEvenstein Distance>2 are kept.
 * And from this group an oracle randomly decides to discard 
 * some, so no statistics or similarity attacks are possible
 * 
 * @author Violettas_geo
 *
 */
public class getSubsBack {
	
	public ArrayList<String> getSomePasses(String inS){

		substitute sub = new substitute(inS);
		
		ArrayList<String> variations=new ArrayList();
		variations=sub.getPasses();
		
		LevenshteinDistance LevenD = new LevenshteinDistance();	
		ArrayList<String> returnSome=new ArrayList();
		Random rand= new Random();

		//run through ALL the created password
		for(int r=0;r<variations.size();r++){
			//keep only those variations with Levenstein Distance >=3
			if(LevenD.LevenshteinDistance(inS, variations.get(r))<3){
				variations.remove(r);
			}	
		}
		//Randomly decide whether to keep the particular proposal or not
		for(int k=0;k<20;k++)
			returnSome.add(variations.get(rand.nextInt((variations.size()))));

		return returnSome;
	}

} 
