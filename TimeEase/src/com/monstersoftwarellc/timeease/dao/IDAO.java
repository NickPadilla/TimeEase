/**
 * 
 */
package com.monstersoftwarellc.timeease.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import com.monstersoftwarellc.timeease.enums.OrderType;
import com.monstersoftwarellc.timeease.model.IModelObject;
import com.monstersoftwarellc.timeease.model.WhereClause;
import com.monstersoftwarellc.timeease.search.ISearchCritiera;

/**
 * @author nick
 *
 */
public interface IDAO<T extends IModelObject> {
	
	/**
	 * @param instance
	 */
	public abstract void persist(T instance);
	
	/**
	 * @param instances
	 */
	public abstract void persistAll(Collection<T> instances);
	
	/**
	 * @param instance
	 */
	public abstract void delete(T instance);
	
	/**
	 * @param instance
	 * @return
	 */
	public abstract T merge(T instance);
	
	/**
	 * @param instance
	 * @return
	 */
	public abstract Collection<T> mergeAll(Collection<T> instance);
	
	/**
	 * @param orderBy
	 * @return
	 */
	public abstract List<T> findAllOrderBy(String ... orderBy);
	

	/**
	 * @param whereClauses
	 * @param useAndBetweenWhereClauses
	 * @param orderBy
	 * @return
	 */
	public abstract List<T> findAllOrderBy(List<WhereClause> whereClauses, boolean useAndBetweenWhereClauses, String... orderBy);
	

	/**
	 * @param defaultOrderType
	 * @param whereClauses
	 * @param orderBy
	 * @return
	 */
	public abstract List<T> findAllOrderBy(OrderType defaultOrderType, List<WhereClause> whereClauses, String... orderBy);
	
	/**
	 * Will return a list of all records matching given criteria.  If page or numberOfItems are equal to -1
	 * we will return all items.
	 * @param page
	 * @param numberOfItems
	 * @param defaultOrderType
	 * @param orderBy
	 * @return
	 */
	public abstract List<T> findPageAndOrder(int page, int numberOfItems, OrderType defaultOrderType, List<WhereClause> whereClauses, String... orderBy);
	
	/**
	 * We will return a list of records from page with the given numberOfItems.
	 * If either of these settings are -1 we will return all items.
	 * @param page
	 * @param numberOfItems
	 * @return
	 */
	public abstract List<T> find(int page, int numberOfItems, List<WhereClause> whereClauses);
	
	/**
	 * @return
	 */
	public abstract Long getCount();
	
	/**
	 * Gets the record count for the given page.
	 * @param page
	 * @return 
	 */
	public abstract long getRecordCountFromPage(int page, int numberOfItems, List<WhereClause> whereClauses);
	
	/**
	 * @param record
	 * @return
	 */
	public abstract T getRecordById(Serializable record);
	
	/**
	 * @param columnName
	 * @param data
	 * @return
	 */
	public abstract T getRecordByColumnNameWhereDataEquals(List<WhereClause> whereClauses);
	
	/**
	 * Will return the list filtered based on the given search criteria.
	 * @param searchCriteriaObject
	 * @param page
	 * @return
	 */
	public abstract List<T> getSearchListForPage(ISearchCritiera searchCriteria, OrderType defaultOrderType, int page, int numberOfItems);
	
	/**
	 * Will return the count based on the given search criteria.
	 * @param searchCriteriaObject
	 * @param page
	 * @return
	 */
	public abstract long getRecordCountFromSearchListForPage(ISearchCritiera searchCriteria, OrderType defaultOrderType, int page, int numberOfItems);
	
	public abstract EntityManager getEntityManager();
}
