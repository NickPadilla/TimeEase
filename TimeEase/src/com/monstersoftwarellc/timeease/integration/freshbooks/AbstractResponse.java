/**
 *  
 */
package com.monstersoftwarellc.timeease.integration.freshbooks;

import javax.xml.bind.annotation.XmlAttribute;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.integration.IResponse;
import com.monstersoftwarellc.timeease.model.enums.ResponseStatus;


/**
 * Base class for all Response implementations, one for each {@link IFreshbooksEntity}.<br/>
 * Concrete classes must set the <code>freshbooksEntity</code> apon creation. <br/>
 * TODO: Find a better way of doing this.
 * @author nick
 *
 */
public abstract class AbstractResponse implements IResponse {
	
	private ResponseStatus status;

	/**
	 * @return the status
	 */
	@XmlAttribute
	public ResponseStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	
}
