/**
 * 
 */
package com.monstersoftwarellc.timeease.test.sendrecieve;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.task.Task;
import com.monstersoftwarellc.timeease.model.task.TaskRequest;
import com.monstersoftwarellc.timeease.model.task.TaskResponse;

/**
 * @author nick
 *
 */
public class SendRecieveTaskTest {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {
		Task task = new Task();
		// note: only ever set the ID on objects when we already know the id
		task.setExternalId(1);

		TaskRequest request = new TaskRequest();
		//request.setItemsPerPage(23);
		//request.setPage(1);
		//request.setProjectId(54);
		request.setEntity(task);
		request.setMethod(RequestMethods.GET);
		TaskResponse response = request.getResponse();
		
		System.out.println(response.getStatus());

		System.out.println(response.getResponseEntity().toString());
	}

}
