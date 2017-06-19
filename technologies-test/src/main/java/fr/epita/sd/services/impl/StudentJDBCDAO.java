/**
 * 
 */
package fr.epita.sd.services.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.sd.datamodel.Student;

/**
 * @author tbrou
 *
 */
public class StudentJDBCDAO {

	/**
	 * @return
	 * @throws SQLException
	 */
	private static Connection getConnection() throws SQLException {
		String connectionString = "jdbc:derby:memory:testDB;create=true";
		Connection connection = DriverManager.getConnection(connectionString, "user", "test");
		return connection;
	}
	
	
	/**
	 * @throws SQLException
	 */
	private static void createDB() throws SQLException {
		//first : connect
		Connection connection = getConnection();
		
		
		//secondly: create table
		String tableCreationString = "CREATE TABLE STUDENTS"
				+ "(STUDENT_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT STUDENT_PK PRIMARY KEY, "
				+ "FIRST_NAME VARCHAR(35), "
				+ "LAST_NAME VARCHAR(50), "
				+ "BIRTHDATE DATE)";
		PreparedStatement pstmt = connection.prepareStatement(tableCreationString);
		
		pstmt.execute();
		pstmt.close();
		
		
		
	}
	
	public void insert(Student student) throws SQLException{
		
	
		Connection connection = getConnection();
		//then insert
		String insertionString = "INSERT INTO STUDENTS (FIRST_NAME,LAST_NAME,BIRTHDATE) VALUES (?,?,?)";
		
		
		PreparedStatement insertionStatement = connection.prepareStatement(insertionString);
		insertionStatement.setString(1,student.getFirstName());
		insertionStatement.setString(2,student.getLastName());
		insertionStatement.setDate(3, new java.sql.Date(student.getBirthDate().getTime()));
		insertionStatement.execute();
		insertionStatement.close();
		connection.close();
	}
	
	
	public List<Student> search(Student criteria) throws SQLException{
		// eventually search
		
		Connection connection = getConnection();
		String selectString = "SELECT * FROM STUDENTS";
		PreparedStatement selectStatement = connection.prepareStatement(selectString);
		
		ResultSet rs = selectStatement.executeQuery();
		List<Student> studentList = new ArrayList<>();
		while(rs.next()){
			String firstName = rs.getString("FIRST_NAME");
			String lastName = rs.getString("LAST_NAME");
			Date date = rs.getDate("BIRTHDATE");
			studentList.add(new Student(firstName, lastName,"", date));
		}
		rs.close();
		selectStatement.close();
		connection.close();
		
		return studentList;
	}
	
	
}
