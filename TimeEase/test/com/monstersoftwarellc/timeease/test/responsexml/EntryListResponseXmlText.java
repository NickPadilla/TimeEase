/**
 * 
 */
package com.monstersoftwarellc.timeease.test.responsexml;

import java.io.File;
import java.net.URL;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.entry.Entry;
import com.monstersoftwarellc.timeease.model.entry.EntryRequest;
import com.monstersoftwarellc.timeease.model.entry.EntryResponse;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;

/**
 * @author nick
 *
 */
public class EntryListResponseXmlText {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {
		URL url = EntryCreateResponseXmlTest.class.getResource("timeEntryList.xml");
		EntryRequest request = new EntryRequest();
		request.setTestFile(new File(url.getPath()));
		request.setItemsPerPage(15);
		request.setPage(1);
		//request.setFrom(new Date());
		//request.setTo(new Date());
		//request.setProjectId(9);
		//request.setTaskId(10);
		request.setMethod(RequestMethods.LIST);
		request.setEntity(new Entry());
		EntryResponse response = request.getResponse();
		
		System.out.println(response.getStatus());
		for(Entry entry : response.getListResponseEntities().getEntryList()){
			System.out.println(entry.toString());
		}
		System.out.println("ItemsPerPage: " + response.getListResponseEntities().getItemsPerPage());
		System.out.println("Page: " + response.getListResponseEntities().getPage());
		System.out.println("Pages: " + response.getListResponseEntities().getPages());
		System.out.println("Total: " + response.getListResponseEntities().getTotal());
	}

}
