/**
 * 
 */
package com.monstersoftwarellc.timeease.integration;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.TimeEaseSession;
import com.monstersoftwarellc.timeease.model.impl.Task;

/**
 * @author nicholas
 *
 */
public interface ITaskService extends IIntegrationService<Task> {
	
	/**
	 * Returns all tasks for a given project id, also method is responsible for getting the tasks from either memory, disk, or server.
	 * <br/><br/>
	 * <b>NOTE:</b> Will attempt to gather the list from memory then disk. If we can't find either we will go to the FreshBooks server, if so
	 * we will save the returned list to xml. We will also update the {@link TimeEaseSession} for you, if needed.
	 * list to xml file.
	 * @param projectId
	 * @return
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	abstract List<Task> getTasksForProject(int projectId) throws AuthenticationException, IllegalStateException, ConfigurationException;

}
