package com.java.password.db.dBSearch;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.java.password.db.dbconn.MySQLConn;

/**
 * Search in all available Dbs. This search usually takes some time(0.5-1min)
 * Return an integer showing "how bad" the incoming password is:
 * 0: A good password to start with. It does not exist in any dictionary
 * 1: A semi good password to start with. It exists in an extensively big db
 * 2: A bad password to start with. It exists in at least one small-size dictionary
 *  3: A very bad password. This word exists in a list such as "the cities of USA"
 *  4: An extremely BAD PASSWORD. It belongs to a list with the 500 worst passwords... 

 * you have the options to search in all dBs together, BUT
 * This will take some time (1/2-1 min)
 * Or you can only search into the small dictionaries.
 * REMEMBER: Searching only in short dictionaries, 
 * DOESNOT give the NIST bonus of 6bits...
 * @author Violettas_geo
 *
 */
public class LookIn2db {

	String worstPassTable1="500_worst_passwords";
	//String worstPassTable2="twitter_banned"; //not needed. It is all inside the 55_worst_passwords
	String table2="cain";
	String table3="us_cities";
	//String table4="facebook_firstnames"; 
	String table5="porno";
	String table6="honeynet_withcount";
	String englishExtDic="english";
	String table8="insidePro";
	
	String inString;
	
	/**
	 * Search in ALL dBS together. THis takes time. Use with caution
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public int dbExist(String inString) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		this.inString = inString;

		//MySQL connection to a specific dB
		MySQLConn mysqlconn = new MySQLConn();
	    Statement stmt=mysqlconn.getConnection().createStatement();

	    
	    //after every stop, if it is found, the search has to stop
	    if(stmt.executeQuery(constructString(inString,worstPassTable1)).next())
			return 0;
		else if(stmt.executeQuery(constructString(inString,table2)).next())
			return 1;
		else if(stmt.executeQuery(constructString(inString,table3)).next())
			return 1;
		//else if(stmt.executeQuery(constructString(inString,table4)).next())
			//return 1;
		else if(stmt.executeQuery(constructString(inString,table5)).next())
			return 2;
		else if(stmt.executeQuery(constructString(inString,table6)).next())
			return 2;
		else if(stmt.executeQuery(constructString(inString,englishExtDic)).next())
			return 3;
		else if(stmt.executeQuery(constructString(inString,table8)).next())
			return 3;
	    
	    //default, if not found in any dic
		return 4;	
		
	}
	
	
	/**
	 * Search into the worst500 passwords.
	 * If found, the word is useless
	 * @param inString: Incoming word to check
	 * 
	 * @return true if found in dic 
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public boolean inWorstPassDb(String inString) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		//MySQL connection to a specific dB
		MySQLConn mysqlconn = new MySQLConn();
	    //Statement stmt=mysqlconn.getConnection().createStatement();
	    Statement stmt=mysqlconn.getConnection().createStatement();

		if(stmt.executeQuery(constructString(inString,worstPassTable1)).next())
			return true;
		return false;
	}
	
	
	/**
	 * Search into db1, db2, db3, db4 or more
	 * of very bad small lists of "forbidden" words
	 * If found, the word is bad. It is so, useless.
	 * Every dB is searched in a new loop for speed.
	 * If found the process exits with true.
	 * 
	 * @param inString: Incoming word to check
	 * ----------------------------
	 * @return true if found in dic 
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public boolean inSmallDbs(String inString ) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		//MySQL connection to a specific dB
		MySQLConn mysqlconn = new MySQLConn();
	    Statement stmt=mysqlconn.getConnection().createStatement();
	    
		//made separately for speed, so it doesn't have to look to the rest of the databases
		if(stmt.executeQuery(constructString(inString,table2)).next() )
			return true; 
		if(stmt.executeQuery(constructString(inString,table3)).next() )
			return true;
		//if(stmt.executeQuery(constructString(inString,table4)).next() )
			//return true;
		//if(stmt.executeQuery(constructString(inString,db4)).next() )
		//	return true;
		
		return false;
	}
		
	
	/**
	 * Search into db7 (English extended dictionary) or more
	 * of very big lists of words
	 * If found, it is up to the user what to do with it
	 * 
	 * @param inString: Incoming word to check
	 * 
	 * --------SQL parameters------
	 * @param mysqlconn
	 * @param conn
	 * @param stmt
	 * ----------------------------
	 * @return true if found in dic 
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public boolean inEnlgishDbs(String inString ) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		//MySQL connection to a specific dB
		MySQLConn mysqlconn = new MySQLConn();
	    Statement stmt=mysqlconn.getConnection().createStatement();
	    
		// add more dBs if needed
		if(stmt.executeQuery(constructString(inString,englishExtDic)).next() )
			return true; 
		
		return false;
	}
	

	private String constructString(String inString, String table){
		
		String str="Select password from "
				//+db
				//+"."
				+table
				+" where password='"
				+ inString+"'";
		return str;
	}

}
