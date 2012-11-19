/**
 * 
 */
package com.monstersoftwarellc.timeease.repository.impl;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.model.impl.Task;
import com.monstersoftwarellc.timeease.repository.IRepository;

/**
 * @author nicholas
 *
 */
public interface TaskRepository extends IRepository<Task> {
 
	List<Task> findByAccount(Account account, Sort sort);

	List<Task> findByAccountOrderByNameAsc(Account account);
}
