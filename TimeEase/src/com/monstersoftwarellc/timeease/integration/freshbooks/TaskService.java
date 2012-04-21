/**
 * 
 */
package com.monstersoftwarellc.timeease.integration.freshbooks;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.integration.IIntegrationService;
import com.monstersoftwarellc.timeease.integration.ITaskService;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.task.Task;
import com.monstersoftwarellc.timeease.model.task.TaskRequest;

/**
 * @author nicholas
 *
 */
public class TaskService implements IIntegrationService<Task>, ITaskService {
	
	private static TaskService service = null;
	
	private TaskService(){}
	
	public static TaskService getCurrentInstance(){
		if(service == null){
			service = new TaskService();
		}
		return service;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#create(com.monstersoftwarellc.timeease.integration.IFreshbooksEntity)
	 */
	@Override
	public boolean create(Task item) {
		try {
			throw new Exception("Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#getById(int)
	 */
	@Override
	public Task getById(int id) throws AuthenticationException, IllegalStateException, ConfigurationException {
		Task task = null;
			Task temp = new Task();
			temp.setExternalId(id);
			TaskRequest request = new TaskRequest();
			request.setEntity(temp);
			request.setMethod(RequestMethods.GET);
			task = request.getResponse().getResponseEntity();
		return task;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#update(com.monstersoftwarellc.timeease.integration.IFreshbooksEntity)
	 */
	@Override
	public boolean update(Task item) {
		try {
			throw new Exception("Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#delete(com.monstersoftwarellc.timeease.integration.IFreshbooksEntity)
	 */
	@Override
	public Task delete(Task item) {
		try {
			throw new Exception("Method not filled in, please setup me up first!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.ITaskService#getTasksForProject(int)
	 */
	@Override
	public List<Task> getTasksForProject(int projectId) throws AuthenticationException, IllegalStateException, ConfigurationException {
		List<Task> tasks = new ArrayList<Task>();
		
				TaskRequest request = new TaskRequest();
				request.setItemsPerPage(100);
				request.setPage(1);
				request.setProjectId(projectId);
				request.setEntity(new Task());
				request.setMethod(RequestMethods.LIST);
				tasks = request.getResponse().getListResponseEntities().getTaskList();
				
		return tasks;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.integration.IIntegrationService#getAll()
	 */
	@Override
	public List<Task> getAll() throws AuthenticationException, IllegalStateException, ConfigurationException {
		List<Task> tasks = new ArrayList<Task>();
		int page = 1;
		boolean haveAll = false;
		while(!haveAll){
			List<Task> temp = getList(100, page);
			tasks.addAll(temp);
			// if we have under 100 or over 100 we are finished.
			// if we have over 100 we had to load from disk
			if(temp.size() < 100 || temp.size() > 100){
				haveAll = true;
			}
			page++;
		}
		return tasks;
	}
	
	/**
	 * @param numberOfItems
	 * @param page
	 * @return
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	private List<Task> getList(int numberOfItems, int page) throws AuthenticationException, IllegalStateException, ConfigurationException {
		List<Task> tasks = new ArrayList<Task>();
		
			TaskRequest request = new TaskRequest();
			request.setItemsPerPage(numberOfItems);
			request.setPage(page);
			request.setEntity(new Task());
			request.setMethod(RequestMethods.LIST);
			tasks = request.getResponse().getListResponseEntities().getTaskList();

		return tasks;
	}
}
