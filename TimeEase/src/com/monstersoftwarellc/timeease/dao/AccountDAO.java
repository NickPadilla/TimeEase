/**
 * 
 */
package com.monstersoftwarellc.timeease.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.monstersoftwarellc.timeease.model.WhereClause;
import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.utility.PasswordUtility;


/**
 * @author nicholas
 *
 */
@Repository
@Transactional
public class AccountDAO extends AbstractDAO<Account> implements IAccountDAO {

	private static Logger LOG = Logger.getLogger(AccountDAO.class);
	
	/**
	 * 
	 */
	public AccountDAO(){
		super(Account.class);
	}

	
	
	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IUserDAO#findAllActive()
	 */
	@Override
	public List<Account> findAllActive() {
		List<WhereClause> clauses = new ArrayList<WhereClause>();
		clauses.add(new WhereClause("status.active", true));

		return findAllOrderBy(clauses, true, "username");
	}



	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IUserDAO#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public Account authenticate(String username, String password) {
		Account user = null;
		List<WhereClause> clauses = new ArrayList<WhereClause>();
		clauses.add(new WhereClause("username", username));
		//clauses.add(new WhereClause("status.active", true));

		user = findAllOrderBy(clauses, true, "username").get(0);

		if (user != null) {
			if (!user.getPassword().equals(PasswordUtility.encodePassword(password))) {
				user = null;
			}
		}
		return user;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.AbstractDAO#getLog()
	 */
	@Override
	protected Logger getLog() {
		return LOG;
	}

}
