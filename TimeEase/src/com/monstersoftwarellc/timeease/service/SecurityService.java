/**
 * 
 */
package com.monstersoftwarellc.timeease.service;

import java.security.AccessController;

import javax.security.auth.Subject;

import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.model.impl.Account;

/**
 * @author nicholas
 *
 */
@Service
public class SecurityService implements ISecurityService {


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.service.ISecurityService#getCurrentlyLoggedInUser()
	 */
	@Override
	public Account getCurrentlyLoggedInUser() {
		Subject subject = Subject.getSubject(AccessController.getContext()); 
		Account user = (Account) subject.getPublicCredentials().toArray()[0];
		return user;
	}

}
