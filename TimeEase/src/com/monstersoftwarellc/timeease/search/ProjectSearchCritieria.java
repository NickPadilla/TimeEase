/**
 * 
 */
package com.monstersoftwarellc.timeease.search;

import javax.persistence.OrderBy;

import com.monstersoftwarellc.timeease.annotations.MappedColumnName;
import com.monstersoftwarellc.timeease.model.AbstractPropertChangeSupport;
import com.monstersoftwarellc.timeease.model.impl.Account;

/**
 * @author nicholas
 *
 */
public class ProjectSearchCritieria extends AbstractPropertChangeSupport implements ISearchCritiera {

	@OrderBy
	@MappedColumnName(useLikeForWhereClause=true,caseSensitive=false)
	private String name;
	@MappedColumnName
	private Account account;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String firstName) {
		this.name = firstName;
	}
	
	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account user) {
		this.account = user;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.enums.ILabel#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Project Search";
	}

}