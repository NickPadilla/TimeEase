/**
 * 
 */
package com.monstersoftwarellc.timeease.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.monstersoftwarellc.timeease.model.AbstractPropertChangeSupport;
import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.model.impl.Client;
import com.monstersoftwarellc.timeease.model.impl.Client_;
import com.monstersoftwarellc.timeease.repository.specification.ClientSpecifications;
import com.monstersoftwarellc.timeease.utility.SpecificationUtility;

/**
 * @author nicholas
 *
 */
public class ClientSearchCritieria extends AbstractPropertChangeSupport implements ISearchCritiera<Client> {

	private String firstName;
	private String lastName;
	private String organization;
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


	@Override
	public Specifications<Client> getQuerySpecifications() {
		List<Specification<Client>> clientSpecs = new ArrayList<Specification<Client>>();
		
		if (firstName != null) {
			clientSpecs.add(ClientSpecifications.searchForFirstName(firstName));
		}
		
		if (lastName != null) {
			clientSpecs.add(ClientSpecifications.searchForLastName(lastName));
		}
		
		if (organization != null) {
			clientSpecs.add(ClientSpecifications.searchForOrganization(organization));
		}
		
		if (account != null) {
			clientSpecs.add(ClientSpecifications.searchForAccount(account));
		}
		
		// now build out our query
		Specifications<Client> spec = null;
		for(Specification<Client> s : clientSpecs){
				if(spec == null){
					spec = Specifications.where(s);
				}else{
					spec = spec.and(s);
				}
		}

		return spec;
	}


	@Override
	public Sort getSort() {
		Sort sort = new Sort(SpecificationUtility.getSortDirection(), Client_.firstName.getName());
		sort.and(new Sort(Client_.lastName.getName()));
		return sort;
	}

}