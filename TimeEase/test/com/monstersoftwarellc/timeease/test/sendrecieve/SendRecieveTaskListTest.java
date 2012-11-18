/**
 * 
 */
package com.monstersoftwarellc.timeease.test.sendrecieve;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.impl.Task;
import com.monstersoftwarellc.timeease.model.task.TaskRequest;
import com.monstersoftwarellc.timeease.model.task.TaskResponse;

/**
 * @author nick
 *
 */
public class SendRecieveTaskListTest {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {
		TaskRequest request = new TaskRequest();
		request.setItemsPerPage(23);
		request.setPage(1);
		//request.setProjectId(54);
		request.setEntity(new Task());
		request.setMethod(RequestMethods.LIST);
		TaskResponse response = request.getResponse();
		
		System.out.println(response.getStatus());
		if(response.getListResponseEntities() != null){
			for(Task t : response.getListResponseEntities().getTaskList()){
				System.out.println(t.toString());
			}
		}
		System.out.println("ItemsPerPage: " + response.getListResponseEntities().getItemsPerPage());
		System.out.println("Page: " + response.getListResponseEntities().getPage());
		System.out.println("Pages: " + response.getListResponseEntities().getPages());
		System.out.println("Total: " + response.getListResponseEntities().getTotal());
	}

}
