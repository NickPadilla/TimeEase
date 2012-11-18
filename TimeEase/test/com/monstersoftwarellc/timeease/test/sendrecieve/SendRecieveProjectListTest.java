/**
 * 
 */
package com.monstersoftwarellc.timeease.test.sendrecieve;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.impl.Project;
import com.monstersoftwarellc.timeease.model.project.ProjectRequest;
import com.monstersoftwarellc.timeease.model.project.ProjectResponse;

/**
 * @author nick
 *
 */
public class SendRecieveProjectListTest {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {
		ProjectRequest request = new ProjectRequest();
		request.setItemsPerPage(15);
		//request.setPage(1);
		//request.setClientId(3);
		//request.setTaskId(45);
		request.setMethod(RequestMethods.LIST);
		request.setEntity(new Project());
		ProjectResponse response = request.getResponse();
		
		System.out.println(response.getStatus());
		if(response.getListResponseEntities() != null){
			for(Project p : response.getListResponseEntities().getProjectList()){
				System.out.println(p.toString());
			}
		}
		System.out.println("ItemsPerPage: " + response.getListResponseEntities().getItemsPerPage());
		System.out.println("Page: " + response.getListResponseEntities().getPage());
		System.out.println("Pages: " + response.getListResponseEntities().getPages());
		System.out.println("Total: " + response.getListResponseEntities().getTotal());
	}

}
