/**
 * 
 */
package com.monstersoftwarellc.timeease.integration.freshbooks;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;


/**
 * Object that represents a full request to send to Freshbooks.
 * @author nick
 *
 */
@XmlRootElement(name="request")
public class EntityRequest {

	private String method;
	
    private IFreshbooksEntity freshbooksEntity;
    

	/**
	 * @return the method
	 */
	@XmlAttribute
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @param freshbooksEntity the freshbooksEntity to set
	 */
	public void setFreshbooksEntity(IFreshbooksEntity body) {
		this.freshbooksEntity = body;
	}

	/**
	 * @return the freshbooksEntity
	 */
	@XmlAnyElement
	public IFreshbooksEntity getFreshbooksEntity() {
		return freshbooksEntity;
	}
}
