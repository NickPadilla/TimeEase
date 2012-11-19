/**
 * 
 */
package com.monstersoftwarellc.timeease.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.repository.impl.TaskRepository;
import com.monstersoftwarellc.timeease.service.ITaskService;

/**
 * @author nicholas
 *
 */
@Service
public class TaskService implements ITaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public TaskRepository getTaskRepository() {
		return taskRepository;
	}

}
