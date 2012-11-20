/**
 * 
 */
package com.monstersoftwarellc.timeease.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.model.impl.Task;
import com.monstersoftwarellc.timeease.model.impl.Task_;
import com.monstersoftwarellc.timeease.utility.SpecificationUtility;

/**
 * @author nicholas
 *
 */
public class TaskSpecifications {
	
	  public static Specification<Task> searchForNameLike(final String name) {
		    return new Specification<Task>() {
				@Override
				public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.like(cb.lower(root.<String>get(Task_.name)), SpecificationUtility.getLikePattern(name));
				}
			};
	  }

	  public static Specification<Task> searchForCreatedBy(final Account account) {
		    return new Specification<Task>() {
				@Override
				public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.equal(root.get(Task_.createdBy), account);
				}
			};
	  }
}
