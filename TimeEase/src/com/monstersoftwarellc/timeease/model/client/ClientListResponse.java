/**
 * 
 */
package com.monstersoftwarellc.timeease.model.client;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractListResponse;
import com.monstersoftwarellc.timeease.model.impl.Client;

/**
 * Concrete {@link AbstractListResponse} object for the {@link Client} entity.
 * @author nick
 *
 */
@XmlRootElement(name="clients")
public class ClientListResponse extends AbstractListResponse {
	
	private List<Client> clientList;
	
	/**
	 * @return the clientList
	 */
	@XmlAnyElement(lax=true)
	public List<Client> getClientList() {
		return clientList;
	}

	/**
	 * @param clientList the clientList to set
	 */
	public void setClientList(List<Client> returnedList) {
		this.clientList = returnedList;
	}

}
