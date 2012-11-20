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
import com.monstersoftwarellc.timeease.model.impl.Task;
import com.monstersoftwarellc.timeease.model.impl.Task_;
import com.monstersoftwarellc.timeease.repository.specification.TaskSpecifications;
import com.monstersoftwarellc.timeease.utility.SpecificationUtility;

/**
 * @author nicholas
 *
 */
public class TaskSearchCritieria extends AbstractPropertChangeSupport implements ISearchCritiera<Task> {

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
		return "Task Search";
	}

	@Override
	public Specifications<Task> getQuerySpecifications() {
		List<Specification<Task>> projectSpecs = new ArrayList<Specification<Task>>();
		
		if (name != null) {
			projectSpecs.add(TaskSpecifications.searchForNameLike(name));
		}
		
		if (account != null) {
			projectSpecs.add(TaskSpecifications.searchForCreatedBy(account));
		}
		
		// now build out our query
		Specifications<Task> spec = null;
		for(Specification<Task> s : projectSpecs){
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
		Sort sort = new Sort(SpecificationUtility.getSortDirection(), Task_.name.getName());
		return sort;
	}

}