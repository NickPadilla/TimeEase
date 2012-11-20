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
import com.monstersoftwarellc.timeease.model.impl.Project;
import com.monstersoftwarellc.timeease.model.impl.Project_;
import com.monstersoftwarellc.timeease.repository.specification.ProjectSpecifications;
import com.monstersoftwarellc.timeease.utility.SpecificationUtility;

/**
 * @author nicholas
 *
 */
public class ProjectSearchCritieria extends AbstractPropertChangeSupport implements ISearchCritiera<Project> {

	private String name;
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

	@Override
	public Specifications<Project> getQuerySpecifications() {
		List<Specification<Project>> projectSpecs = new ArrayList<Specification<Project>>();
		
		if (name != null) {
			projectSpecs.add(ProjectSpecifications.searchForNameLike(name));
		}
		
		if (account != null) {
			projectSpecs.add(ProjectSpecifications.searchForCreatedBy(account));
		}
		
		// now build out our query
		Specifications<Project> spec = null;
		for(Specification<Project> s : projectSpecs){
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
		Sort sort = new Sort(SpecificationUtility.getSortDirection(), Project_.name.getName());
		return sort;
	}
}