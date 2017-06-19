/**
 * 
 */
package fr.epita.sd.services.impl;

import fr.epita.sd.datamodel.Message;

/**
 * @author tbrou
 *
 */
public class JSONServiceMessageJacksonImpl extends AbstractJSONServiceImpl<Message>{

	/* (non-Javadoc)
	 * @see fr.epita.sd.services.impl.AbstractJSONServiceImpl#getObjectClass()
	 */
	@Override
	public Class<Message> getObjectClass() {
	
		return Message.class;
	}


}
