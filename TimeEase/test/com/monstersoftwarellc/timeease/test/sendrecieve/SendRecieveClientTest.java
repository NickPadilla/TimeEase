/**
 * 
 */
package com.monstersoftwarellc.timeease.test.sendrecieve;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.client.ClientRequest;
import com.monstersoftwarellc.timeease.model.client.ClientResponse;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.impl.Client;

/**
 * @author nick
 *
 */
public class SendRecieveClientTest {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {
		Client client = new Client();
		// note: only ever set the ID on objects when we already know the id
		client.setExternalId(3);

		ClientRequest request = new ClientRequest();
		//request.setEmail("nicholas@monstersoftwarellc.com");
		//request.setItemsPerPage(15);
		//request.setNotes("Testing!");
		//request.setPage(1);
		//request.setStatus(ClientStatus.ACTIVE);
		//request.setUsername("nick");
		request.setMethod(RequestMethods.GET);
		request.setEntity(client);
		ClientResponse response = request.getResponse();
		
		System.out.println(response.getStatus());

		System.out.println(response.getResponseEntity());
	}

}
