/**
 * 
 */
package fr.epita.sd.databases.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.epita.sd.datamodel.Student;
import fr.epita.sd.services.JSONService;
import fr.epita.sd.services.impl.JSONServiceStudentJacksonImpl;

/**
 * @author tbrou
 *
 */
public class TestJacksonLibrary {

	@Test
	public void firstTest() throws JsonGenerationException, JsonMappingException, IOException{
		Student student = new Student("Thomas", "Broussard", "tbr@tbr.com",new Date());
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.writeValue(new File("target/studentTest.json"), student);
		
		List<Student> list = new ArrayList<>();
		list.add(student);
		list.add(student);
		mapper.writeValue(new File("target/studentTestList.json"), list);

		list = mapper.readValue(new File("target/studentTestList.json"), new TypeReference<List<Student>>(){});
		
		
		System.out.println(list);
		
	}
	
	@Test
	public void testJsonToStudent() {
		JSONService<Student> studentJsonService = new JSONServiceStudentJacksonImpl();
		//{"firstName":"Thomas","lastName":"Broussard","email":"tbr@tbr.com","birthDate":1496686609417}
		Student student = studentJsonService.fromJson("{\"firstName\":\"Thomas\", \"lastName\": \"Broussard\", \"email\":\"tbr@tbr.com\"}");
		
		System.out.println("testJsonToStudent :" + student);
		
	}
	@Test
	public void testJsonToStudentAsList() {
		JSONService<Student> studentJsonService = new JSONServiceStudentJacksonImpl();
		//{"firstName":"Thomas","lastName":"Broussard","email":"tbr@tbr.com","birthDate":1496686609417}
		List<Student> studentsList = studentJsonService.fromJsonAsList("[{\"firstName\":\"Thomas\", \"lastName\": \"Broussard\", \"email\":\"tbr@tbr.com\"}]");
		
		System.out.println("testJsonToStudentAsList :" + studentsList);
		
	}
	
	@Test
	public void testStudentToJsonAsList() {
		JSONService<Student> studentJsonService = new JSONServiceStudentJacksonImpl();
		List<Student> studentList = new ArrayList<Student>();
		studentList.add(new Student("thomas", "broussard", "-", new Date()));
		
		
		System.out.println("testStudentToJsonAsList :" + studentJsonService.toJson(studentList));
		
	}
	
	
	@Test
	public void testStudentToJson() {
		JSONService<Student> studentJsonService = new JSONServiceStudentJacksonImpl();
		Student student = new Student("thomas2", "broussard", "-", new Date());
		
		
		System.out.println("testStudentToJsonAsList :" + studentJsonService.toJson(student));
		
	}
	
}
