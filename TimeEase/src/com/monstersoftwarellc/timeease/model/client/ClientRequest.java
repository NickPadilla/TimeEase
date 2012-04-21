/**
 * 
 */
package com.monstersoftwarellc.timeease.model.client;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractRequest;
import com.monstersoftwarellc.timeease.model.enums.ClientStatus;

/**
 * @author nick
 *
 */
@XmlRootElement(name="request")
public class ClientRequest extends AbstractRequest<ClientResponse>{
	
	private String email;
	
	private String username;
	
	private Date updatedFrom;
	
	private Date updatedTo;
	
	private ClientStatus status;
	
	private String notes;

	
	/* (non-Javadoc)
	 * @see timeease.model.IRequest#getEntity()
	 */
	@Override
	public Client getEntity() {
		return (Client) entity;
	}
	
	/**
	 * @return the email
	 */
	@XmlElement(name="email")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the username
	 */
	@XmlElement(name="username")
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the updatedFrom
	 */
	@XmlElement(name="updated_from")
	public Date getUpdatedFrom() {
		return updatedFrom;
	}

	/**
	 * @param updatedFrom the updatedFrom to set
	 */
	public void setUpdatedFrom(Date updatedFrom) {
		this.updatedFrom = updatedFrom;
	}

	/**
	 * @return the updatedTo
	 */
	@XmlElement(name="updated_to")
	public Date getUpdatedTo() {
		return updatedTo;
	}

	/**
	 * @param updatedTo the updatedTo to set
	 */
	public void setUpdatedTo(Date updatedTo) {
		this.updatedTo = updatedTo;
	}

	/**
	 * @return the status
	 */
	@XmlElement(name="folder")
	public ClientStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ClientStatus status) {
		this.status = status;
	}

	/**
	 * @return the notes
	 */
	@XmlElement(name="notes")
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
