/**
 * 
 */
package com.monstersoftwarellc.timeease.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.repository.impl.AccountRepository;
import com.monstersoftwarellc.timeease.service.IAccountService;
import com.monstersoftwarellc.timeease.utility.PasswordUtility;

/**
 * @author nicholas
 *
 */
@Service
public class AccountService implements IAccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account authenticate(String username, String password) {
		Account account = accountRepository.findByUsername(username);
		if (account != null) {
			if (!account.getPassword().equals(PasswordUtility.encodePassword(password))) {
				account = null;
			}
		}
		// TODO: create some exceptions for user-not-found and password-incorrect
		return account;
	}

	@Override
	public AccountRepository getAccountRepository() {
		return accountRepository;
	}
	
	
}
