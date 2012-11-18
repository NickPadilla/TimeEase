/**
 * 
 */
package com.monstersoftwarellc.timeease.model.task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractRequest;
import com.monstersoftwarellc.timeease.model.impl.Task;

/**
 * @author nick
 *
 */
@XmlRootElement(name="request")
public class TaskRequest extends AbstractRequest<TaskResponse>{
	
	private Integer projectId;

	@Override
	public Task getEntity() {
		return (Task) entity;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Integer taskId) {
		this.projectId = taskId;
	}

	/**
	 * @return the projectId
	 */
	@XmlElement(name="project_id")
	public Integer getProjectId() {
		return projectId;
	}
}
