/**
 * 
 */
package com.monstersoftwarellc.timeease.model.task;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractListResponse;

/**
 * Concrete {@link AbstractListResponse} that is needed for the {@link Task} object.
 * @author nick
 *
 */
@XmlRootElement(name="tasks")
public class TaskListResponse extends AbstractListResponse {
	
	private List<Task> taskList;


	/**
	 * @param taskList the taskList to set
	 */
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	/**
	 * @return the taskList
	 */
	@XmlAnyElement(lax=true)
	public List<Task> getTaskList() {
		return taskList;
	}

}
