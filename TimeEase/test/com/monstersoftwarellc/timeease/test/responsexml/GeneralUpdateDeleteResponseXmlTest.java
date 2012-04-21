/**
 * 
 */
package com.monstersoftwarellc.timeease.test.responsexml;

import java.io.File;
import java.net.URL;

import com.monstersoftwarellc.timeease.integration.GeneralResponse;
import com.monstersoftwarellc.timeease.utility.FreshbooksResponseUtility;

/**
 * @author nick
 *
 */
public class GeneralUpdateDeleteResponseXmlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneralResponse blankResponse = new GeneralResponse();
		URL url = EntryCreateResponseXmlTest.class.getResource("timeEntryDelete.xml");
		GeneralResponse response = (GeneralResponse) FreshbooksResponseUtility.getResponse(blankResponse,new File(url.getPath()));
		System.out.println("Delete XML : " + response.getStatus());
		
		url = EntryCreateResponseXmlTest.class.getResource("timeEntryUpdate.xml");
		response = (GeneralResponse) FreshbooksResponseUtility.getResponse(blankResponse,new File(url.getPath()));
		System.out.println("Update XML : " + response.getStatus());
		
		url = EntryCreateResponseXmlTest.class.getResource("failedXml.xml");
		response = (GeneralResponse) FreshbooksResponseUtility.getResponse(blankResponse,new File(url.getPath()));
		System.out.println("Failed XML : " + response.getStatus());
		System.out.println("Failed XML Message : " + response.getError());
	}

}
