/**
 * 
 */
package com.monstersoftwarellc.timeease.repository;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.monstersoftwarellc.timeease.property.IApplicationSettings;
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
	Page<T> getSearchListPage(ISearchCritiera<T> searchCriteria, int page, IApplicationSettings settings);
	
	/**
	 * Will return the count based on the given search criteria.
	 * @param searchCriteriaObject
	 * @param page
	 * @return
	 */
	int getSearchListPageCount(ISearchCritiera<T> searchCriteria, int page, IApplicationSettings settings);
	
}
