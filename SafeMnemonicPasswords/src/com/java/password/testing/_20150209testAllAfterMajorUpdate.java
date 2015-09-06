package com.java.password.testing;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import com.java.password.db.dBSearch.LookIn2db;
import com.java.password.db.dbconn.MySQLConn;

public class _20150209testAllAfterMajorUpdate {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		MySQLConn con= new MySQLConn();
		LookIn2db look4String = new LookIn2db();
		
		long startTime = System.currentTimeMillis();//counting time...
		
		int result = look4String.dbExist("godess");
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = (stopTime - startTime); 
		long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
		System.out.println("Elapsed time="+seconds+"sec");
		System.out.println("result="+result);

	}

}
