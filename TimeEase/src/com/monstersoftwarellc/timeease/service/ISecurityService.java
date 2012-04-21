/**
 * 
 */
package com.monstersoftwarellc.timeease.service;

import com.monstersoftwarellc.timeease.model.impl.Account;


/**
 * @author nicholas
 *
 */
public interface ISecurityService {
	
	public abstract Account getCurrentlyLoggedInUser();
	
}
