package com.monstersoftwarellc.timeease.model.client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractResponse;

/**
 * Concrete {@link AbstractResponse} object that specifies the {@link Client} object.
 * @author nick
 *
 */
@XmlRootElement(name="response")
public class ClientResponse extends AbstractResponse {

	private Long id;
	
	private Client responseEntity;
	
	private ClientListResponse listResponseEntities;
	
	/**
	 * @return the id
	 */
	@XmlElement(name="client_id")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the responseEntity
	 */
	@XmlElement(name="client")
	public Client getResponseEntity() {
		return responseEntity;
	}

	/**
	 * @param responseEntity the responseEntity to set
	 */
	public void setResponseEntity(Client client) {
		this.responseEntity = client;
	}

	/**
	 * @return the listResponseEntities
	 */
	@XmlElement(name="clients")
	public ClientListResponse getListResponseEntities() {
		return listResponseEntities;
	}

	/**
	 * @param listResponseEntities the listResponseEntities to set
	 */
	public void setListResponseEntities(ClientListResponse clientListResponse) {
		this.listResponseEntities = clientListResponse;
	}
	
}
