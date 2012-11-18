/**
 * 
 */
package com.monstersoftwarellc.timeease.test.responsexml;

import java.io.File;
import java.net.URL;

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
public class EntryGetResponseXmlTest {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {
		URL url = EntryCreateResponseXmlTest.class.getResource("timeEntryGet.xml");
		EntryRequest request = new EntryRequest();
		request.setTestFile(new File(url.getPath()));
		request.setItemsPerPage(15);
		request.setPage(1);
		//request.setFrom(new Date());
		//request.setTo(new Date());
		//request.setProjectId(9);
		//request.setTaskId(10);
		request.setMethod(RequestMethods.GET);
		request.setEntity(new Entry());
		EntryResponse response = request.getResponse();
		
		System.out.println(response.getStatus());
		System.out.println(response.getResponseEntity());
	}

}
