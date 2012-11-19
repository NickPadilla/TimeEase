/**
 * 
 */
package com.monstersoftwarellc.timeease.repository.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


/**
 * @author nicholas
 *
 */
public class PropertySpecifications {

	 /* public static Specification<Property> searchForCompany(final Company company) {
		    return new Specification<Property>() {
				@Override
				public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Path<UserAccount> userAccount = root.get(Property_.createdBy);
					Path<Company> comp = userAccount.get(UserAccount_.company);
					return cb.equal(comp, company);
				}
			};
	  }

	  public static Specification<Property> searchForCreatedByUserName(final String userName) {
		    return new Specification<Property>() {
				@Override
				public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Path<UserAccount> userAccount = root.get(Property_.createdBy);
					Path<String> userNamePath = userAccount.get(UserAccount_.userName);
	                return cb.equal(cb.lower(userNamePath), userName);
				}
			};
	  }

	  public static Specification<Property> searchForOrigin(final Origin origin) {
		    return new Specification<Property>() {
				@Override
				public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get(Property_.origin), origin);
				}
			};
	  }

	  public static Specification<Property> searchForProductCategory(final ProductCategory category) {
		    return new Specification<Property>() {
				@Override
				public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.equal(root.get(Property_.category), category);
				}
			};
	  }

	  public static Specification<Property> searchForBrandNameLike(final String brandName) {
		    return new Specification<Property>() {
				@Override
				public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.like(cb.lower(root.<String>get(Property_.brandName)), getLikePattern(brandName));
				}
			};
	  }

	  public static Specification<Property> searchForNameLike(final String name) {
		    return new Specification<Property>() {
				@Override
				public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.like(cb.lower(root.<String>get(Property_.name)), getLikePattern(name));
				}
			};
	  }

	  public static Specification<Property> searchForSourceDateGTE(final Date fromDate) {
		    return new Specification<Property>() {
				@Override
				public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder cb) {					
					return cb.greaterThanOrEqualTo(root.get(Property_.createdDate), fromDate);
				}
			};
	  }

	  public static Specification<Property> searchForSourceDateLTE(final Date toDate) {
		    return new Specification<Property>() {
				@Override
				public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder cb) {					
					return cb.lessThanOrEqualTo(root.get(Property_.createdDate), toDate);
				}
			};
	  }

	  public static Specification<Property> searchForUPC14Like(final UPC upc) {
		    return new Specification<Property>() {
				@Override
				public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					ListJoin<Property, UPC> upcs = root.join(Property_.upcs);
					return cb.like(upcs.get(UPC_.upc14), getLikePattern(upc.getUpc14()));
				}
			};
	  }
	  
	  private static String getLikePattern(final String searchTerm) {
          StringBuilder pattern = new StringBuilder();
          pattern.append(searchTerm.toLowerCase());
          pattern.append("%");
          return pattern.toString();
      }
*/
}
