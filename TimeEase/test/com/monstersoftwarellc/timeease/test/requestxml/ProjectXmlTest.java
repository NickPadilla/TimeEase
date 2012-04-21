/**
 * 
 */
package com.monstersoftwarellc.timeease.test.requestxml;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.monstersoftwarellc.timeease.model.client.Client;
import com.monstersoftwarellc.timeease.model.enums.BillingMethodType;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.project.Project;
import com.monstersoftwarellc.timeease.model.project.ProjectRequest;
import com.monstersoftwarellc.timeease.model.task.Task;
import com.monstersoftwarellc.timeease.utility.FreshbooksRequestUtility;

/**
 * @author nick
 *
 */
public class ProjectXmlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Project project = getProject();
        StringWriter sw = new StringWriter();
		for(RequestMethods method : RequestMethods.values()){
			// note: only ever set the ID on objects when we already know the id
			if(method != RequestMethods.CREATE)project.setExternalId(1);
			ProjectRequest request = getProjectRequest(project);
			request.setMethod(method);
			sw.append(FreshbooksRequestUtility.getRequest(request));
		}
		System.out.println(sw.getBuffer());
	}
	
	private static ProjectRequest getProjectRequest(Project project){
		ProjectRequest request = new ProjectRequest();
		request.setClientId(3);
		request.setEntity(project);
		request.setItemsPerPage(15);
		request.setPage(1);
		request.setTaskId(3);
		return request;
	}

	private static Project getProject() {
		Project project = new Project();
		project.setBillingMethodType(BillingMethodType.PROJECT);
		project.setClient(new Client());
		project.setDescription("Freshbooks Plugin");
		project.setName("FBPI");
		project.setProjectHourBudget(0d);
		project.setProjectTasks(getTasks());
		project.setRate(27.50);
		return project;
	}

	private static List<Task> getTasks() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		Task task = new Task();
		task.setBillable(false);
		task.setName("Task1");
		task.setDescription("Working Out Freshbooks Requests!");
		task.setRate(27.50);
		task.setExternalId(1);
		
		tasks.add(task);
		
		task = new Task();
		task.setBillable(false);
		task.setName("Task2");
		task.setDescription("Working Out Freshbooks Responses!");
		task.setRate(27.50);
		task.setExternalId(2);
		
		tasks.add(task);
		
		return tasks;
	}

}
