/**
 * 
 */
package fr.epita.sd.services.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.epita.sd.services.JSONService;

/**
 * @author tbrou
 *
 */
public abstract class AbstractJSONServiceImpl<T> implements JSONService<T> {


	ObjectMapper mapper = new ObjectMapper();
	/* (non-Javadoc)
	 * @see fr.epita.sd.services.JSONService#toJson(java.util.List)
	 */
	@Override
	public String toJson(List<T> list) {
		StringWriter writer = new StringWriter();
		String result ="";
		try {
			mapper.writeValue(writer, list);
			result = writer.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fr.epita.sd.services.JSONService#toJson(java.lang.Object)
	 */
	@Override
	public String toJson(T instance) {
		StringWriter writer = new StringWriter();
		String result ="";
		try {
			mapper.writeValue(writer, instance);
			result = writer.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fr.epita.sd.services.JSONService#fromJson(java.lang.String)
	 */
	@Override
	public T fromJson(String jsonString) {
		T instance = null;
		try {
			T student = getObjectClass().newInstance();
			student = mapper.readValue(jsonString, getObjectClass());
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see fr.epita.sd.services.JSONService#fromJsonAsList(java.lang.String)
	 */
	@Override
	public List<T> fromJsonAsList(String jsonString) {
		List<T> list = new ArrayList<T>();
		try {
			list = mapper.readValue(jsonString, new TypeReference<List<T>>() {});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	

	public abstract Class<T> getObjectClass();
	

}
