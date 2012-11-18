/**
 * 
 */
package com.monstersoftwarellc.timeease.model.entry;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractResponse;
import com.monstersoftwarellc.timeease.model.impl.Entry;


/**
 * Concrete {@link AbstractResponse} object that specifies the {@link Entry} object.
 * @author nick
 *
 */
@XmlRootElement(name="response")
public class EntryResponse extends AbstractResponse {
	
	private Long id;
	
	private Entry responseEntity;
	
	private EntryListResponse listResponseEntities;

	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	@Override
	@XmlElement(name="time_entry_id")
	public Long getId() {
		return id;
	}

	/**
	 * @param responseEntity the responseEntity to set
	 */
	public void setResponseEntity(Entry responseEntity) {
		this.responseEntity = responseEntity;
	}

	/**
	 * @return the responseEntity
	 */
	@Override
	@XmlElement(name="time_entry")
	public Entry getResponseEntity() {
		return responseEntity;
	}
	
	/**
	 * @param listResponseEntities the listResponseEntities to set
	 */
	public void setListResponseEntities(EntryListResponse listResponseEntities) {
		this.listResponseEntities = listResponseEntities;
	}

	/**
	 * @return the listResponseEntities
	 */
	@Override
	@XmlAnyElement(lax=true)
	public EntryListResponse getListResponseEntities() {
		return listResponseEntities;
	}


}
