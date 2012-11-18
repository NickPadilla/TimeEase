/**
 * 
 */
package com.monstersoftwarellc.timeease.model.project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractResponse;
import com.monstersoftwarellc.timeease.model.impl.Project;

/**
 * Concrete {@link AbstractResponse} object that specifies the {@link Project} object.
 * @author nick
 *
 */
@XmlRootElement(name="response")
public class ProjectResponse extends AbstractResponse {

	private Long id;
	
	private Project responseEntity;
	
	private ProjectListResponse listResponseEntities;

	
	/**
	 * @return the id
	 */
	@XmlElement(name="project_id")
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
	@XmlElement(name="project")
	public Project getResponseEntity() {
		return responseEntity;
	}

	/**
	 * @param responseEntity the responseEntity to set
	 */
	public void setResponseEntity(Project project) {
		this.responseEntity = project;
	}

	/**
	 * @return the listResponseEntities
	 */
	@XmlElement(name="projects")
	public ProjectListResponse getListResponseEntities() {
		return listResponseEntities;
	}

	/**
	 * @param listResponseEntities the listResponseEntities to set
	 */
	public void setListResponseEntities(ProjectListResponse projectListResponse) {
		this.listResponseEntities = projectListResponse;
	}
	
	
}
