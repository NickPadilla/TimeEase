/**
 * 
 */
package com.monstersoftwarellc.timeease.repository.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.monstersoftwarellc.timeease.enums.SearchOperators;
import com.monstersoftwarellc.timeease.model.impl.Entry;
import com.monstersoftwarellc.timeease.model.impl.Entry_;

/**
 * @author nicholas
 *
 */
public class EntrySpecifications {


	  public static Specification<Entry> searchForEntryDate(final Date entryDate, final SearchOperators operator) {
		    return new Specification<Entry>() {
				@Override
				public Predicate toPredicate(Root<Entry> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate pred = null;
					switch(operator) {
					case EQUAL_TO:
						pred = cb.equal(root.get(Entry_.entryDate), entryDate);
						break;
					case GREATER_THAN:
						pred = cb.greaterThan(root.get(Entry_.entryDate), entryDate);
						break;
					case GREATER_THAN_OR_EQUAL:
						pred = cb.greaterThanOrEqualTo(root.get(Entry_.entryDate), entryDate);
						break;
					case LESS_THAN:
						pred = cb.lessThan(root.get(Entry_.entryDate), entryDate);
						break;
					case LESS_THAN_OR_EQUAL:
						pred = cb.lessThanOrEqualTo(root.get(Entry_.entryDate), entryDate);
						break;
					case NOT_EQUAL_TO:
						pred = cb.notEqual(root.get(Entry_.entryDate), entryDate);
						break;
					default:
						pred = cb.equal(root.get(Entry_.entryDate), entryDate);
						break;
					}						
					return pred;
				}
			};
	  }

	  public static Specification<Entry> searchForHours(final Double hours, final SearchOperators operator) {
		    return new Specification<Entry>() {
				@Override
				public Predicate toPredicate(Root<Entry> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate pred = null;
					switch(operator) {
					case EQUAL_TO:
						pred = cb.equal(root.get(Entry_.hours), hours);
						break;
					case GREATER_THAN:
						pred = cb.greaterThan(root.get(Entry_.hours), hours);
						break;
					case GREATER_THAN_OR_EQUAL:
						pred = cb.greaterThanOrEqualTo(root.get(Entry_.hours), hours);
						break;
					case LESS_THAN:
						pred = cb.lessThan(root.get(Entry_.hours), hours);
						break;
					case LESS_THAN_OR_EQUAL:
						pred = cb.lessThanOrEqualTo(root.get(Entry_.hours), hours);
						break;
					case NOT_EQUAL_TO:
						pred = cb.notEqual(root.get(Entry_.hours), hours);
						break;
					default:
						pred = cb.equal(root.get(Entry_.hours), hours);
						break;
					}						
					return pred;
				}
			};
	  }
}
