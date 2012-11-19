/**
 * 
 */
package com.monstersoftwarellc.timeease;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.service.IAccountService;
import com.monstersoftwarellc.timeease.utility.PasswordUtility;


/**
 * @author nicholas
 *
 */
@Service
public class CreateDatabaseForTesting implements ICreateDatabaseForTesting {

	@Autowired
	private IAccountService accountService;

	public void createTestUpdateDatabase(boolean devMode) {
		List<Account> listTest = null;
		try{
			listTest = accountService.getAccountRepository().findAll();
		}catch(IllegalArgumentException ex){
			// no db.. 
		}
		if(listTest == null || listTest.isEmpty() && devMode){
			try {
				createUser();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return
	 */
	private void createUser() {
		Account user = new Account();
		user.setFirstName("Admin");
		user.setLastName("Dude");
		user.setPassword(PasswordUtility.encodePassword("admin"));
		user.setUsername("admin");
		accountService.getAccountRepository().saveAndFlush(user);
	}

}
