/**
 * 
 */
package com.monstersoftwarellc.timeease.repository.impl;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.model.impl.Client;
import com.monstersoftwarellc.timeease.repository.IRepository;

/**
 * @author nicholas
 *
 */
public interface ClientRepository extends IRepository<Client> {

	List<Client> findByAccount(Account account, Sort sort);

	List<Client> findByAccountOrderByFirstNameAsc(Account account);
}
