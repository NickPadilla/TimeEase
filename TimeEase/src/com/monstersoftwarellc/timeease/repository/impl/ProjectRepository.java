/**
 * 
 */
package com.monstersoftwarellc.timeease.repository.impl;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.model.impl.Client;
import com.monstersoftwarellc.timeease.model.impl.Project;
import com.monstersoftwarellc.timeease.repository.IRepository;

/**
 * @author nicholas
 *
 */
public interface ProjectRepository extends IRepository<Project> {

	List<Project> findByClient(Client client, Sort sort);
	
	List<Project> findByCreatedBy(Account account, Sort sort);

	List<Project> findByCreatedByAndClientOrderByNameAsc(Account Account, Client client);

}
