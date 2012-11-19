/**
 * 
 */
package com.monstersoftwarellc.timeease.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.StringUtils;

import com.monstersoftwarellc.timeease.annotations.MappedColumnName;
import com.monstersoftwarellc.timeease.annotations.MappedToCollection;
import com.monstersoftwarellc.timeease.enums.OrderType;
import com.monstersoftwarellc.timeease.enums.SearchOperators;
import com.monstersoftwarellc.timeease.model.WhereClause;
import com.monstersoftwarellc.timeease.search.ISearchCritiera;

/**
 * @author nicholas
 *
 */
public class RepositoryImpl<T> extends SimpleJpaRepository<T, Long> implements IRepository<T>{
	
	 private EntityManager entityManager;
	 
	 private Class<T> domainClass;

	  // There are two constructors to choose from, either can be used.
	  public RepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
	    super(domainClass, entityManager);
	    // This is the recommended method for accessing inherited class dependencies.
	    this.entityManager = entityManager;
	    this.domainClass = domainClass;
	  }

	/* (non-Javadoc)
	 * @see com.reflogic.upd.repository.IRepository#refresh(java.lang.Object)
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	
	@Override
	public List<T> getSearchListForPage(ISearchCritiera searchCriteria, OrderType defaultOrderType, int page, int numberOfItems){
		List<T> returnList = Collections.emptyList();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(domainClass);
		Root<T> root = query.from(domainClass);
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
						// last but not least, get order by
						if(field.isAnnotationPresent(OrderBy.class)){
							orderBy.add(columnName);
						}
					}
				}
			}
			if(predicates.isEmpty()){
				// get all items
				if(page == -1 || numberOfItems == -1 || numberOfItems == 0){
					returnList = findAll(new Sort(Sort.Direction.ASC, orderBy.toArray(new String[0])));
				}else{
					// TODO: move application to use the internal Sort.Direction for the OrderType.
					findAll(new PageRequest(page, numberOfItems, Sort.Direction.ASC, orderBy.toArray(new String[0])));
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
	
	@Override
	public long getRecordCountFromSearchListForPage(ISearchCritiera searchCriteria, OrderType defaultOrderType, int page, int numberOfItems){
		return getSearchListForPage(searchCriteria, defaultOrderType, page, numberOfItems).size();
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
		for(String order : orderBy){
			if(orderType == OrderType.ASC){
				query.orderBy(builder.asc(root.get(order)));
			}else{
				query.orderBy(builder.desc(root.get(order)));
			}
		}
	}

}
