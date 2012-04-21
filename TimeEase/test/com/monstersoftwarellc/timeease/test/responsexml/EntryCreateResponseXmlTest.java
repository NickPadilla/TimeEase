/**
 * 
 */
package com.monstersoftwarellc.timeease.test.responsexml;

import java.io.File;
import java.net.URL;
import java.util.Date;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.entry.Entry;
import com.monstersoftwarellc.timeease.model.entry.EntryRequest;
import com.monstersoftwarellc.timeease.model.entry.EntryResponse;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.utility.TimeUtil;

/**
 * @author nick
 *
 */
public class EntryCreateResponseXmlTest {
	
	
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {		
		URL url = EntryCreateResponseXmlTest.class.getResource("timeEntryCreate.xml");
		EntryRequest request = new EntryRequest();
		request.setTestFile(new File(url.getPath()));
		request.setItemsPerPage(15);
		request.setPage(1);
		request.setFrom(TimeUtil.formatDate(new Date().getTime()));
		request.setTo(TimeUtil.formatDate(new Date().getTime()));
		request.setProjectId(9);
		request.setTaskId(10);
		request.setMethod(RequestMethods.CREATE);
		request.setEntity(new Entry());
		EntryResponse response = request.getResponse();
		System.out.println(response.getStatus());
		System.out.println(response.getId());
	}

}