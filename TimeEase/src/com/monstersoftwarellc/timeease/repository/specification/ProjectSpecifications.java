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
import com.monstersoftwarellc.timeease.model.impl.Project;
import com.monstersoftwarellc.timeease.model.impl.Project_;
import com.monstersoftwarellc.timeease.utility.SpecificationUtility;

/**
 * @author nicholas
 *
 */
public class ProjectSpecifications {

	  public static Specification<Project> searchForNameLike(final String name) {
		    return new Specification<Project>() {
				@Override
				public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.like(cb.lower(root.<String>get(Project_.name)), SpecificationUtility.getLikePattern(name));
				}
			};
	  }

	  public static Specification<Project> searchForAccount(final Account account) {
		    return new Specification<Project>() {
				@Override
				public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.equal(root.get(Project_.account), account);
				}
			};
	  }
}
