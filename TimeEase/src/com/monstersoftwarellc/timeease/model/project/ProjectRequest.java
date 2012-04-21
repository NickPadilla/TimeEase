/**
 * 
 */
package com.monstersoftwarellc.timeease.model.project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractRequest;

/**
 * @author nick
 *
 */
@XmlRootElement(name="request")
public class ProjectRequest extends AbstractRequest<ProjectResponse>{
	
	private Integer clientId;
	
	private Integer taskId;

	/* (non-Javadoc)
	 * @see timeease.model.IRequest#getEntity()
	 */
	@Override
	public Project getEntity() {
		return (Project) entity;
	}
	
	/**
	 * @return the clientId
	 */
	@XmlElement(name="client_id")
	public Integer getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the taskId
	 */
	@XmlElement(name="task_id")
	public Integer getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

}
