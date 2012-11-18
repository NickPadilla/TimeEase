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
public class SendRecieveEntryListTest {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {

		EntryRequest request = new EntryRequest();
		request.setItemsPerPage(15);
		request.setPage(1);
/*		Calendar from = Calendar.getInstance();
		from.add(Calendar.MONDAY, -2);
		Calendar to = Calendar.getInstance();
		to.add(Calendar.MONDAY, -1);
		request.setFrom(from.getTime());
		request.setTo(to.getTime());*/
		//request.setProjectId(9);
		//request.setTaskId(10);
		request.setMethod(RequestMethods.LIST);
		request.setEntity(new Entry());
		EntryResponse response = request.getResponse();
		
		System.out.println(response.getStatus());
		if(response.getListResponseEntities() != null){
			for(Entry e : response.getListResponseEntities().getEntryList()){
				System.out.println(e.toString());
			}
		}
		System.out.println("ItemsPerPage: " + response.getListResponseEntities().getItemsPerPage());
		System.out.println("Page: " + response.getListResponseEntities().getPage());
		System.out.println("Pages: " + response.getListResponseEntities().getPages());
		System.out.println("Total: " + response.getListResponseEntities().getTotal());
	}

}
