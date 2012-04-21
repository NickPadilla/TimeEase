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
public class TaskSearchCritieria extends AbstractPropertChangeSupport implements ISearchCritiera {

	@OrderBy
	@MappedColumnName(useLikeForWhereClause=true,caseSensitive=false)
	private String name;
	@MappedColumnName
	private Account user;

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
	 * @return the user
	 */
	public Account getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Account user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.enums.ILabel#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Task Search";
	}

}