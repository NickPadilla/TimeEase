/**
 * 
 */
package com.monstersoftwarellc.timeease.test.sendrecieve;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.entry.EntryRequest;
import com.monstersoftwarellc.timeease.model.entry.EntryResponse;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.impl.Entry;

/**
 * @author nick
 *
 */
public class SendRecieveEntryTest {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {
		Entry entry = new Entry();
		// note: only ever set the ID on objects when we already know the id
		entry.setExternalId(20361L);

		EntryRequest request = new EntryRequest();
		//request.setItemsPerPage(15);
		//request.setPage(1);
		//request.setFrom(new Date());
		//request.setTo(new Date());
		//request.setProjectId(9);
		//request.setTaskId(10);
		request.setMethod(RequestMethods.GET);
		request.setEntity(entry);
		EntryResponse response = request.getResponse();
		
		System.out.println(response.getStatus());

		System.out.println(response.getResponseEntity().toString());
	}

}
