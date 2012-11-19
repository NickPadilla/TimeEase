/**
 * 
 */
package com.monstersoftwarellc.timeease.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.repository.impl.ProjectRepository;
import com.monstersoftwarellc.timeease.service.IProjectService;

/**
 * @author nicholas
 *
 */
@Service
public class ProjectService implements IProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public ProjectRepository getProjectRepository() {
		return projectRepository;
	}

}
