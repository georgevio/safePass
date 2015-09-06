package com.java.password.db.dbconn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//enter below all the credentials to connect to 
//specific server with username, password etc.

//Don't forget to rename this class to MySQLConn


/**
 * Include here credentials for connecting to a dB.
 * This class makes sure that credentials are
 * not populated with the rest of the code
 * 
 * The IP or URL of the server is separated from the
 * dB name for flexibility. 
 * the prefix "jdbc:mysql://" is added automatically
 * 
 * @author Violettas_geo
 *
 */
public class MySQLConn_NoCredentials {

	String jdbcCon="jdbc:mysql://"; //essential mySQL java con prefix

	String server = "xxx.xxx.xxx.xxx";
	String db="dbName";
	String username="username";
	String password="password";
	
	private Connection conn;
	
	public MySQLConn_NoCredentials() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		String fulldB=jdbcCon+server+"/"+db;
		//System.out.println("db url= "+fulldB);
		try {
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
			conn = DriverManager.getConnection(fulldB,username,password);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
	}

	// getter
	public Connection getConnection(){
		return conn;
	}
}

