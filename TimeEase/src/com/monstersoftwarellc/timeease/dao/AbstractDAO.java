/**
 * 
 */
package com.monstersoftwarellc.timeease.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.monstersoftwarellc.timeease.annotations.MappedColumnName;
import com.monstersoftwarellc.timeease.annotations.MappedToCollection;
import com.monstersoftwarellc.timeease.enums.OrderType;
import com.monstersoftwarellc.timeease.enums.SearchOperators;
import com.monstersoftwarellc.timeease.model.IModelObject;
import com.monstersoftwarellc.timeease.model.WhereClause;
import com.monstersoftwarellc.timeease.search.ISearchCritiera;

/**
 * @author nick
 *
 */
@Repository
@Transactional
public abstract class AbstractDAO<T extends IModelObject> implements IDAO<T> {

	@PersistenceContext
	protected EntityManager entityManager;

	private final Class<T> daoImplClass;

	/**
	 * Used to store class type for generic type.
	 * @param returnedClass the Class of the concrete Type.
	 */
	protected AbstractDAO(Class<T> daoImplClass) {
		this.daoImplClass = daoImplClass;
	}

	/**
	 * Should return the Log object to be used during execution. 
	 * For performance reasons this should reference a static Log of the implementing class. 
	 * @return
	 */
	protected abstract Logger getLog();


	protected Class<T> getDaoImplClass() {
		return daoImplClass;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#persist(java.lang.Object)
	 */
	@Override
	public void persist(T instance) {
		if(getLog().isDebugEnabled()){
			getLog().debug("persisting "+daoImplClass.getName()+"'s");
		}
		try{
			entityManager.persist(instance);
			entityManager.flush();
			getLog().debug("persist successful");
		}catch (RuntimeException re) {
			getLog().error("persist failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#persistAll(java.util.Collection)
	 */
	@Override
	public void persistAll(Collection<T> instances) {
		if(getLog().isDebugEnabled()){
			getLog().debug("persisting "+daoImplClass.getName()+"'s");
		}
		try{
			for(T instance : instances){
				entityManager.persist(instance);
			}
			entityManager.flush();
			getLog().debug("persist successful");
		}catch (RuntimeException re) {
			getLog().error("persist failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(T instance) {
		if(getLog().isDebugEnabled()){
			getLog().debug("deleting "+daoImplClass.getName()+"'s");
		}
		try{
			T copy = entityManager.getReference(daoImplClass, ((IModelObject)instance).getId());
			entityManager.remove(copy);
			entityManager.flush();
			getLog().debug("delete successful");
		}catch (RuntimeException re) {
			getLog().error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#merge(java.lang.Object)
	 */
	@Override
	public T merge(T instance) {
		if(getLog().isDebugEnabled()){
			getLog().debug("merging "+daoImplClass.getName()+"'s");
		}
		try{
			T object = entityManager.merge(instance);
			entityManager.flush();
			getLog().debug("merge successful, result size: " + object.toString());
			return object;
		}catch (RuntimeException re) {
			getLog().error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#mergeAll(java.util.Collection)
	 */
	@Override
	public Collection<T> mergeAll(Collection<T> instances) {
		if(getLog().isDebugEnabled()){
			getLog().debug("merging "+daoImplClass.getName()+"'s");
		}
		try{
			List<T> newList = new ArrayList<T>();
			for(T instance : instances){
				newList.add(entityManager.merge(instance));
			}
			entityManager.flush();
			getLog().debug("merge successful, result size: " + newList.toString());
			return newList;
		}catch (RuntimeException re) {
			getLog().error("merge failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#findAllOrderBy(java.lang.String[])
	 */
	@Override
	public List<T> findAllOrderBy(String... orderBy){
		return findAllOrderBy(null, null, orderBy);
	}


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#findAllOrderBy(com.monstersoftwarellc.goldrush.enums.impl.OrderType, com.monstersoftwarellc.goldrush.model.impl.WhereClause, java.lang.String[])
	 */
	@Override
	public List<T> findAllOrderBy(OrderType defaultOrderType, List<WhereClause> whereClauses, String... orderBy) {
		if(getLog().isDebugEnabled()){
			getLog().debug("findAllOrderBy "+daoImplClass.getName()+"'s");
		}

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(daoImplClass);
		Root<T> root = query.from(daoImplClass); 
		if(defaultOrderType != null){
			addOrderBy(builder, query, root, defaultOrderType, orderBy);
		}
		addWhereClauses(whereClauses, builder, query, root, false,true);
		List<T> records = entityManager.createQuery(query).getResultList();
		getLog().debug("findAllOrderBy successful, result size: " + records.size());
		return records;
	}
	
	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#findAllOrderBy(java.util.List, boolean, java.lang.String[])
	 */
	public List<T> findAllOrderBy(List<WhereClause> whereClauses, boolean useAndBetweenWhereClauses, String... orderBy) {
		if(getLog().isDebugEnabled()){
			getLog().debug("findAllOrderBy "+daoImplClass.getName()+"'s");
		}

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(daoImplClass);
		Root<T> root = query.from(daoImplClass); 
		addOrderBy(builder, query, root, OrderType.ASC, orderBy);
		addWhereClauses(whereClauses, builder, query, root, useAndBetweenWhereClauses,true);
		List<T> records = entityManager.createQuery(query).getResultList();
		getLog().debug("findAllOrderBy successful, result size: " + records.size());
		return records;
	}
	

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#findPageAndOrder(int, int, com.monstersoftwarellc.goldrush.enums.impl.OrderType, com.monstersoftwarellc.goldrush.model.impl.WhereClause, java.lang.String[])
	 */
	@Override
	public List<T> findPageAndOrder(int page, int numberOfItems, OrderType defaultOrderType, List<WhereClause> whereClauses, String... orderBy) {
		if(getLog().isDebugEnabled()){
			getLog().debug("findOrderBy "+daoImplClass.getName()+"'s");
		}
		List<T> records = Collections.emptyList();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(daoImplClass);
		Root<T> root = query.from(daoImplClass); 
		addWhereClauses(whereClauses, builder, query, root, false,true);
		addOrderBy(builder, query, root, defaultOrderType, orderBy);
		records = getPageRecords(page, numberOfItems, query);
		getLog().debug("findOrderBy successful, result size: " + records.size());
		return records;
	}


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#find(int, int, com.monstersoftwarellc.goldrush.model.impl.WhereClause)
	 */
	@Override
	public List<T> find(int page, int numberOfItems, List<WhereClause> whereClauses) {
		if(getLog().isDebugEnabled()){
			getLog().debug("find "+daoImplClass.getName()+"'s");
		}
		List<T> records = Collections.emptyList();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(daoImplClass);
		Root<T> root = query.from(daoImplClass);
		addWhereClauses(whereClauses, builder, query, root, false,true);
		records = getPageRecords(page, numberOfItems, query);
		getLog().debug("find successful, result size: " + records.size());
		return records;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#getCount()
	 */
	@Override
	public Long getCount() {
		if(getLog().isDebugEnabled()){
			getLog().debug("findAllOrderBy "+daoImplClass.getName()+"'s");
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(daoImplClass);
		Long records = (long) entityManager.createQuery(query).getResultList().size();
		getLog().debug("findAllOrderBy successful, result size: " + records);
		return records;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#getRecordById(java.io.Serializable)
	 */
	@Override
	public T getRecordById(Serializable id) {
		if(getLog().isDebugEnabled()){
			getLog().debug("findAllOrderBy "+daoImplClass.getName()+"'s");
		}
		T record = entityManager.find(daoImplClass, id);
		getLog().debug("getRecordById successful, record: " + record);
		return record;
	}


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#getRecordByColumnNameWhereDataEquals(com.monstersoftwarellc.goldrush.model.impl.WhereClause)
	 */
	@Override
	public T getRecordByColumnNameWhereDataEquals(List<WhereClause> whereClauses) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(daoImplClass);
		Root<T> root = query.from(daoImplClass); 
		addWhereClauses(whereClauses, builder, query, root, false,true);
		List<T> list = entityManager.createQuery(query).getResultList();
		return (list != null && !list.isEmpty()) ? list.get(0) : null;
	}


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#getRecordCountFromPage(int, int, com.monstersoftwarellc.goldrush.model.impl.WhereClause)
	 */
	@Override
	public long getRecordCountFromPage(int page, int numberOfItems, List<WhereClause> whereClauses) {
		long records = 0;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(daoImplClass);
		if(page == -1 || numberOfItems == -1 || numberOfItems == 0){
			records = (long) entityManager.createQuery(query)
					.getResultList().size();
		}else{
			int index = page * numberOfItems;
			records = (long) entityManager.createQuery(query)
					.setFirstResult(index)
					.setMaxResults(numberOfItems)
					.getResultList().size();
		}
		return records;
	}


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#getSearchListForPage(com.monstersoftwarellc.goldrush.model.ISearchCritiera, com.monstersoftwarellc.goldrush.enums.impl.OrderType, int, int)
	 */
	@Override
	public List<T> getSearchListForPage(ISearchCritiera searchCriteria, OrderType defaultOrderType, int page, int numberOfItems){
		List<T> returnList = Collections.emptyList();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getDaoImplClass());
		Root<T> root = query.from(daoImplClass);
		Field fields[] = searchCriteria.getClass().getDeclaredFields();
		List<String> orderBy = new ArrayList<String>();
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			for (Field field : fields){
				if(field.isAnnotationPresent(MappedColumnName.class)){
					field.setAccessible(true); 
					MappedColumnName columnAnnotation = field.getAnnotation(MappedColumnName.class);
					String columnName = columnAnnotation.columnNameOverride();
					if(columnName.isEmpty()){
						columnName = field.getName();
					}
					Object data = field.get(searchCriteria);
					if(data != null){
						// if we need to not take case into account
						if(!columnAnnotation.caseSensitive()){
							if(data instanceof String){
								data = ((String)data).toLowerCase();
							}
						}
						// got needed info for search, now get search operators if needed
						SearchOperators operator = null;
						if(columnAnnotation.requireSearchOperator()){
							String operatorField = columnAnnotation.searchOperatorFieldNameOverride();
							if(operatorField.isEmpty()){
								operatorField = columnName + "Operator";
							}
							operator = findOperatorForField(searchCriteria, operatorField);
						}
						// now set the actual where clause
						addPredicateToList(builder, root, query, predicates, columnName,
								data, operator, field, columnAnnotation);
					}
					// last but not least, get order by
					if(field.isAnnotationPresent(OrderBy.class)){
						orderBy.add(columnName);
					}
				}
			}
			if(predicates.isEmpty()){
				// get all items
				if(page == -1 || numberOfItems == -1 || numberOfItems == 0){
					returnList = findAllOrderBy(defaultOrderType, null, orderBy.toArray(new String[0]));
				}else{
					returnList = findPageAndOrder(page, numberOfItems, defaultOrderType, null, orderBy.toArray(new String[0]));
				}
				
			}else{
				query.where(predicates.toArray(new Predicate[0]));
				addOrderBy(builder, query, root, defaultOrderType, orderBy.toArray(new String[0]));
				returnList = getPageRecords(page, numberOfItems, query);
			}
		} catch(IllegalArgumentException ex){
			ex.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.dao.IDAO#getRecordCountFromSearchListForPage(com.monstersoftwarellc.goldrush.model.ISearchCritiera, com.monstersoftwarellc.goldrush.enums.impl.OrderType, int, int)
	 */
	@Override
	public long getRecordCountFromSearchListForPage(ISearchCritiera searchCriteria, OrderType defaultOrderType, int page, int numberOfItems){
		return getSearchListForPage(searchCriteria, defaultOrderType, page, numberOfItems).size();
	}

	/**
	 * @param page
	 * @param numberOfItems
	 * @param query
	 * @return
	 */
	private List<T> getPageRecords(int page, int numberOfItems,
			CriteriaQuery<T> query) {
		List<T> records;
		if(page == -1 || numberOfItems == -1 || numberOfItems == 0){
			records = entityManager.createQuery(query)
					.getResultList();
		}else{
			int index = page * numberOfItems;
			records = entityManager.createQuery(query)
					.setFirstResult(index)
					.setMaxResults(numberOfItems)
					.getResultList();
		}
		return records;
	}
	
	/**
	 * @param whereClauses
	 * @param builder
	 * @param query
	 * @param root
	 */
	private void addWhereClauses(List<WhereClause> whereClauses, CriteriaBuilder builder, CriteriaQuery<T> query, 
			Root<T> root, boolean useAndBetweenPredicates, boolean addToQuery) {
		if(whereClauses != null && !whereClauses.isEmpty()){
			List<Predicate> predicates = new ArrayList<Predicate>();
			for(WhereClause clause : whereClauses){
				if(clause.isUseLikeClause()){
					Path<String> path = root.get(clause.getField());
					if(clause.isCaseSensitive()){
						predicates.add(builder.like(path, ((String)clause.getSearchFor())+"%"));
					}else{
						predicates.add(builder.like(builder.lower(path), ((String)clause.getSearchFor())+"%"));
					}
				}else{
					if(!clause.getField().contains(".")){
						addPredicateFromSearchOperator(builder, predicates, clause.getSearchFor(), clause.getOperator(),
								root.get(clause.getField()));						
					}else{
						String[] mappedBy = StringUtils.split(clause.getField(), "."); 
						Path<Object> path = root.join(mappedBy[0]).get(mappedBy[1]);
						addPredicateFromSearchOperator(builder, predicates, clause.getSearchFor(), clause.getOperator(),
								path);
					}
				}
			}
			if(addToQuery){
				if(useAndBetweenPredicates){
					query.where(builder.and(predicates.toArray(new Predicate[0])));
				}else{
					query.where(builder.or(predicates.toArray(new Predicate[0])));
				}
			}
		}
	}
	
	/**
	 * Only used by {@link ISearchCritiera} operations.
	 * @param builder
	 * @param root
	 * @param predicates
	 * @param columnName
	 * @param data
	 * @param operator
	 */
	private void addPredicateToList(CriteriaBuilder builder, Root<T> root, CriteriaQuery<T> query,
			List<Predicate> predicates, String columnName, Object data, SearchOperators operator, 
			Field field, MappedColumnName mappedColumnNameAnnotation) {
		Path<Object> path = null;
		
		// if we are dealing with a type of collection then we need to Join the column.
		if(field.isAnnotationPresent(MappedToCollection.class)){
			MappedToCollection mappedToCollection = field.getAnnotation(MappedToCollection.class);
			path = root.join(mappedToCollection.mappedBy()).get(columnName);
		}else{
			path = root.get(columnName);
		}
		
		if(mappedColumnNameAnnotation.useLikeForWhereClause()){
			addWhereClauses(Collections.singletonList(new WhereClause(columnName, data, true, true)),builder,query,root,true,false);
		}else{
			addPredicateFromSearchOperator(builder, predicates, data, operator,	path);
		}
	}

	/**
	 * @param builder
	 * @param predicates
	 * @param data
	 * @param operator
	 * @param path
	 */
	@SuppressWarnings("unchecked")
	private void addPredicateFromSearchOperator(CriteriaBuilder builder,
			List<Predicate> predicates, Object data, SearchOperators operator, Path<Object> path) {
		if(operator != null){
			switch(operator){
				case EQUAL_TO:
					predicates.add(builder.equal(path, data));
				break;
				case GREATER_THAN:
					if(data instanceof Number){
						predicates.add(builder.gt(path.as(Number.class),(Number) data));	    			
					}else if(data instanceof Date){
						predicates.add(builder.greaterThan(path.as(Date.class),(Date) data));
					}else if(data instanceof Enum){
						predicates.add(builder.greaterThan(path.as(Enum.class),(Enum<?>) data));
					}else{
						throw new IllegalStateException("Error getting Search List for " + data + " Return Type not Supported! Please add support!");
					}
				break;
				case GREATER_THAN_OR_EQUAL:
					if(data instanceof Number){
						predicates.add(builder.ge(path.as(Number.class),(Number) data));	    			
					}else if(data instanceof Date){
						predicates.add(builder.greaterThanOrEqualTo(path.as(Date.class),(Date) data));
					}else if(data instanceof Enum){
						predicates.add(builder.greaterThanOrEqualTo(path.as(Enum.class),(Enum<?>) data));
					}else{
						throw new IllegalStateException("Error getting Search List for " + data + " Return Type not Supported! Please add support!");
					}
				break;
				case LESS_THAN:
					if(data instanceof Number){
						predicates.add( builder.lt(path.as(Number.class),(Number) data));	    			
					}else if(data instanceof Date){
						predicates.add(builder.lessThan(path.as(Date.class),(Date) data));
					}else if(data instanceof Enum){
						predicates.add(builder.lessThan(path.as(Enum.class), (Enum<?>) data));
					}else{
						throw new IllegalStateException("Error getting Search List for " + data + " Return Type not Supported! Please add support!");
					}
				break;
				case LESS_THAN_OR_EQUAL:
					if(data instanceof Number){
						predicates.add( builder.le(path.as(Number.class),(Number) data));	    			
					}else if(data instanceof Date){
						predicates.add(builder.lessThanOrEqualTo(path.as(Date.class),(Date) data));
					}else if(data instanceof Enum){
						predicates.add(builder.lessThanOrEqualTo(path.as(Enum.class), (Enum<?>) data));
					}else{
						throw new IllegalStateException("Error getting Search List for " + data + " Return Type not Supported! Please add support!");
					}
				break;
				case NOT_EQUAL_TO:
					predicates.add(builder.notEqual(path, data));
				break;
				default:
					throw new IllegalStateException("Search Operators MUST be setup in AbstractDAO to enable its usage! Please add support for new type!");
			}
		}else{
			predicates.add(builder.equal(path, data));
		}
	}

	/**
	 * @param searchCriteria
	 * @param operatorField
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private SearchOperators findOperatorForField(Object searchCriteria, String operatorField) throws IllegalArgumentException, IllegalAccessException {
		SearchOperators operator = null;
		Field fields[] = searchCriteria.getClass().getDeclaredFields();
		for (Field field : fields){
			if(field.getName().equals(operatorField) && field.getType().isAssignableFrom(SearchOperators.class)){
				field.setAccessible(true); 
				operator = (SearchOperators) field.get(searchCriteria);
				break;
			}
		}
		return operator;
	}


	/**
	 * @param builder
	 * @param query
	 * @param root
	 * @param orderBy
	 */
	private void addOrderBy(CriteriaBuilder builder, CriteriaQuery<T> query,
			Root<T> root, OrderType orderType, String... orderBy) {
		List<Order> orderList = new ArrayList<Order>();
		for(String order : orderBy){
			if(orderType == OrderType.ASC){
				orderList.add(builder.asc(root.get(order)));				
			}else{
				orderList.add(builder.desc(root.get(order)));
			}
		}
		query.orderBy(orderList);
	}

}