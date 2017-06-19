/**
 * 
 */
package fr.epita.sd.services;

import java.util.List;

/**
 * @author tbrou
 *
 */
public interface JSONService<T> {
	
	String toJson(List<T> list);
	
	String toJson(T instance);
	
	T fromJson(String jsonString);
	
	List<T> fromJsonAsList(String jsonString);
	
}
