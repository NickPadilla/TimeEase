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
import com.monstersoftwarellc.timeease.model.impl.Client;
import com.monstersoftwarellc.timeease.model.impl.Client_;


/**
 * @author nicholas
 *
 */
public class ClientSpecifications {

	public static Specification<Client> searchForAccount(final Account account) {
		return new Specification<Client>() {
			@Override
			public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get(Client_.account), account);
			}
		};
	}

	public static Specification<Client> searchForFirstName(final String firstName) {
		return new Specification<Client>() {
			@Override
			public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String>get(Client_.firstName), firstName);
			}
		};
	}

	public static Specification<Client> searchForLastName(final String lastName) {
		return new Specification<Client>() {
			@Override
			public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String>get(Client_.lastName), lastName);
			}
		};
	}

	public static Specification<Client> searchForOrganization(final String organization) {
		return new Specification<Client>() {
			@Override
			public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String>get(Client_.organization), organization);
			}
		};
	}

}
