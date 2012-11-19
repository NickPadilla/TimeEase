/**
 * 
 */
package com.monstersoftwarellc.timeease.search;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;

/**
 * @author nicholas
 *
 */
public interface ISearchCritiera<T> {

	/**
	 * This interface method provides an easy way for the Search Critiera's to define their own query.  
	 * @return
	 */
	public abstract Specifications<T> getQuerySpecifications();
	
	/**
	 * This method provides an easy way to add order by statements to your criteria query.
	 * @return
	 */
	public abstract Sort getSort();
}
