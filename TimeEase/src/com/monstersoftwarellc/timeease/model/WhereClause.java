/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

import com.monstersoftwarellc.timeease.enums.SearchOperators;

/**
 * @author nicholas
 *
 */
public class WhereClause {

	private String field;
	private Object searchFor;
	private boolean useLikeClause;
	private SearchOperators operator;
	private boolean caseSensitive;
	
	/**
	 * @param field
	 * @param searchFor
	 */
	public WhereClause(String field, Object searchFor) {
		this.field = field;
		this.searchFor = searchFor;
	}
	
	/**
	 * @param field
	 * @param searchFor
	 * @param useLikeClause
	 */
	public WhereClause(String field, Object searchFor, boolean useLikeClause) {
		this.field = field;
		this.searchFor = searchFor;
		this.useLikeClause = useLikeClause;
	}
	
	/**
	 * @param field
	 * @param searchFor
	 * @param operator
	 */
	public WhereClause(String field, Object searchFor, SearchOperators operator) {
		this.field = field;
		this.searchFor = searchFor;
		this.operator = operator;
	}
	
	/**
	 * @param field
	 * @param searchFor
	 * @param useLikeClause
	 */
	public WhereClause(String field, Object searchFor, boolean useLikeClause, boolean caseSensitive) {
		this.field = field;
		this.searchFor = searchFor;
		this.useLikeClause = useLikeClause;
		this.caseSensitive = caseSensitive;
	}
	
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}
	/**
	 * @return the searchFor
	 */
	public Object getSearchFor() {
		return searchFor;
	}
	/**
	 * @param searchFor the searchFor to set
	 */
	public void setSearchFor(Object searchFor) {
		this.searchFor = searchFor;
	}
	/**
	 * @return the useLikeClause
	 */
	public boolean isUseLikeClause() {
		return useLikeClause;
	}
	/**
	 * @param useLikeClause the useLikeClause to set
	 */
	public void setUseLikeClause(boolean useLikeClause) {
		this.useLikeClause = useLikeClause;
	}

	/**
	 * @return the operator
	 */
	public SearchOperators getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(SearchOperators operator) {
		this.operator = operator;
	}

	/**
	 * @return the caseSensitive
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * @param caseSensitive the caseSensitive to set
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

}
