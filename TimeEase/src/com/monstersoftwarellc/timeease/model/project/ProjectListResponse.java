/**
 * 
 */
package com.monstersoftwarellc.timeease.model.project;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractListResponse;

/**
 * Concrete {@link AbstractListResponse} object for the {@link Project} entity.
 * @author nick
 *
 */
@XmlRootElement(name="projects")
public class ProjectListResponse extends AbstractListResponse {
	
	private List<Project> projectList;

	/**
	 * @param projectList the projectList to set
	 */
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	/**
	 * @return the projectList
	 */
	@XmlAnyElement(lax=true)
	public List<Project> getProjectList() {
		return projectList;
	}

}
