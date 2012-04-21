/**
 * 
 */
package com.monstersoftwarellc.timeease.test.sendrecieve;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.client.Client;
import com.monstersoftwarellc.timeease.model.client.ClientRequest;
import com.monstersoftwarellc.timeease.model.client.ClientResponse;
import com.monstersoftwarellc.timeease.model.enums.ClientStatus;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;

/**
 * @author nick
 *
 */
public class SendRecieveClientListTest {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {
		ClientRequest request = new ClientRequest();
		//request.setEmail("nicholas@monstersoftwarellc.com");
		request.setItemsPerPage(15);
		//request.setNotes("Testing!");
		request.setPage(1);
		request.setStatus(ClientStatus.ACTIVE);
		//request.setUsername("nick");
		request.setMethod(RequestMethods.LIST);
		request.setEntity(new Client());
		ClientResponse response = request.getResponse();
		
		System.out.println(response.getStatus());
		if(response.getListResponseEntities() != null){
			for(Client c : response.getListResponseEntities().getClientList()){
				System.out.println(c.toString());
			}
		}
		System.out.println("ItemsPerPage: " + response.getListResponseEntities().getItemsPerPage());
		System.out.println("Page: " + response.getListResponseEntities().getPage());
		System.out.println("Pages: " + response.getListResponseEntities().getPages());
		System.out.println("Total: " + response.getListResponseEntities().getTotal());
	}

}
