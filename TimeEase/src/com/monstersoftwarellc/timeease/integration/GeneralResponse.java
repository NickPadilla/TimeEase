/**
 * 
 */
package com.monstersoftwarellc.timeease.integration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractResponse;


/**
 * @author nick
 *
 */
@XmlRootElement(name="response")
public class GeneralResponse extends AbstractResponse {
	
	private String error;


	@Override
	public Long getId() {
		throw new IllegalStateException("This method should never be called for this class!");
	}

	@Override
	public IFreshbooksEntity getResponseEntity() {
		return null;
	}

	@Override
	public Object getListResponseEntities() {
		throw new IllegalStateException("This method should never be called for this class!");
	}
	
	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the error
	 */
	@XmlElement(required=false)
	public String getError() {
		return error;
	}
}
