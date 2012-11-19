/**
 * 
 */
package com.monstersoftwarellc.timeease.service;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.repository.impl.AccountRepository;

/**
 * @author nicholas
 *
 */
public interface IAccountService {

	public abstract Account authenticate(String username, String password);
	
	public abstract AccountRepository getAccountRepository();
}
