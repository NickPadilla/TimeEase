/**
 * 
 */
package com.monstersoftwarellc.timeease.dao;

import java.util.List;

import com.monstersoftwarellc.timeease.model.impl.Account;

/**
 * @author nicholas
 *
 */
public interface IAccountDAO extends IDAO<Account> {

	/**
	 * Check if the user name and password can be authenticated.
	 * @param username
	 * @param password
	 * @return {@link Employee} for if the user could be authenticated null if not.
	 */
	public Account authenticate(String username, String password);
	
	/**
	 * Find all active employees.
	 * @return
	 */
	public List<Account> findAllActive();
	
}
