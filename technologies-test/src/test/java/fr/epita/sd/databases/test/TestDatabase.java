package fr.epita.sd.databases.test;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	   String query = "CREATE TABLE students "
				+ "(STUDENT_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT STUDENT_PK PRIMARY KEY, "
				+ "FIRST_NAME VARCHAR(35), "
				+ "LAST_NAME VARCHAR(50), "
				+ "BIRTHDATE DATE)";
	   PreparedStatement statement = connection.prepareStatement(query);
	   statement.execute();
	   statement.close();
	   
	   //add values to table
	   query = "INSERT INTO students (first_name, last_name, birthdate) values (?, ?, ?)";
	   PreparedStatement statement0 = connection.prepareStatement(query);
	   
	   statement0.setString(1, "Gervaise");
	   statement0.setString(2, "Richard");
	   statement0.setDate(3, java.sql.Date.valueOf("1970-06-01"));
	   statement0.addBatch();
	   
	   statement0.setString(1, "Thomas");
	   statement0.setString(2, "Broussard");
	   statement0.setDate(3, java.sql.Date.valueOf("1986-07-01"));
	   statement0.addBatch();
	  
	   statement0.executeBatch();
	    
   }
   
   @BeforeClass
   public static void setUp() throws SQLException{
	   connection = getConnection();
	   initDB(connection);
		
   }
   
   @Test
   public void testConnection(){
	   try {
		   org.junit.Assert.assertNotNull(connection);
	   } catch (Exception e) {
		   LOGGER.log(Level.SEVERE, "testConnection :"+e.getMessage());
	   }
	  
   }
   
   @Test 
   public void writeXml(){
	  
		ResultSet rs = getStudents();
		try {
		    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    Document doc = builder.newDocument();
		    Element root = doc.createElement("Students");
		    doc.appendChild(root);
			while(rs.next()){
				Element student = doc.createElement("Student");
				student.setAttribute("id", rs.getString("student_id"));
			    Element firstname = doc.createElement("FirstName");
			    firstname.setTextContent(rs.getString("first_name"));
			    student.appendChild(firstname);
			    Element lastname = doc.createElement("LastName");
			    lastname.setTextContent(rs.getString("last_name"));
			    student.appendChild(lastname);
			    Element birthdate = doc.createElement("Birthdate");
			    birthdate.setTextContent(rs.getString("birthdate"));
			    student.appendChild(birthdate);
			    root.appendChild(student);
			}
			rs.close();
			connection.close();
			
			// write content of doc into xml
			String path =  System.getProperty("user.dir") +"/tmp/xmltest/identities.xml";
			fromDocToXML(doc, path);
		
			
		} catch (SQLException| ParserConfigurationException e) {
			 LOGGER.log(Level.SEVERE, "writeXML :"+e.getMessage());
		}
		
		
   }
   
private void fromDocToXML(Document doc, String path) {
	Transformer transformer;
	try {
		transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc.getDocumentElement()), new StreamResult(writer));
		String result = writer.toString();
		File file = new File(path);
		file.getParentFile().mkdirs();
		if (!file.exists()){
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		fw.write(result);
		fw.flush();
		fw.close();
		
	} catch (TransformerFactoryConfigurationError | TransformerException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
private ResultSet getStudents() {
	ResultSet rs = null; 
	PreparedStatement selectStatement;
	String selectString = "SELECT * FROM STUDENTS";
	try {
		selectStatement = connection.prepareStatement(selectString);
		rs = selectStatement.executeQuery();
	} catch (SQLException e) {
		LOGGER.log(Level.SEVERE, "writeXML: "+e.getMessage());
		return rs;
	}
	return rs;
}
   
 
}
