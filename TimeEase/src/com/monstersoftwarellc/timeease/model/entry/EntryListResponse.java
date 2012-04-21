/**
 * 
 */
package com.monstersoftwarellc.timeease.model.entry;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractListResponse;
import com.monstersoftwarellc.timeease.utility.ComparatorUtility;

/**
 * Concrete {@link AbstractListResponse} object for the {@link Entry} entity.
 * @author nick
 *
 */
@XmlRootElement(name="time_entries")
public class EntryListResponse extends AbstractListResponse {
	
	private List<Entry> entryList;

	
	/**
	 * @return the entryList
	 */
	@XmlAnyElement(lax=true)
	public List<Entry> getEntryList() {
		if(entryList != null){
			//TODO: sort list; kinda hacky to add here, need to move this to a service layer at some point
			Collections.sort(entryList, ComparatorUtility.byEntryDate());
		}else{
			entryList = Collections.emptyList();
		}
		return entryList;
	}

	/**
	 * @param entryList the entryList to set
	 */
	public void setEntryList(List<Entry> returnedList) {
		this.entryList = returnedList;
	}

}
