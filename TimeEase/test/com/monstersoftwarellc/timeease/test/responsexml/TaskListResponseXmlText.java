/**
 * 
 */
package com.monstersoftwarellc.timeease.test.responsexml;

import java.io.File;
import java.net.URL;

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
public class TaskListResponseXmlText {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IllegalStateException, ConfigurationException {
		URL url = TaskListResponseXmlText.class.getResource("taskList.xml");
		TaskRequest request = new TaskRequest();
		request.setTestFile(new File(url.getPath()));
		request.setItemsPerPage(23);
		request.setEntity(new Task());
		request.setPage(1);
		//request.setProjectId(54);
		request.setMethod(RequestMethods.LIST);
		TaskResponse response = request.getResponse();
		
		System.out.println(response.getStatus());
		for(Task t : response.getListResponseEntities().getTaskList()){
			System.out.println(t.toString());
		}
		System.out.println("ItemsPerPage: " + response.getListResponseEntities().getItemsPerPage());
		System.out.println("Page: " + response.getListResponseEntities().getPage());
		System.out.println("Pages: " + response.getListResponseEntities().getPages());
		System.out.println("Total: " + response.getListResponseEntities().getTotal());
	}

}
