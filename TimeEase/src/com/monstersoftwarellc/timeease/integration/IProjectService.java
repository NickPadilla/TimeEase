/**
 * 
 */
package com.monstersoftwarellc.timeease.integration;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.TimeEaseSession;
import com.monstersoftwarellc.timeease.model.client.Client;
import com.monstersoftwarellc.timeease.model.project.Project;

/**
 * @author nicholas
 *
 */
public interface IProjectService  extends IIntegrationService<Project> {
	
	/**
	 * Returns all projects for a given client id, also method is responsible for getting the project from either memory, disk, or server.
	 * <br/><br/>
	 * <b>NOTE:</b> Will attempt to gather the list from memory then disk. If we can't find either we will go to the FreshBooks server, if so
	 * we will save the returned list to xml. We will also update the {@link TimeEaseSession} for you, if needed.
	 * list to xml file.
	 * @param clientId
	 * @return
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	abstract List<Project> getProjectsForClient(Client client) throws AuthenticationException, IllegalStateException, ConfigurationException;

}
