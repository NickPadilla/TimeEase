/**
 * 
 */
package com.monstersoftwarellc.timeease.repository.impl;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.repository.IRepository;

/**
 * @author nicholas
 *
 */
public interface AccountRepository extends IRepository<Account> {

	Account findByUsername(String username);
	
}
