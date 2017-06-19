/**
 * 
 */
package fr.epita.sd.datamodel;

/**
 * @author tbrou
 *
 */
public class Message {
	
	
	private String state;
	private String details;
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * @param state
	 * @param details
	 */
	public Message(String state, String details) {
		super();
		this.state = state;
		this.details = details;
	}
	
	

}
