/**
 * 
 */
package com.monstersoftwarellc.timeease.service;

import com.monstersoftwarellc.timeease.repository.impl.TaskRepository;

/**
 * @author nicholas
 *
 */
public interface ITaskService {

	public abstract TaskRepository getTaskRepository();
}
