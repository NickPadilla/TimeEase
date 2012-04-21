/**
 * 
 */
package com.monstersoftwarellc.timeease;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.dao.IAccountDAO;
import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.utility.PasswordUtility;


/**
 * @author nicholas
 *
 */
@Service
public class CreateDatabaseForTesting implements ICreateDatabaseForTesting {

	@Autowired
	private IAccountDAO userDAO;

	public void createTestUpdateDatabase(boolean devMode) {
		List<Account> listTest = null;
		try{
			listTest = userDAO.findAllOrderBy("name");
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
		userDAO.persist(user);
	}

}
