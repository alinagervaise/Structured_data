/**
 * 
 */
package fr.epita.sd.services.impl;

import fr.epita.sd.datamodel.Student;

/**
 * @author tbrou
 *
 */
public class JSONServiceStudentJacksonImpl extends AbstractJSONServiceImpl<Student>{

	/* (non-Javadoc)
	 * @see fr.epita.sd.services.impl.AbstractJSONServiceImpl#getObjectClass()
	 */
	@Override
	public Class<Student> getObjectClass() {
		// TODO Auto-generated method stub
		return Student.class;
	}


	
	

}
