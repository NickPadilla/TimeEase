/**
 * 
 */
package com.monstersoftwarellc.timeease.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.monstersoftwarellc.timeease.enums.OrderType;
import com.monstersoftwarellc.timeease.search.ISearchCritiera;

/**
 * @author nicholas
 *
 */
public interface IRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

	EntityManager getEntityManager();
	/**
	 * Will return the list filtered based on the given search criteria.
	 * @param searchCriteriaObject
	 * @param page
	 * @return
	 */
	List<T> getSearchListForPage(ISearchCritiera searchCriteria, OrderType defaultOrderType, int page, int numberOfItems);
	
	/**
	 * Will return the count based on the given search criteria.
	 * @param searchCriteriaObject
	 * @param page
	 * @return
	 */
	long getRecordCountFromSearchListForPage(ISearchCritiera searchCriteria, OrderType defaultOrderType, int page, int numberOfItems);
}
