/**
 * 
 */
package com.monstersoftwarellc.timeease.test.requestxml;

import java.io.StringWriter;
import java.util.Date;

import com.monstersoftwarellc.timeease.model.client.Client;
import com.monstersoftwarellc.timeease.model.client.ClientRequest;
import com.monstersoftwarellc.timeease.model.enums.ClientStatus;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.utility.FreshbooksRequestUtility;

/**
 * @author nick
 *
 */
public class ClientXmlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        StringWriter sw = new StringWriter();
        Client client = getClient();
		for(RequestMethods method : RequestMethods.values()){			
			ClientRequest request = getClientRequest(client);
			if(method != RequestMethods.CREATE)client.setExternalId(8);
			request.setMethod(method);
			sw.append(FreshbooksRequestUtility.getRequest(request));		
		}
		System.out.println(sw.getBuffer());
	}

	private static Client getClient(){
		Client client = new Client();
		client.setFirstName("Nicholas");
		client.setLastName("Padilla");
		client.setOrganization("Monster Software LLC");
		client.setEmail("nicholas@monstersoftwarellc.com");
		client.setUsername("nickPadilla");
		client.setPassword("kissmyass");
		client.setWorkPhone("(575) 680-0146");
		client.setHomePhone("(575) 680-0146");
		client.setCellPhone("(575) 680-0146");
		client.setFax("");
		client.setLanguage("en");
		client.setCurrencyCode("USD");
		client.setNotes("I am a client!");
		client.setPrimaryAddress("123 fake street");
		client.setPrimaryAddress2("");
		client.setPrimaryCity("New York");
		client.setPrimaryState("New York");
		client.setPrimaryCountry("United States");
		client.setPrimaryZipCode("88888");
		client.setSecondaryZipCode("77777");
		client.setVatName("vat");
		client.setVatNumber(45);
		return client;
	}

	private static ClientRequest getClientRequest(Client client) {
		ClientRequest request = new ClientRequest();
		request.setEmail(client.getEmail());
		request.setItemsPerPage(7);
		request.setNotes("this is a test!");
		request.setPage(1);
		request.setStatus(ClientStatus.ACTIVE);
		request.setUpdatedFrom(new Date());
		request.setUpdatedTo(new Date());
		request.setUsername("kevinmalone");
		request.setEntity(client);
		return request;
	}
}
