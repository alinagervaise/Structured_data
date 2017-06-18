package fr.epita.sd.databases.test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class TestDatabase {
	static Connection connection;
	static Logger LOGGER= Logger.getLogger(TestDatabase.class.getName());
	
	
	private static Connection getConnection() throws SQLException{
		String connectionString = "jdbc:derby:memory:IAM;create=true";
		Connection connection = DriverManager.getConnection(connectionString, "admin", "admin");
		return connection;
	}
   private static void initDB(Connection connection) throws SQLException{
	   // create table
	   String query = "CREATE TABLE students"
				+ "(STUDENT_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT STUDENT_PK PRIMARY KEY, "
				+ "FIRST_NAME VARCHAR(35), "
				+ "LAST_NAME VARCHAR(50), "
				+ "BIRTHDATE DATE)";
	   PreparedStatement statement = connection.prepareStatement(query);
	   statement.execute();
	   
	   //add values to table
	   query = "INSERT INTO student (student_id, first_name, last_name, birthdate) values (?, ?, ?,?)";
	   statement.setString(1, "Thomas");
	   statement.setString(2, "Broussard");
	   statement.setDate(3, java.sql.Date.valueOf("1986-07-01"));
	   statement.execute();
	   
	  //add values to table
	   query = "INSERT INTO student (student_id, first_name, last_name, birthdate) values (?, ?, ?,?)";
	   statement.setString(1, "Gervaise");
	   statement.setString(2, "Richard");
	   statement.setDate(3, java.sql.Date.valueOf("1970-06-01"));
	   statement.execute();
	   statement.close();
	    
   }
   
   @BeforeClass
   public static void setUp() throws SQLException{
	   connection = getConnection();
	   initDB(connection);
		
   }
   
   @Test
   public void testConnection(){
	   org.junit.Assert.assertNotNull(connection);
	   try {
		initDB(connection);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		LOGGER.log(Level.SEVERE, "in Method testConnection: Fail database connection!");
	}
   }
}
