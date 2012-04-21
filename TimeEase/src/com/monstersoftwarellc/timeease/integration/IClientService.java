/**
 * 
 */
package com.monstersoftwarellc.timeease.integration;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.client.Client;
import com.monstersoftwarellc.timeease.model.enums.ClientStatus;

/**
 * @author nicholas
 *
 */
public interface IClientService extends IIntegrationService<Client> {
	
	/**
	 * Returns all clients, also method is responsible for getting the tasks, if not from the db then the configured
	 * integration type(s).  
	 * <br/><br/>
	 * 
	 * @param ClientStatus - status to search
	 * @return - all clients of the given types - or all of them
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	abstract List<Client> getClients(ClientStatus statuses) throws AuthenticationException, IllegalStateException, ConfigurationException;

}
