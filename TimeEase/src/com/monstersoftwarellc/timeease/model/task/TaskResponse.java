/**
 * 
 */
package com.monstersoftwarellc.timeease.model.task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractResponse;
import com.monstersoftwarellc.timeease.model.impl.Task;

/**
 * Concrete {@link AbstractResponse} object that specifies the {@link Task} object.
 * @author nick
 *
 */
@XmlRootElement(name="response")
public class TaskResponse extends AbstractResponse {
	
	private Long id;
	
	private Task responseEntity;
	
	private TaskListResponse listResponseEntities;
	
	
	/**
	 * @return the id
	 */
	@XmlElement(name="task_id")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the responseEntity
	 */
	@XmlElement(name="task")
	public Task getResponseEntity() {
		return responseEntity;
	}

	/**
	 * @param responseEntity the responseEntity to set
	 */
	public void setResponseEntity(Task task) {
		this.responseEntity = task;
	}

	/**
	 * @return the listResponseEntities
	 */
	@XmlElement(name="tasks")
	public TaskListResponse getListResponseEntities() {
		return listResponseEntities;
	}

	/**
	 * @param listResponseEntities the listResponseEntities to set
	 */
	public void setListResponseEntities(TaskListResponse taskListResponse) {
		this.listResponseEntities = taskListResponse;
	}

}
