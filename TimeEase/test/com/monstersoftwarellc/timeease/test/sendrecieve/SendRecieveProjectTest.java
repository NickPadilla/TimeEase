/**
 * 
 */
package com.monstersoftwarellc.timeease.test.sendrecieve;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.project.Project;
import com.monstersoftwarellc.timeease.model.project.ProjectRequest;
import com.monstersoftwarellc.timeease.model.project.ProjectResponse;

/**
 * @author nick
 *
 */
public class SendRecieveProjectTest {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {
		Project project = new Project();
		// note: only ever set the ID on objects when we already know the id
		project.setExternalId(2);

		ProjectRequest request = new ProjectRequest();
		//request.setItemsPerPage(15);
		//request.setPage(1);
		//request.setClientId(3);
		//request.setTaskId(45);
		request.setMethod(RequestMethods.GET);
		request.setEntity(project);
		ProjectResponse response = request.getResponse();
		
		System.out.println(response.getStatus());

		System.out.println(response.getResponseEntity());
	}

}
