/**
 * 
 */
package com.monstersoftwarellc.timeease.test.requestxml;

import java.io.StringWriter;

import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.task.Task;
import com.monstersoftwarellc.timeease.model.task.TaskRequest;
import com.monstersoftwarellc.timeease.utility.FreshbooksRequestUtility;

/**
 * @author nick
 *
 */
public class TaskXmlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Task task = getTask();
        StringWriter sw = new StringWriter();
		for(RequestMethods method : RequestMethods.values()){
			// note: only ever set the ID on objects when we already know the id
			if(method != RequestMethods.CREATE)task.setExternalId(1);
			TaskRequest request = getTaskRequest(task);
			request.setMethod(method);
			sw.append(FreshbooksRequestUtility.getRequest(request));
		}
		System.out.println(sw.getBuffer());
	}
	
	private static TaskRequest getTaskRequest(Task task){
		TaskRequest request = new TaskRequest();
		request.setItemsPerPage(23);
		request.setEntity(task);
		request.setPage(1);
		request.setProjectId(54);
		return request;
	}

	private static Task getTask() {
		Task task = new Task();
		task.setBillable(false);
		task.setDescription("Working Out Freshbooks Requests!");
		task.setRate(27.50);
		return task;
	}

}
