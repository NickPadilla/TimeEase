/**
 * 
 */
package com.monstersoftwarellc.timeease.model.entry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractRequest;
import com.monstersoftwarellc.timeease.model.impl.Entry;

/**
 * @author nick
 *
 */
@XmlRootElement(name="request")
public class EntryRequest extends AbstractRequest<EntryResponse>{
	
	private String from;
	
	private String to;
	
	private Integer projectId;
	
	private Integer taskId;

	/* (non-Javadoc)
	 * @see timeease.model.IRequest#getEntity()
	 */
	@Override
	public Entry getEntity() {
		return (Entry) entity;
	}

	
	/**
	 * @return the from
	 */
	@XmlElement(name="date_from")
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	@XmlElement(name="date_to")
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the projectId
	 */
	@XmlElement(name="project_id")
	public Integer getProjectId() {
		return projectId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the taskId
	 */
	@XmlElement(name="task_id")
	public Integer getTaskId() {
		return taskId;
	}
}
