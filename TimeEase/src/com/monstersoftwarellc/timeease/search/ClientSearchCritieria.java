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
public class ClientSearchCritieria extends AbstractPropertChangeSupport implements ISearchCritiera {

	@OrderBy
	private String firstName;
	@OrderBy
	private String lastName;
	private String organization;
	@MappedColumnName
	private Account account;


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}


	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
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


	@Override
	public String getLabel() {
		return "Client Search";
	}

}